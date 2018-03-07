package com.example.subjects.app.home;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.example.subjects.BaseActivity;
import com.example.subjects.R;
import com.example.subjects.app.subject.addSubject.AddSubjectActivity;
import com.example.subjects.app.subject.models.Subject;
import com.example.subjects.app.subject.repos.MockSubjectRepository;
import com.example.subjects.databinding.ActivityHomeBinding;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity implements HomeView {
    private static final String TAG = HomeActivity.class.getSimpleName();

    private HomePresenter homePresenter;

    private ActivityHomeBinding dataBinding;
    private SubjectAdapter subjectAdapter;

    public static void start(Context context) {
        Log.d(TAG, "start() called with: context = [" + context + "]");
        Intent starter = new Intent(context, HomeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        homePresenter = new HomePresenter(this, MockSubjectRepository.getInstance());

        dataBinding.fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSubjectActivity.start(HomeActivity.this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        doInit();
    }

    @Override
    public void doInit() {
        homePresenter.getSubjects();
    }

    @Override
    public void gotSubjects(ArrayList<Subject> subjects) {
        dataBinding.txtHomeEmptyState.setVisibility(View.GONE);
        dataBinding.recyclerHome.setVisibility(View.VISIBLE);

        dataBinding.recyclerHome.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        subjectAdapter = new SubjectAdapter(this, subjects);
        dataBinding.recyclerHome.setAdapter(subjectAdapter);
    }

    @Override
    public void showEmptyState() {
        dataBinding.recyclerHome.setVisibility(View.GONE);
        dataBinding.txtHomeEmptyState.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        makeToast("Some error happened please try again");
    }

    @Override
    public void showLoading() {
        dataBinding.recyclerHome.setVisibility(View.GONE);
        dataBinding.txtHomeEmptyState.setVisibility(View.GONE);
        dataBinding.progressHome.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        dataBinding.progressHome.setVisibility(View.GONE);
    }
}
