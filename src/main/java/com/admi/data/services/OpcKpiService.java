package com.admi.data.services;

import com.admi.data.dto.OpcKpiDto;
import com.admi.data.entities.DealerMasterEntity;
import com.admi.data.entities.OpcTsp200DataEntity;
import com.admi.data.entities.OpcWeeklyPerformanceEntity;
import com.admi.data.repositories.DealerMasterRepository;
import com.admi.data.repositories.FordDealerInventoryRepository;
import com.admi.data.repositories.OpcTsp200DataRepository;
import com.admi.data.repositories.OpcWeeklyPerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
                takePerformanceSnapshot(paCode); //take snapshot AFTER updating
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("Failed to run OPC process for P&A Code " + paCode + ".");
            }
        }
    }

    /**
     * For testing purposes only
     */
    public void tester(){
        System.out.println(fordDealerInventoryRepo.findFirstByPaCode("00000"));
    }

    /**
     * Copies over data from FORD_DEALER_INVENTORY to replace data in OPC_TSP_200_DATA table
     * by comparing it to the OPC_TSP_200 list.
     * If we haven't received new data for this dealer, don't overwrite the old data.
     */
    public void updateOpc200Data(String paCode){
        //If there's no data for this PA code in ford_dealer_inventory, don't override our old OPC data
        if(fordDealerInventoryRepo.findFirstByPaCode(paCode) != null){
            List<OpcTsp200DataEntity> newOpc200Data = opcTsp200DataRepo.findAllByPaCodeFromFordDealerInventory(paCode);
            opcTsp200DataRepo.deleteAllByPaCode(paCode);
            try{
                opcTsp200DataRepo.saveAll(newOpc200Data);
            } catch (Exception e){
                System.out.println("Unable to save all new OPC_TSP_200_DATA for P&A " + paCode + ".");
                e.printStackTrace();
                for(OpcTsp200DataEntity datan : newOpc200Data){
                    try{
                        opcTsp200DataRepo.save(datan);
                    } catch (Exception f){
                        System.out.println("Unable to save the following part to opc_tsp_200_data for P&A " + paCode + ": " + datan);
                        f.printStackTrace();
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
        Integer opcQoh = opcTsp200DataRepo.findSkuQohByPaCode(paCode);
        Double d = opcTsp200DataRepo.findTotalOpcValueByPaCode(paCode) * 100;
        Integer opcValue = d.intValue();

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
