package com.example.subjects;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;

import com.example.subjects.db.AppDatabase;

/**
 * Created by vivek on 07/03/18.
 */

public class MainApplication extends Application {
    private static final String TAG = MainApplication.class.getSimpleName();

    static AppDatabase appDatabase;

    static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = getSharedPreferences("subjects_pref", MODE_PRIVATE);

        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "Subjects_db").build();
    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public static SharedPreferences getSharedPref() {
        return sharedPreferences;
    }
}
