package com.example.subjects.app.home;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.subjects.BaseActivity;
import com.example.subjects.R;
import com.example.subjects.app.subject.addSubject.AddSubjectActivity;
import com.example.subjects.databinding.ActivityHomeBinding;

public class HomeActivity extends BaseActivity implements HomeView {
    private static final String TAG = HomeActivity.class.getSimpleName();

    ActivityHomeBinding dataBinding;

    public static void start(Context context) {
        Log.d(TAG, "start() called with: context = [" + context + "]");
        Intent starter = new Intent(context, HomeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

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

    }
}
