package com.example.subjects.app.home;

import android.support.annotation.NonNull;

import com.example.subjects.app.subject.models.Subject;
import com.example.subjects.app.subject.repos.SubjectRepository;

import java.util.ArrayList;

/**
 * Created by vivek on 07/03/18.
 */

public class HomePresenter {
    private static final String TAG = HomePresenter.class.getSimpleName();

    HomeView homeView;
    SubjectRepository subjectRepository;

    public HomePresenter(HomeView homeView, SubjectRepository subjectRepository){
        this.homeView = homeView;
        this.subjectRepository = subjectRepository;
    }

    public void getSubjects(){
        homeView.showLoading();
        subjectRepository.getSubjects(new SubjectRepository.GetSubjectsInterface() {
            @Override
            public void gotSubjects(ArrayList<Subject> subjects) {
                homeView.hideLoading();
                if (subjects == null || subjects.size() < 1){
                    homeView.showEmptyState();
                }else {
                    homeView.gotSubjects(subjects);
                }
            }

            @Override
            public void onError() {
                homeView.hideLoading();
                homeView.showError();
            }
        });
    }

    public void deleteSubject(final Subject subject, final int position){
        subjectRepository.removeSubject(subject, new SubjectRepository.RemoveSubjectInterface() {
            @Override
            public void removedSubject(Subject subject) {
                homeView.subjectRemoved(subject, position);
            }

            @Override
            public void onError() {
                homeView.showError();
            }
        });

    }

}
