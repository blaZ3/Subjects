package com.example.subjects.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.subjects.app.subject.models.Subject;
import com.example.subjects.app.subject.repos.SubjectRepository;

import java.util.List;

/**
 * Created by vivek on 07/03/18.
 */

@Dao
public interface SubjectDAO {

    @Insert
    void addSubject(Subject subject);

    @Delete
    void removeSubject(Subject subject);

    @Query("select * from Subject")
    List<Subject> getSubjects();
}
