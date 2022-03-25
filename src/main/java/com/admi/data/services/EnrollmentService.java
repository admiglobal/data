package com.admi.data.services;

import com.admi.data.entities.EnrollmentEntity;
import com.admi.data.repositories.AipEnrollmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    AipEnrollmentsRepository aipEnrollRepo;

    public List<EnrollmentEntity> getAllDealers() {
        List<? extends EnrollmentEntity> aipDealers = getAllAipDealers();

        List<EnrollmentEntity> dealers = new ArrayList<>();
        dealers.addAll(aipDealers);

        return dealers;
    }

    public List<? extends EnrollmentEntity> getAllAipDealers() {
        return aipEnrollRepo.findAllByOrderByPaCode();
    }
}
