package com.admi.data.processes;

import com.admi.data.entities.AipInventoryEntity;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class AisKpiService {

	public void calculateAisKpi(List<AipInventoryEntity> inventory) {
		Map<String, Pair<Long, Long>> admiStatusCountAndTotal = totalByStatus(inventory, AipInventoryEntity::getAdmiStatus);
		Map<String, Pair<Long, Long>> dmsStatusCountAndTotal = totalByStatus(inventory, AipInventoryEntity::getStatus);

	}


	public Map<String, Pair<Long, Long>> totalByStatus(List<AipInventoryEntity> inventory,
	                                                   Function<AipInventoryEntity, String> statusGetter) {
		Map<String, Pair<Long, Long>> statusMap = new HashMap<>();

		Long count = 0L;
		Long total = 0L;

		for (AipInventoryEntity part : inventory) {

			String newStatus = statusGetter.apply(part);

			Pair<Long, Long> countAndTotal = statusMap.get(newStatus);

			count = 1L;
			total = part.getCents() * part.getQoh();

			if (countAndTotal != null) {
				count = count + countAndTotal.getFirst();
				total = total + countAndTotal.getSecond();
			}

			statusMap.put(newStatus, Pair.of(count, total));
		}

		return statusMap;
	}

}
