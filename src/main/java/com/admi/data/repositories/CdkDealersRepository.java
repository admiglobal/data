package com.admi.data.repositories;

import com.admi.data.entities.CdkDealersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CdkDealersRepository extends JpaRepository<CdkDealersEntity, Long> {

	List<CdkDealersEntity> findAllByEndDateIsNullAndInstallationIsNotNull();
	List<CdkDealersEntity> findAllByEndDateIsNullAndInstallationIsNull();

	CdkDealersEntity findByDealerId(String dealerId);

	CdkDealersEntity findAllByAdmiDealerId(Long dealerId);

}
