package com.admi.data.processes;

import com.admi.data.repositories.AipInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessService {

	@Autowired
	AipInventoryRepository inventoryRepo;



}
