package com.example.subjects.app.subject.repos;

import com.example.subjects.app.subject.models.Subject;

import java.util.ArrayList;

/**
 * Created by vivek on 07/03/18.
 */

public interface SubjectRepository {

    void addSubject(Subject subject);

    void getSubjects();

    interface SubjectRepositoryInterface{
        void gotSubjects(ArrayList<Subject> subjects);
    }

}
