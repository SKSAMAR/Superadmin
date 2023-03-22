package com.fintech.petoindia.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.fintech.petoindia.data.db.entities.Report;

@Dao
public interface ReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReport(Report report);

    @Query("SELECT * FROM report WHERE uId = CURRENT_USER_ID")
    Report getReport();
}
