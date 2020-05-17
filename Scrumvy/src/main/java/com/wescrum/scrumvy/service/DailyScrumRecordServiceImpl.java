/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.DailyScrumRecord;
import com.wescrum.scrumvy.repos.DailyScrumRecordRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailyScrumRecordServiceImpl implements DailyScrumRecordServiceInterface {
    
    @Autowired
    DailyScrumRecordRepository dailyScrumRepo;

    @Override
    public List<DailyScrumRecord> getAllDailyScrumRecord() {
        return dailyScrumRepo.findAll();
    }

    @Override
    public void createDailyScrumRecord(DailyScrumRecord dailyScrumRecord) {
       dailyScrumRepo.save(dailyScrumRecord);
    }

    @Override
    public DailyScrumRecord getDailyScrumRecordbyid(Long id) {
        return dailyScrumRepo.getOne(id);
    }

    @Override
    public void deleteDailyScrumRecord(DailyScrumRecord dailyScrumRecord) {
        dailyScrumRepo.delete(dailyScrumRecord);
    }

    @Override
    public void updateDailyScrumRecord(DailyScrumRecord dailyScrumRecord) {
         dailyScrumRepo.save(dailyScrumRecord);
    }
    
}
