/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.DailyScrumRecord;
import java.util.List;

public interface DailyScrumRecordServiceInterface {

    public List<DailyScrumRecord> getAllDailyScrumRecord();

    public void createDailyScrumRecord(DailyScrumRecord dailyScrumRecord);

    public DailyScrumRecord getDailyScrumRecordbyid(Long id);

    public void deleteDailyScrumRecord(DailyScrumRecord dailyScrumRecord);

    public void updateDailyScrumRecord(DailyScrumRecord dailyScrumRecord);
}
