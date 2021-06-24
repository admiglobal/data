package com.admi.data.repositories;

import com.admi.data.entities.McPartsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface McPartsRepository extends JpaRepository<McPartsEntity, Long> {

	List<McPartsEntity> findAllByReleasedIsNotNull();

}
