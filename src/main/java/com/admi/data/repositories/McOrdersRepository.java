package com.admi.data.repositories;

import com.admi.data.entities.McOrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface McOrdersRepository extends JpaRepository<McOrdersEntity, Long> {

	McOrdersEntity findByOrderNumber(String orderNumber);

	List<McOrdersEntity> findAllByPlacedAfterAndPlacedIsNotNull(LocalDateTime date);

	@Query(value = "select OC_ORDER_NUM_S.nextval from dual",
			nativeQuery = true)
	String getOrderNumber();

}
