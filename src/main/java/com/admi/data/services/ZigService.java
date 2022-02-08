package com.admi.data.services;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.ZigEntity;
import com.admi.data.repositories.ZigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZigService {

	@Autowired
	ZigRepository zigRepo;

	public void saveAsZig(List<AipInventoryEntity> inventory, String paCode) {
		deleteCurrentZig(paCode);

		List<ZigEntity> zigEntities = inventory
				.stream()
//				.filter(part -> part.getQoh() > 0)
				.map(part -> part.toZigEntity(paCode))
				.collect(Collectors.toList());
		try {
			zigRepo.saveAll(zigEntities);
		} catch(Exception e) {
			for (ZigEntity part : zigEntities) {
				try {
					zigRepo.save(part);
				} catch (Exception f) {
					System.out.println("ZIG Part not saved - "
							+ "P&A Code: " + paCode
							+ " Part Number: " + part.getPartNo()
							+ " Desc: " + part.getDes());
				}
			}
		}
	}

	public void deleteCurrentZig(String paCode) {
		zigRepo.deleteAllByPaCode(paCode);
	}

	public void removeCoresThenSaveAsZig(List<AipInventoryEntity> inventory, String paCode) {

	}

}
