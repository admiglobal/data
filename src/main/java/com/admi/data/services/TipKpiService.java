package com.admi.data.services;

import com.admi.data.repositories.TipOrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipKpiService {

    @Autowired
    TipOrderDetailRepository tipOrderDetailRepo;


}
