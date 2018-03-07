package com.example.subjects.app.subject.addSubject;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import com.example.subjects.BaseActivity;
import com.example.subjects.R;
import com.example.subjects.databinding.ActivityAddSubjectBinding;

public class AddSubjectActivity extends BaseActivity implements AddSubjectView {
    private static final String TAG = AddSubjectActivity.class.getSimpleName();

    ActivityAddSubjectBinding dataBinding;

    public static void start(Context context) {
        Log.d(TAG, "start() called with: context = [" + context + "]");
        Intent starter = new Intent(context, AddSubjectActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_subject);


    }

    @Override
    protected void onResume() {
        super.onResume();
        doInit();
    }

    ///////////////////////////////////////////////////////////////////////////
    // View implementations
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void doInit() {

    }

    @Override
    public void showTitleError() {

    }

    @Override
    public void showDescError() {

    }

    @Override
    public void showImageError() {

    }

    @Override
    public void doAddSubject() {

    }
}
