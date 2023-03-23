package com.fintech.payware.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.fintech.payware.data.db.dao.AuthDao;
import com.fintech.payware.data.db.dao.PaySprintDao;
import com.fintech.payware.data.db.dao.ProfileDao;
import com.fintech.payware.data.db.dao.ReportDao;
import com.fintech.payware.data.db.dao.UserDao;
import com.fintech.payware.data.db.entities.AuthData;
import com.fintech.payware.data.db.entities.PaySprintApi;
import com.fintech.payware.data.db.entities.Report;
import com.fintech.payware.data.db.entities.User;
import com.fintech.payware.data.db.entities.UserProfile;

@Database(entities = {AuthData.class, User.class, PaySprintApi.class, UserProfile.class, Report.class}, version = 16, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase appDatabase = null;

    public abstract UserDao getUserDao();

    public abstract ReportDao getReportDao();

    public abstract AuthDao getAuthDao();

    public abstract PaySprintDao getPaySprint();

    public abstract ProfileDao getUserProfileDao();

    public static synchronized AppDatabase getAppDatabase(Context context){

        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "myDB")
                    .allowMainThreadQueries()
                    .addMigrations()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return appDatabase;
    }
}
