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