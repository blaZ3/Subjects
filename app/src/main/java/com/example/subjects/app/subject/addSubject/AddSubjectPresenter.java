package com.example.subjects.app.subject.addSubject;

import android.text.TextUtils;

import com.example.subjects.app.subject.models.Subject;
import com.example.subjects.app.subject.repos.SubjectRepository;

import java.io.File;

/**
 * Created by vivek on 07/03/18.
 */

public class AddSubjectPresenter {
    private static final String TAG = AddSubjectPresenter.class.getSimpleName();

    private SubjectRepository subjectRepository;
    private AddSubjectView subjectView;

    public AddSubjectPresenter(AddSubjectView subjectView, SubjectRepository subjectRepository){
        this.subjectRepository = subjectRepository;
        this.subjectView = subjectView;
    }


    public void addSubject(String title, String desc, String imgFilePath){

        if(TextUtils.isEmpty(title)){
            subjectView.showTitleError();
            return;
        }

        if (TextUtils.isEmpty(desc)){
            subjectView.showDescError();
            return;
        }

        if (!TextUtils.isEmpty(imgFilePath)){
            File file = new File(imgFilePath);
            if (!file.exists()){
                subjectView.showImageError();
                return;
            }
        }

        final Subject subject = new Subject();
        subject.setTitle(title);
        subject.setDesc(desc);
        subject.setImgFilePath(imgFilePath);
        subjectRepository.addSubject(subject, new SubjectRepository.AddSubjectInterface() {
            @Override
            public void addedSubject(Subject subject) {
                subjectView.subjectAdded(subject);
            }

            @Override
            public void onError() {
                subjectView.subjectAdditionFailed(subject);
            }
        });


    }

}
