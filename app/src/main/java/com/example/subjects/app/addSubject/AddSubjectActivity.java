package com.example.subjects.app.addSubject;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.subjects.BaseActivity;
import com.example.subjects.MainApplication;
import com.example.subjects.Manifest;
import com.example.subjects.R;
import com.example.subjects.app.subject.models.Subject;
import com.example.subjects.app.subject.repos.LocalSubjectRepository;
import com.example.subjects.app.utils.FileHelper;
import com.example.subjects.databinding.ActivityAddSubjectBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;
import com.karumi.dexter.listener.single.BasePermissionListener;

import java.io.File;
import java.util.List;

public class AddSubjectActivity extends BaseActivity implements AddSubjectView {
    private static final String TAG = AddSubjectActivity.class.getSimpleName();

    private static final int PICK_IMAGE = 101;

    private ActivityAddSubjectBinding dataBinding;
    private AddSubjectPresenter addSubjectPresenter;

    private String  imageFilePath = null;

    private String timeOpened;

    public static void start(Context context) {
        Log.d(TAG, "start() called with: context = [" + context + "]");
        Intent starter = new Intent(context, AddSubjectActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_subject);

        timeOpened = ""+System.currentTimeMillis();

        initToolbar();

        addSubjectPresenter = new AddSubjectPresenter(this,
                LocalSubjectRepository.getInstance(MainApplication.getAppDatabase().subjectDAO()));

        dataBinding.btnSubjectAdd.setOnClickListener(addSubjectClickListener);
        dataBinding.imgSubjectImage.setOnClickListener(addImageClickListener);
    }


    private void initToolbar() {
        setSupportActionBar(dataBinding.toolbarAddSubject);
        getSupportActionBar().setTitle("Add Subject");
        dataBinding.toolbarAddSubject.setNavigationIcon(
                getResources().getDrawable(R.drawable.ic_arrow_back));
        dataBinding.toolbarAddSubject.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        doInit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            Uri imageFileUri = data.getData();
            try{
                imageFilePath = FileHelper.savefile(getApplicationContext(), imageFileUri, timeOpened);
                dataBinding.imgSubjectImage.setImageBitmap(
                        BitmapFactory.decodeFile(imageFilePath));
            }catch (Exception ex){
                ex.printStackTrace();
                imageFilePath = null;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!TextUtils.isEmpty(imageFilePath)){
            try{
                new File(imageFilePath).delete();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    View.OnClickListener addImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Dexter.withActivity(AddSubjectActivity.this)
                    .withPermissions(
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new BaseMultiplePermissionsListener(){
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            super.onPermissionsChecked(report);
                            if (report.areAllPermissionsGranted()){

                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                                        PICK_IMAGE);

                            }else if (report.isAnyPermissionPermanentlyDenied()){
                                makeToast(getString(R.string.add_image_rationale_denied));
                            }else {
                                makeToast(getString(R.string.add_image_rationale));
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            super.onPermissionRationaleShouldBeShown(permissions, token);
                        }
                    })
                    .check();
        }
    };

    View.OnClickListener addSubjectClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addSubjectPresenter.addSubject(dataBinding.edtxtSubjectTitle.getText().toString(),
                    dataBinding.edtxtSubjectDesc.getText().toString(),
                    imageFilePath);
        }
    };


    ///////////////////////////////////////////////////////////////////////////
    // View implementations
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void doInit() {

    }

    @Override
    public void showTitleError() {
        makeToast(getString(R.string.error_invalid_title));
    }

    @Override
    public void showDescError() {
        makeToast(getString(R.string.error_invalid_desc));
    }

    @Override
    public void showImageError() {
        makeToast(getString(R.string.error_invalid_image));
    }

    @Override
    public void subjectAdded(Subject subject) {
        makeToast(subject.getTitle()+" added successfully");
        finish();
    }

    @Override
    public void subjectAdditionFailed(Subject subject) {
        makeToast("Failed to add subject "+subject.getTitle()+". Please try again");
    }
}
