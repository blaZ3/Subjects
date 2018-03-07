package com.example.subjects.app.subject.repos;

import android.os.AsyncTask;

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

    private static SubjectDAO subjectDAO;

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
        new AddTask(subject, callback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void removeSubject(Subject subject, RemoveSubjectInterface removeSubjectInterface) {
        new RemoveTask(subject, removeSubjectInterface).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void getSubjects(GetSubjectsInterface callback) {
        new GetTask(callback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    static class AddTask extends AsyncTask<Void, Void, Void>{
        private Subject subject;
        private AddSubjectInterface callback;
        public AddTask(Subject subject, AddSubjectInterface callback){
            this.subject = subject;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            subjectDAO.addSubject(subject);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callback.addedSubject(subject);
        }
    }

    static class RemoveTask extends AsyncTask<Void, Void, Void>{
        private Subject subject;
        private RemoveSubjectInterface callback;
        public RemoveTask(Subject subject, RemoveSubjectInterface callback){
            this.subject = subject;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            subjectDAO.removeSubject(subject);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callback.removedSubject(subject);
        }
    }

    static class GetTask extends AsyncTask<Void, Void, Void>{
        private GetSubjectsInterface callback;
        private ArrayList<Subject> subjects;

        public GetTask(GetSubjectsInterface callback){
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            subjects = (ArrayList<Subject>) subjectDAO.getSubjects();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callback.gotSubjects(subjects);
        }

    }



}
