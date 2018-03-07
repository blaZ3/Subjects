package com.example.subjects;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.subjects.app.db.AppDatabase;

/**
 * Created by vivek on 07/03/18.
 */

public class MainApplication extends Application {
    private static final String TAG = MainApplication.class.getSimpleName();

    static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "Subjects_db").allowMainThreadQueries().build();
    }


    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
