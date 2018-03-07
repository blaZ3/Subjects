package com.example.subjects.app.subject.addSubject;

/**
 * Created by vivek on 07/03/18.
 */

public interface AddSubjectView {


    void doInit();

    void showTitleError();
    void showDescError();
    void showImageError();

    void doAddSubject();


}
