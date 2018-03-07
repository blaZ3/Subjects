package com.example.subjects;

import com.example.subjects.app.home.HomePresenter;
import com.example.subjects.app.home.HomeView;
import com.example.subjects.app.subject.models.Subject;
import com.example.subjects.app.subject.repos.MockSubjectRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class HomeScreenPresenterTests {

    private HomePresenter homePresenter;

    @Mock
    private HomeView homeView;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_home_presenter_get_subjects(){
        homePresenter = new HomePresenter(homeView, new MockSubjectRepository(false));

        homePresenter.getSubjects();

        Mockito.verify(homeView, Mockito.times(1)).showLoading();
        Mockito.verify(homeView, Mockito.times(1))
                .gotSubjects((ArrayList<Subject>) Mockito.any());
        Mockito.verify(homeView, Mockito.times(1)).hideLoading();
    }

    @Test
    public void test_home_presenter_get_subjects_fail(){
        homePresenter = new HomePresenter(homeView, new MockSubjectRepository(true));

        homePresenter.getSubjects();

        Mockito.verify(homeView, Mockito.times(1)).showLoading();
        Mockito.verify(homeView, Mockito.times(1)).hideLoading();
        Mockito.verify(homeView, Mockito.times(1)).showEmptyState();
    }

}