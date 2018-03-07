package com.example.subjects.app.subject.addSubject;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.subjects.BaseActivity;
import com.example.subjects.R;
import com.example.subjects.app.subject.models.Subject;
import com.example.subjects.app.subject.repos.MockSubjectRepository;
import com.example.subjects.app.utils.FileHelper;
import com.example.subjects.databinding.ActivityAddSubjectBinding;

public class AddSubjectActivity extends BaseActivity implements AddSubjectView {
    private static final String TAG = AddSubjectActivity.class.getSimpleName();

    private static final int PICK_IMAGE = 101;

    private ActivityAddSubjectBinding dataBinding;
    private AddSubjectPresenter addSubjectPresenter;

    private String  imageFilePath = null;

    public static void start(Context context) {
        Log.d(TAG, "start() called with: context = [" + context + "]");
        Intent starter = new Intent(context, AddSubjectActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_subject);

        addSubjectPresenter = new AddSubjectPresenter(this, MockSubjectRepository.getInstance());

        dataBinding.btnSubjectAdd.setOnClickListener(addSubjectClickListener);

        dataBinding.imgSubjectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                        PICK_IMAGE);
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
                imageFilePath = FileHelper.savefile(getApplicationContext(), imageFileUri);
                dataBinding.imgSubjectImage.setImageBitmap(
                        BitmapFactory.decodeFile(imageFilePath));
            }catch (Exception ex){
                ex.printStackTrace();
                imageFilePath = null;
            }
        }
    }

    View.OnClickListener addSubjectClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addSubjectPresenter.addSubject(dataBinding.edtxtSubjectTitle.getText().toString(),
                    dataBinding.edtxtSubjectDesc.getText().toString(),
                    "");
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
