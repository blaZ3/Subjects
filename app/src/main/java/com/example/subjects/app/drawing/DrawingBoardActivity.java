package com.example.subjects.app.drawing;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.subjects.BaseActivity;
import com.example.subjects.R;
import com.example.subjects.databinding.ActivityDrawingBoardBinding;
import com.example.subjects.views.DrawingView;

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

    private void saveDrawing() {
        Bitmap drawing = drawingView.saveDrawing();
        drawingBoardPresenter.saveDrawing(drawing);
    }


    @Override
    public void doInit() {

    }

    @Override
    public void drawingSaved(String filePath) {
        makeToast("Drawing saved to "+filePath);
    }

    @Override
    public void drawingSaveError() {
        makeToast("Error saving drawing please try again");
    }
}
