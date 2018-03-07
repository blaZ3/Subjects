package com.example.subjects.app.subject.repos;

import com.example.subjects.app.subject.models.Subject;

import java.util.ArrayList;

/**
 * Created by vivek on 07/03/18.
 */

public interface SubjectRepository {

    void addSubject(Subject subject, AddSubjectInterface callback);

    void getSubjects(GetSubjectsInterface callback);

    interface AddSubjectInterface{
        void addedSubject(Subject subject);
        void onError();
    }

    interface GetSubjectsInterface{
        void gotSubjects(ArrayList<Subject> subjects);
        void onError();
    }


}
