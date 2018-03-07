package com.example.subjects.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.subjects.app.subject.models.Subject;

/**
 * Created by vivek on 07/03/18.
 */

@Database(entities = {Subject.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SubjectDAO subjectDAO();
}
