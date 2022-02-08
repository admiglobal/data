package com.admi.data.services;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.RimHistoryEntity;
import com.admi.data.repositories.RimHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RimHistoryService {

	@Autowired
	RimHistoryRepository rimRepo;

	public void processRimParts(Long dealerId, List<AipInventoryEntity> inventory) {

		try {
			Map<Boolean, List<AipInventoryEntity>> sortedPartsList = sortPartsForMfgControl(inventory);

			Map<String, RimHistoryEntity> currentControlledParts = rimRepo.findMapByDealerIdAndPhaseOutIsNull(dealerId);
			Map<String, RimHistoryEntity> pastControlledParts = rimRepo.findMapByDealerIdAndPhaseOutIsNotNull(dealerId);

			List<AipInventoryEntity> newControlledPartsToAdd = compareListToDatabase(sortedPartsList.get(true), currentControlledParts, false);
			List<AipInventoryEntity> controlledPartsToUpdate = compareListToDatabase(sortedPartsList.get(false), currentControlledParts, true);
			List<AipInventoryEntity> partsNotToUpdate = compareListToDatabase(sortedPartsList.get(false), currentControlledParts, false);
			List<AipInventoryEntity> pastControlledPartsToAdd = compareListToDatabase(partsNotToUpdate, pastControlledParts, false);

			List<RimHistoryEntity> newToAdd = createRimHistoryEntities(newControlledPartsToAdd, LocalDate.now(), null);
			List<RimHistoryEntity> pastToAdd = createRimHistoryEntities(pastControlledPartsToAdd, LocalDate.now(), LocalDate.now());

			List<String> partsToFind = controlledPartsToUpdate
					.stream()
					.map(AipInventoryEntity::getPartNo)
					.collect(Collectors.toList());
			List<RimHistoryEntity> partsToUpdate = rimRepo.findAllByDealerIdAndPartnoInAndPhaseOutIsNull(dealerId, partsToFind);

			partsToUpdate.forEach(v -> v.setPhaseOut(LocalDate.now()));

			rimRepo.saveAll(newToAdd);
			rimRepo.saveAll(pastToAdd);
			rimRepo.saveAll(partsToUpdate);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("RIM History not saved for Dealer Id: " + dealerId);
		}
	}

	private Map<Boolean, List<AipInventoryEntity>> sortPartsForMfgControl(List<AipInventoryEntity> inventory) {
		Map<Boolean, List<AipInventoryEntity>> mfgControlledParts = new HashMap<>();
		List<AipInventoryEntity> trueList = new ArrayList<>();
		List<AipInventoryEntity> falseList = new ArrayList<>();

		for (AipInventoryEntity part : inventory) {
			if (part.getMfgControlled() != null) {
				if (part.getMfgControlled())
					trueList.add(part);
				else
					falseList.add(part);
			}
		}
		mfgControlledParts.put(true, trueList);
		mfgControlledParts.put(false, falseList);

		return mfgControlledParts;
	}

	private List<AipInventoryEntity> compareListToDatabase(List<AipInventoryEntity> partList,
	                                                       Map<String, RimHistoryEntity> inDatabase,
	                                                       boolean findTrue) {
		List<AipInventoryEntity> partNotInList = new ArrayList<>();

		for (AipInventoryEntity part : partList) {
			if (inDatabase.containsKey(part.getPartNo().toUpperCase()) == findTrue) {
				partNotInList.add(part);
			}
		}
		return partNotInList;
	}

	private List<RimHistoryEntity> createRimHistoryEntities(List<AipInventoryEntity> listToAlter, LocalDate phaseIn, LocalDate phaseOut) {
		List<RimHistoryEntity> rimHistoryEntities = new ArrayList<>();

		for (AipInventoryEntity part : listToAlter) {
			RimHistoryEntity rimHistory = new RimHistoryEntity();
			rimHistory.setDealerId(part.getDealerId());
			rimHistory.setPhaseIn(phaseIn);
			rimHistory.setPhaseOut(phaseOut);
			rimHistoryEntities.add(rimHistory);
		}
		return rimHistoryEntities;
	}

	public void addOrUpdateRimParts(Long dealerId, List<AipInventoryEntity> inventory) {
		for (AipInventoryEntity part : inventory) {
			if (part.getMfgControlled() != null) {
				if (part.getMfgControlled()) {
					try {
						rimRepo.insertNewRimPart(dealerId, part.getPartNo());
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Couldn't insert RIM part for " + dealerId + " - " + part.getPartNo());
					}
				} else {
					try {
						rimRepo.updateOldRimPart(dealerId, part.getPartNo());
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Couldn't update RIM part for " + dealerId + " - " + part.getPartNo());
					}
				}
			}
		}
	}

}
