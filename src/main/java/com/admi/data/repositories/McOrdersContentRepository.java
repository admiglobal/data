package com.admi.data.repositories;

import com.admi.data.entities.McOrdersContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface McOrdersContentRepository extends JpaRepository<McOrdersContentEntity, Long> {

	List<McOrdersContentEntity> findAllByPaCodeAndOrderNumber(String paCode, String orderNumber);
	List<McOrdersContentEntity> findAllByOrderNumber(String orderNumber);

}
