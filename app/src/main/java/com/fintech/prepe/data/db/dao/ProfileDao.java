package com.fintech.prepe.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.fintech.prepe.data.db.entities.UserProfile;

@Dao
public interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUserProfile(UserProfile userProfile);

    @Query("SELECT * FROM user_profile WHERE uId = CURRENT_USER_ID")
    LiveData<UserProfile> getUserProfile();


    @Query("SELECT * FROM user_profile WHERE uId = CURRENT_USER_ID")
    UserProfile getRegularUserProfile();


    @Query("DELETE FROM user_profile")
    void deleteUserProfile();
}
