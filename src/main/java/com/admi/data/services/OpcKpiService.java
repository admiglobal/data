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
        System.out.println("Running top 90 OPC dealers");
        String[] top90Dealers = {"04134", "04196", "04281", "03491", "09217", "09469", "00210", "04921", "08124", "03106", "04113", "01699", "05291", "03554", "01319", "08621", "02960", "03318", "03557", "05591", "01533", "06069", "02405", "04107", "05266", "03360", "01167", "01705", "02737", "04809", "01046", "00912", "05222", "01743", "01880", "03938", "05076", "06314", "00378", "09522", "00377", "08254", "06790", "04517", "06431", "02042", "05448", "09566", "05319", "09193", "01650", "03558", "07670", "08027", "00175", "00601", "03832", "00172", "01412", "00048", "00775", "01417", "02175", "06161", "01487", "08110", "20341", "08155", "04544", "03830", "05928", "05982", "06804", "03569", "06043", "03130", "03897", "01307", "04120", "08615", "01741", "04362", "04410", "00169", "01302", "07192", "09324", "02673", "06758"};
        int counter = 0;
        for(String paCode : top90Dealers){
            double completionTime = processSingleOpcDealer(paCode);
            System.out.println("(" + ++counter + "/90) Ran process for " + paCode + " in " + completionTime + " seconds.");
        }
    }

    /**
     * Runs process for a single OPC dealer
     * @return The number of seconds it took to complete
     */
    public double processSingleOpcDealer(String paCode){
        long startTime = System.currentTimeMillis();
        updateOpc200Data(paCode);
        takePerformanceSnapshot(paCode); //take snapshot AFTER updating
        opcTsp200DataRepo.flush();
        long endTime = System.currentTimeMillis();
        Long l = endTime-startTime;
        return (l.doubleValue())/1000;
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
                if(safeReadQoh(part.getQoh()) > 0 &&
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
        Integer opcQoh = zeroIfNull(opcTsp200DataRepo.countAllByPaCode(paCode));
        Integer opcValue = zeroIfNull(opcTsp200DataRepo.sumPartCostCentsByPaCode(paCode));

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

    private static Integer zeroIfNull(Integer number){
        if(number == null) return 0;
        return number;
    }

    private static Long zeroIfNull(Long number){
        if(number == null) return 0L;
        return number;
    }

    private static long ensureUnderFiveDigits(long number){
        if(number > 99999 || number < -99999) return -1;
        return number;
    }

    /**
     * Checks if null or if an unreasonably large amount for QOH.
     * The QOH column in OPC_TSP_200_DATA is only 5 digits long (that is, 99,999 max).
     * A number any higher will throw an error and is too long to be a real QOH number.
     * In this case, we mark the QOH as -1 to indicate something's gone wrong.
     * @param qoh The quantity on hand value
     * @return The non-null QOH value. Will be -1 if the qoh argument is more than 5 digits long.
     */
    private static Long safeReadQoh(Long qoh){
        return ensureUnderFiveDigits(
                    zeroIfNull(qoh));
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
