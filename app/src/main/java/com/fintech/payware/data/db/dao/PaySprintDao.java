package com.fintech.payware.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.fintech.payware.data.db.entities.PaySprintApi;

@Dao
public interface PaySprintDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(PaySprintApi paySprintApi);


    @Query("SELECT * FROM paysprint_api WHERE uId = CURRENT_USER_ID")
    PaySprintApi getRegularPaySprint();

    @Query("DELETE FROM paysprint_api")
    void loggedOut();


}
