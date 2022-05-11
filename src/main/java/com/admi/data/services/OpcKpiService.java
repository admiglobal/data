package com.admi.data.services;

import com.admi.data.dto.Opc200PartDto;
import com.admi.data.dto.OpcKpiDto;
import com.admi.data.entities.*;
import com.admi.data.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OpcKpiService {
    @Autowired
    OpcTsp200DataRepository opcTsp200DataRepo;

    @Autowired
    OpcWeeklyPerformanceRepository opcWeeklyPerformanceRepo;

    @Autowired
    DealerMasterRepository dealerMasterRepo;

    @Autowired
    FordDealerInventoryRepository fordDealerInventoryRepo;

    @Autowired
    OpcTsp200Repository opcTsp200Repo;

    @Autowired
    FordPtRepository fordPtRepo;

    /**
     * Updates OPC_TSP_200_DATA and takes a performance snapshot.
     * If we didn't receive new inventory data, doesn't delete old data but still takes a new snapshot.
     */
    public void runOpcProcess(){
        List<DealerMasterEntity> quickLaneDealers = dealerMasterRepo.findAllQuickLaneDealers();

        for(DealerMasterEntity dealer : quickLaneDealers){
            String paCode = dealer.getPaCode();
            try{
                updateOpc200Data(paCode);
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("Failed to run OPC process for P&A Code " + paCode + ".");
            }
            takePerformanceSnapshot(paCode); //take snapshot AFTER updating, even if update fails
        }

        opcTsp200DataRepo.flush();
        System.out.println("Finished running OPC data process.");
    }

    /**
     * For testing purposes only
     */
    public void tester(){
        System.out.println(fordDealerInventoryRepo.findFirstByPaCode("00000"));
    }

    /**
     * Queries the list of OPC parts that a dealer has on-hand, including QOH info
     */
    private List<OpcTsp200DataEntity> getOpc200Data(String paCode){
        List<FordDealerInventoryEntity> inventory = fordDealerInventoryRepo.findAllByPaCode(paCode);
        List<OpcTsp200Entity> opc200List = opcTsp200Repo.findAll();
        Set<Opc200PartDto> opcOnHand = new HashSet<>();

        for(FordDealerInventoryEntity part : inventory){
            for(OpcTsp200Entity opcPart : opc200List){
                //need to account for both part numbers
                //also need to account for an inventory having the same OPC200 part under both names--only count this once (use a Set)
                if(part.getQoh() > 0 &&
                        (part.getPartno().equals(opcPart.getServicePartNumber())
                        || part.getPartno().equals(opcPart.getOcPartNumber()))){
//                    System.out.println("Found a match! Opc part: " + opcPart);
                    opcOnHand.add(new Opc200PartDto(opcPart,
                                                    part.getQoh(),
                                                    part.getPartno().equals(opcPart.getServicePartNumber())));
//                    System.out.println("\tOur set now: (size = " + opcOnHand.size() + ")");
//                    System.out.print("\t\t");
//                    for(Opc200PartDto dto : opcOnHand){
//                        System.out.print(dto.getRank() + ", ");
//                    }
//                    System.out.println();
                }
            }
        }

        List<OpcTsp200DataEntity> opcDataOnHand = new ArrayList<>();
        for(Opc200PartDto partDto : opcOnHand){
            opcDataOnHand.add(partDto.toOpcTsp200DataEntity(paCode, fordPtRepo));
        }

        return opcDataOnHand;
    }

    /**
     * Copies over data from FORD_DEALER_INVENTORY to replace data in OPC_TSP_200_DATA table
     * by comparing it to the OPC_TSP_200 list.
     * If we haven't received new data for this dealer, don't overwrite the old data.
     */
    public void updateOpc200Data(String paCode){
        //If there's no data for this PA code in ford_dealer_inventory, don't override our old OPC data
        if(fordDealerInventoryRepo.findFirstByPaCode(paCode) != null){
            List<OpcTsp200DataEntity> newOpc200Data = getOpc200Data(paCode);
            opcTsp200DataRepo.deleteAllByPaCode(paCode);
            try{
                opcTsp200DataRepo.saveAll(newOpc200Data);
            } catch (Exception e){
                for(OpcTsp200DataEntity part : newOpc200Data){
                    try{
                        opcTsp200DataRepo.saveAndFlush(part);
                    } catch (Exception f){
                        f.printStackTrace();
                        System.out.println("Unable to save the following OPC part for P&A code " + paCode + ": " + part);
                        //often caused because QOH is > 5 digits long (a data error)
                    }
                }
            }

        } else{
            System.out.println("No OPC inventory data received for PA code " + paCode + ".");
        }
    }

    /**
     * Takes an inventory breakdown performance snapshot for a particular dealer.
     * If we haven't received new data from this dealer from Ford, we just copy our last snapshot.
     */
    public void takePerformanceSnapshot(String paCode){
        if(fordDealerInventoryRepo.findFirstByPaCode(paCode) != null){
            takePerformanceSnapshotFromInventory(paCode);
        } else{
            copyLastSnapshotForToday(paCode);
        }
    }

    /**
     * Saves a performance snapshot for a particular dealer's inventory breakdown by brand.
     * The "snapshot date" is LocalDate.now().
     */
    public void takePerformanceSnapshotFromInventory(String paCode){
        Integer opcQoh = opcTsp200DataRepo.countAllByPaCode(paCode);
        Integer opcValue = opcTsp200DataRepo.sumPartCostCentsByPaCode(paCode);

        List<OpcKpiDto> brandData = opcWeeklyPerformanceRepo.findBrandAndValueAndSkuByPaCode(paCode);

        OpcWeeklyPerformanceEntity snapshot = new OpcWeeklyPerformanceEntity();
        snapshot.setPaCode(paCode);
        snapshot.setSnapshotDate(LocalDate.now());
        snapshot.setQoh(opcQoh);
        snapshot.setOpcValueCents(opcValue);
        snapshot.setTotalSku(0); //default these to zeros: may not be data for all of them
        snapshot.setTotalValueCents(0);
        snapshot.setMcSku(0);
        snapshot.setMcValueCents(0);
        snapshot.setFordSku(0);
        snapshot.setFordValueCents(0);
        snapshot.setOtherOcSku(0);
        snapshot.setOtherOcValueCents(0);

        int ocValue = 0;
        int ocSku = 0;
        int totalValue = 0;
        int totalSku = 0;

        for(OpcKpiDto brandRow : brandData){
            Double b = brandRow.getValue() * 100;
            Integer valueCents = b.intValue();

            totalValue += valueCents;
            totalSku += brandRow.getSku();

            String brand = (brandRow.getBrand() == null) ? "" : brandRow.getBrand();
            switch(brand){
                case "Q":   //OnmiCraft
                            ocValue = valueCents;
                            ocSku = brandRow.getSku();
                            break;
                case "A":   //Motorcraft
                            snapshot.setMcSku(brandRow.getSku());
                            snapshot.setMcValueCents(valueCents);
                            break;
                default:    //BlueBox or null brand (count both as Ford)
                            snapshot.setFordSku(snapshot.getFordSku() + brandRow.getSku());
                            snapshot.setFordValueCents(snapshot.getFordValueCents() + valueCents);
                            break;
            }

        }

        snapshot.setTotalSku(totalSku);
        snapshot.setTotalValueCents(totalValue);
        //subtract OPC from other OC parts
        snapshot.setOtherOcSku(ocSku - opcQoh);
        snapshot.setOtherOcValueCents(ocValue - opcValue);

        opcWeeklyPerformanceRepo.save(snapshot);
    }

    /**
     * Copies the most recent performance snapshot to today for a certain dealer.
     * (Useful for when we don't receive new data from Ford, but still want a snapshot).
     */
    public void copyLastSnapshotForToday(String paCode){
        OpcWeeklyPerformanceEntity latestSnapshot = opcWeeklyPerformanceRepo.findFirstByPaCodeOrderBySnapshotDateDesc(paCode);
        if(latestSnapshot != null){
            latestSnapshot.setSnapshotDate(LocalDate.now());
            opcWeeklyPerformanceRepo.save(latestSnapshot);
        } else{ //if we don't have any prior snapshot data, return all nulls
            latestSnapshot = new OpcWeeklyPerformanceEntity();
            latestSnapshot.setPaCode(paCode);
            latestSnapshot.setSnapshotDate(LocalDate.now());
            opcWeeklyPerformanceRepo.save(latestSnapshot);
        }
    }

}
