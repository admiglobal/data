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

    public void tester(String paCode){
        //testing QL dealers list
//        List<DealerMasterEntity> quickLaneDealers = dealerMasterRepo.findAllQuickLaneDealers();
//        int count = 1;
//
//        for(DealerMasterEntity dealer : quickLaneDealers){
//            System.out.print(count++ + ": ");
//            System.out.println(dealer);
//        }

        //testing performance snapshot method
            //issues: SpringBoot doesn't want to convert LocalDate to sql.Date. IDK why.
//        takePerformanceSnapshot("02960");

        //testing OPC data process
//        List<OpcTsp200DataEntity> newOpc200Data = opcTsp200DataRepo.findAllByPaCodeFromFordDealerInventory("02960");
//        System.out.println("Number of new parts found: " + newOpc200Data.size());
    }

//    @Async("asyncExecutor")
//    @Scheduled(cron="0 0 23 L * ?")
    public void takeMonthEndSnapshots(){
        List<DealerMasterEntity> quickLaneDealers = dealerMasterRepo.findAllQuickLaneDealers();

        for(DealerMasterEntity dealer : quickLaneDealers){
            takePerformanceSnapshot(dealer.getPaCode());
        }
    }

    /**
     * Updates OPC_TSP_200_DATA and takes a performance snapshot.
     * If we didn't receive new inventory data, doesn't delete old data but still takes a new snapshot.
     */
    public void runOpcProcess(){
        List<DealerMasterEntity> quickLaneDealers = dealerMasterRepo.findAllQuickLaneDealers();

        for(DealerMasterEntity dealer : quickLaneDealers){
            String paCode = dealer.getPaCode();
            updateOpc200Data(paCode);
            takePerformanceSnapshot(paCode); //take snapshot AFTER updating
        }
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
            opcTsp200DataRepo.saveAll(newOpc200Data);
        } else{
            System.out.println("No OPC inventory data received for PA code " + paCode + ".");
        }
    }

    /**
     * Saves a performance snapshot for a particular dealer's inventory breakdown by brand.
     * The "snapshot date" is LocalDate.now().
     */
    public void takePerformanceSnapshot(String paCode){
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

}
