package com.admi.data.repositories;

import com.admi.data.entities.MixDealersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MixDealersRepository extends JpaRepository<MixDealersEntity, Long> {

	MixDealersEntity findByDealerCode(String dealerCode);

	List<MixDealersEntity> findAllByEndDateIsNull();

	MixDealersEntity findByDealerId(Long dealerId);

}
