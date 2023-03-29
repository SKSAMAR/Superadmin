package com.fintech.prepe.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.fintech.prepe.data.db.entities.AuthData;

@Dao
public interface AuthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAuthDao(AuthData authData);

    @Query("SELECT * FROM auth_data WHERE uId = CURRENT_USER_ID")
    AuthData getAuthData();

    @Query("DELETE FROM auth_data")
    void deleteAuthData();

}
