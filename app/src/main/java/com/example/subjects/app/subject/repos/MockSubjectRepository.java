package com.example.subjects.app.subject.repos;

import com.example.subjects.app.subject.models.Subject;

/**
 * Created by vivek on 07/03/18.
 */

public class MockSubjectRepository implements SubjectRepository {
    private static final String TAG = MockSubjectRepository.class.getSimpleName();

    static MockSubjectRepository instance;

    public static MockSubjectRepository getInstance() {
        if (instance == null){
            instance = new MockSubjectRepository();
        }
        return instance;
    }

    @Override
    public void addSubject(Subject subject, AddSubjectInterface callback) {
        callback.addedSubject(subject);
    }

    @Override
    public void getSubjects(GetSubjectsInterface callback) {

    }
}
