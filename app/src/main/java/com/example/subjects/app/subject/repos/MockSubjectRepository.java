package com.example.subjects.app.subject.repos;

import com.example.subjects.app.subject.models.Subject;

import java.util.ArrayList;

/**
 * Created by vivek on 07/03/18.
 */

public class MockSubjectRepository implements SubjectRepository {
    private static final String TAG = MockSubjectRepository.class.getSimpleName();

    private boolean fail = false;

    public MockSubjectRepository(boolean fail) {
        this.fail = fail;
    }

    @Override
    public void addSubject(Subject subject, AddSubjectInterface callback) {
        if (fail){
            callback.onError();
            return;
        }

        callback.addedSubject(subject);
    }

    @Override
    public void removeSubject(Subject subject, RemoveSubjectInterface removeSubjectInterface) {
        if (fail){
            removeSubjectInterface.onError();
            return;
        }

        removeSubjectInterface.removedSubject(subject);
    }

    @Override
    public void getSubjects(GetSubjectsInterface callback) {
        if (fail){
            callback.gotSubjects(null);
            return;
        }

        ArrayList<Subject> subjects = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Subject subject = new Subject();
            subject.setTitle("Title "+i);
            subject.setDesc("Desc desc desc desc desc desc desc desc desc desc desc desc desc desc ");
            subject.setImgFilePath("");

            subjects.add(subject);
        }


        callback.gotSubjects(subjects);
    }
}
