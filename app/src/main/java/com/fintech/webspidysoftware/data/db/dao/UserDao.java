package com.fintech.webspidysoftware.data.db.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.fintech.webspidysoftware.data.db.entities.User;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(User user);

    @Query("SELECT * FROM user WHERE uId = CURRENT_USER_ID")
    LiveData<User> getUser();


    @Query("SELECT * FROM user WHERE uId = CURRENT_USER_ID")
    User getRegularUser();


    @Query("DELETE FROM user")
    void loggedOutUser();

}
