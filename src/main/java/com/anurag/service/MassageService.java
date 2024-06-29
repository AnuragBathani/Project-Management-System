package com.anurag.service;

import com.anurag.model.Massage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MassageService {

    Massage sendMassage(Long senderId,Long projectId,String content) throws Exception;


    List<Massage> getMassageByProjectId(Long projectId) throws Exception;
}
