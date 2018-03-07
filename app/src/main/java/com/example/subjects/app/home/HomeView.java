package com.example.subjects.app.home;

import com.example.subjects.app.subject.models.Subject;

import java.util.ArrayList;

/**
 * Created by vivek on 07/03/18.
 */

public interface HomeView {

    void doInit();

    void showLoading();
    void hideLoading();

    void showError();

    void showEmptyState();

    void gotSubjects(ArrayList<Subject> subjects);
    void subjectRemoved(Subject subject, int position);

}
