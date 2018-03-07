package com.example.subjects;

import com.example.subjects.app.addSubject.AddSubjectPresenter;
import com.example.subjects.app.addSubject.AddSubjectView;
import com.example.subjects.app.subject.models.Subject;
import com.example.subjects.app.subject.repos.MockSubjectRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Created by vivek on 07/03/18.
 */

public class AddSubjectPresenterTests {

    AddSubjectPresenter addSubjectPresenter;

    @Mock
    AddSubjectView addSubjectView;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        addSubjectPresenter = new AddSubjectPresenter(addSubjectView, new MockSubjectRepository(false));
    }


    @Test
    public void test_add_subject_present_show_title_error(){
        addSubjectPresenter.addSubject("", "desc", "");
        Mockito.verify(addSubjectView, Mockito.times(1))
                .showTitleError();
    }

    @Test
    public void test_add_subject_present_show_desc_error(){
        addSubjectPresenter.addSubject("title", "", "");
        Mockito.verify(addSubjectView, Mockito.times(1))
                .showDescError();
    }

    @Test
    public void test_add_subject_present_show_image_error(){
        addSubjectPresenter.addSubject("title", "desc", "invalid file path");
        Mockito.verify(addSubjectView, Mockito.times(1))
                .showImageError();
    }


    @Test
    public void test_add_subject_present_add(){
        addSubjectPresenter.addSubject("title", "desc", "");
        Mockito.verify(addSubjectView, Mockito.times(1))
                .subjectAdded((Subject) Mockito.any());
    }

    @Test
    public void test_add_subject_present_add_fail(){
        addSubjectPresenter = new AddSubjectPresenter(addSubjectView, new MockSubjectRepository(true));
        addSubjectPresenter.addSubject("title", "desc", "");
        Mockito.verify(addSubjectView, Mockito.times(1))
                .subjectAdditionFailed((Subject) Mockito.any());
    }

}
