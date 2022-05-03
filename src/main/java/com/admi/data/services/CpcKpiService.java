package com.admi.data.services;

import com.admi.data.entities.*;
import com.admi.data.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CpcKpiService {

    @Autowired
    AipInventoryRepository inventoryRepo;

    @Autowired
    CpcKpiRepository kpiRepo;

    @Autowired
    CpcDealerProfileRepository cpcDealerProfileRepo;

    @Autowired
    CpcPartsListsRepository cpcPartsListsRepo;

    @Autowired
    CpcPartsOnHandRepository cpcPartsOnHandRepo;

    @Autowired
    CpcObjectivesRepository cpcObjectivesRepo;

    @Autowired
    PriceTapeRepository priceTapeRepo;

    public void runCpcDealers(LocalDate inventoryDate) {

        List<CpcDealerProfileEntity> profiles = cpcDealerProfileRepo.findAll();

        for (CpcDealerProfileEntity profile : profiles) {
            runSingleCpcDealer(profile.getDealerId(), inventoryDate, profile.getPartsList(), profile.getTierList());
        }

//        Capital Ford (00978) - 951L
//        Capitol Ford (06637) - 3051L
    }

    public void runSingleCpcDealer(Long dealerId, LocalDate inventoryDate, String partsList, Short tierList) {
        calculateCpcKpi(dealerId, inventoryDate, partsList, tierList);
    }

    private BigDecimal getTotalInvestment(List<AipInventoryEntity> inventory, List<PriceTapeEntity> priceTapeParts) {

        Map<String, AipInventoryEntity> mappedDealerParts = inventory.stream()
                .collect(Collectors.toMap(AipInventoryEntity::getPartNo, entity -> entity));
        Map<String, PriceTapeEntity> mappedPriceTapeParts = priceTapeParts.stream()
                .collect(Collectors.toMap(PriceTapeEntity::getPartNo, entity -> entity));
        List<BigDecimal> investments = new ArrayList<>();

        mappedDealerParts.forEach((key, value) -> investments
                .add(mappedPriceTapeParts.get(key).getPcValue().multiply(BigDecimal.valueOf(value.getQoh()))));

        BigDecimal totalInvestment = BigDecimal.ZERO;

        for (BigDecimal investment : investments) {
            totalInvestment = totalInvestment.add(investment);
        }

//        totalInvestment = totalInvestment.setScale(0, RoundingMode.HALF_EVEN);

        return totalInvestment.setScale(0, RoundingMode.HALF_EVEN);
    }

    private List<AipInventoryEntity> getOnHandCpcParts(List<AipInventoryEntity> inventory, List<CpcPartsListsEntity> cpcParts) {

        Map<String, String> cpcPartsSku = cpcParts.stream()
                .collect(Collectors.toMap(CpcPartsListsEntity::getPrimarySku,
                        part -> Optional.ofNullable(part.getAlternateSku()).orElse("")));
        Map<String, List<String>> formattedCpcPartsSku = new HashMap<>();
        List<AipInventoryEntity> onHandCpcParts = new ArrayList<>();
        List<CpcPartsOnHandEntity> onHandEntities = new ArrayList<>();

        //Reformat alternateSku Strings into List<String> for use in comparisons
        cpcPartsSku.forEach((key, value) -> {
                String[] alternates = value.split("[\",]");
                List<String> formattedAlternates = Arrays.asList(alternates);
                formattedCpcPartsSku.put(key, formattedAlternates);
        });

        for (AipInventoryEntity part : inventory) {
            if (formattedCpcPartsSku.containsKey(part.getPartNo())) {
                onHandCpcParts.add(part);
                onHandEntities.add(new CpcPartsOnHandEntity(
                        part.getDealerId(),
                        part.getDataDate(),
                        part.getPartNo(),
                        part.getQoh()));
                formattedCpcPartsSku.remove(part.getPartNo());
            }
            else {
                Iterator<Map.Entry<String, List<String>>> iterator = formattedCpcPartsSku.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<String, List<String>> altPart = iterator.next();
                    if (altPart.getValue().contains(part.getPartNo())) {
                        onHandCpcParts.add(part);
                        onHandEntities.add(new CpcPartsOnHandEntity(
                                part.getDealerId(),
                                part.getDataDate(),
                                altPart.getKey(),
                                part.getQoh()));
                        iterator.remove();
                        break;
                    }
                }
            }
        }
        cpcPartsOnHandRepo.saveAll(onHandEntities);
        return onHandCpcParts;
    }

    public void calculateCpcKpi(Long dealerId, LocalDate dataDate, String partsList, Short tierList) {

        List<AipInventoryEntity> collisionParts = inventoryRepo.findAllCollisionPartsInInventory(dealerId, dataDate);
        List<AipInventoryEntity> nonCollisionParts = inventoryRepo.findAllNonCollisionPartsInInventory(dealerId, dataDate);
        List<CpcPartsListsEntity> cpcParts = cpcPartsListsRepo.findByPartsListAndRankIsLessThanEqual(partsList, tierList);
        List<PriceTapeEntity> allOnHandDealerPartsInPriceTape = priceTapeRepo.findAllOnHandDealerPartsInPriceTape(dealerId, dataDate);
        List<PriceTapeEntity> allCpcListPartsInPriceTape = priceTapeRepo.findAllCpcListPartsInPriceTape(partsList, tierList);
        List<AipInventoryEntity> onHandCpcParts = getOnHandCpcParts(collisionParts, cpcParts);
        CpcObjectivesEntity objectiveMonth = cpcObjectivesRepo.findByObjectiveMonth(dataDate.withDayOfMonth(1));

        long totalCollisionSku = collisionParts.size();
        long totalNonCollisionSku = nonCollisionParts.size();
        long totalCpcListSku = onHandCpcParts.size();

        if (totalCollisionSku > 1 || totalNonCollisionSku > 1) {
            BigDecimal totalCollisionInvestment = getTotalInvestment(collisionParts, allOnHandDealerPartsInPriceTape);
            BigDecimal totalNonCollisionInvestment = getTotalInvestment(nonCollisionParts, allOnHandDealerPartsInPriceTape);
            BigDecimal totalCpcListInvestment = getTotalInvestment(onHandCpcParts, allOnHandDealerPartsInPriceTape);

            BigDecimal cpcListPartsPriceTotal = BigDecimal.ZERO;

            for (PriceTapeEntity part : allCpcListPartsInPriceTape) {
                cpcListPartsPriceTotal = cpcListPartsPriceTotal.add(part.getPcValue());
            }

            BigDecimal cpcListPartsPriceAverage = cpcListPartsPriceTotal.divide(BigDecimal.valueOf(tierList), 2, RoundingMode.HALF_EVEN);

            BigDecimal cpcObjective = cpcListPartsPriceAverage
                    .multiply(BigDecimal.valueOf((objectiveMonth.getCpcPercent() * tierList) - totalCpcListSku));
            cpcObjective = cpcObjective.setScale(0, RoundingMode.HALF_EVEN);

            CpcKpiEntity cpcKpi =  new CpcKpiEntity(
                    dealerId,
                    dataDate,
                    tierList,
                    totalCollisionSku,
                    totalNonCollisionSku,
                    totalCpcListSku,
                    totalCollisionInvestment,
                    totalNonCollisionInvestment,
                    totalCpcListInvestment,
                    cpcObjective);

            kpiRepo.save(cpcKpi);
        }
    }
}
