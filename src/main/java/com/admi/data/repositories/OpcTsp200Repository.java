package com.admi.data.repositories;

import com.admi.data.entities.OpcTsp200Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpcTsp200Repository  extends JpaRepository<OpcTsp200Entity, Long> {
    List<OpcTsp200Entity> findAll();
}
