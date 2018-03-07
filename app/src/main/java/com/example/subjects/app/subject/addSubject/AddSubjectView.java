package com.example.subjects.app.subject.addSubject;

import com.example.subjects.app.subject.models.Subject;

/**
 * Created by vivek on 07/03/18.
 */

public interface AddSubjectView {


    void doInit();

    void showTitleError();
    void showDescError();
    void showImageError();

    void subjectAdded(Subject subject);
    void subjectAdditionFailed(Subject subject);


}
