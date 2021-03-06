package com.example.subjects.app.drawing;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.subjects.BaseActivity;
import com.example.subjects.R;
import com.example.subjects.app.addSubject.AddSubjectActivity;
import com.example.subjects.databinding.ActivityDrawingBoardBinding;
import com.example.subjects.views.DrawingView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;

import java.util.List;

public class DrawingBoardActivity extends BaseActivity implements DrawingBoardView {
    private static final String TAG = DrawingBoardActivity.class.getSimpleName();

    private ActivityDrawingBoardBinding dataBinding;
    private DrawingView drawingView;

    private DrawingBoardPresenter drawingBoardPresenter;

    public static void start(Context context) {
        Intent starter = new Intent(context, DrawingBoardActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_drawing_board);

        initToolbar();

        drawingBoardPresenter = new DrawingBoardPresenter(this);

        drawingView = new DrawingView(this);
        drawingView.setBackgroundColor(Color.WHITE);
        drawingView.setDrawingCacheEnabled(true);
        dataBinding.layoutDrawing.addView(drawingView);


        dataBinding.btnDrawingSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDrawing();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        doInit();
    }

    private void initToolbar() {
        setSupportActionBar(dataBinding.toolbarDrawing);
        getSupportActionBar().setTitle("Draw");
        dataBinding.toolbarDrawing.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        dataBinding.toolbarDrawing.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        dataBinding.toolbarDrawing.inflateMenu(R.menu.menu_drawing);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_draw_undo:
                if (drawingView.undoDrawing() == -1){
                    makeToast("Cant Undo");
                }
                break;
            case R.id.action_draw_redo:
                if (drawingView.redoDrawing() == -1){
                    makeToast("Cant Redo");
                }
                break;
        }
        return true;
    }

    @Override
    public void doInit() {

    }

    private void saveDrawing() {
        Dexter.withActivity(DrawingBoardActivity.this)
                .withPermissions(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new BaseMultiplePermissionsListener(){
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        super.onPermissionsChecked(report);
                        if (report.areAllPermissionsGranted()){

                            Bitmap drawing = drawingView.saveDrawing();
                            drawingBoardPresenter.saveDrawing(drawing);

                        }else if (report.isAnyPermissionPermanentlyDenied()){
                            makeToast(getString(R.string.save_drawing_rationale_denied));
                        }else {
                            makeToast(getString(R.string.save_drawing_rationale));
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        super.onPermissionRationaleShouldBeShown(permissions, token);
                    }
                })
                .check();
    }

    @Override
    public void drawingSaved(String filePath) {
        makeToast("Drawing saved to "+filePath);
        finish();
    }

    @Override
    public void drawingSaveError() {
        makeToast("Error saving drawing please try again");
    }
}
