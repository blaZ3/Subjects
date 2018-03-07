package com.example.subjects.app.subject.repos;

import com.example.subjects.app.db.AppDatabase;
import com.example.subjects.app.db.SubjectDAO;
import com.example.subjects.app.subject.models.Subject;

import java.util.ArrayList;

/**
 * Created by vivek on 07/03/18.
 */

public class LocalSubjectRepository implements SubjectRepository {
    private static final String TAG = LocalSubjectRepository.class.getSimpleName();

    private static LocalSubjectRepository instance = null;

    private SubjectDAO subjectDAO;

    public static LocalSubjectRepository getInstance(SubjectDAO subjectDAO) {
        if (instance == null){
            instance = new LocalSubjectRepository(subjectDAO);
        }
        return instance;
    }

    private LocalSubjectRepository(SubjectDAO subjectDAO) {
        this.subjectDAO = subjectDAO;
    }

    @Override
    public void addSubject(Subject subject, AddSubjectInterface callback) {
        subjectDAO.addSubject(subject);
        callback.addedSubject(subject);
    }

    @Override
    public void removeSubject(Subject subject, RemoveSubjectInterface removeSubjectInterface) {
        subjectDAO.removeSubject(subject);
        removeSubjectInterface.removedSubject(subject);
    }

    @Override
    public void getSubjects(GetSubjectsInterface callback) {
        callback.gotSubjects((ArrayList<Subject>) subjectDAO.getSubjects());
    }
}
