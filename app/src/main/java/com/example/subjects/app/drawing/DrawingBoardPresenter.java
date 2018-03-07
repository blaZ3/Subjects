package com.example.subjects.app.drawing;

import android.graphics.Bitmap;

import com.example.subjects.app.utils.FileHelper;

import java.io.FileOutputStream;

/**
 * Created by vivek on 07/03/18.
 */

public class DrawingBoardPresenter {

    DrawingBoardView boardView;

    public DrawingBoardPresenter(DrawingBoardView boardView) {
        this.boardView = boardView;
    }

    public void saveDrawing(Bitmap bitmap){

        try{
            long currTime = System.currentTimeMillis();
            String filePath = FileHelper.getFilePathForName(currTime+"");
            bitmap.compress(Bitmap.CompressFormat.PNG, 80,
                    new FileOutputStream(filePath));

            boardView.drawingSaved(filePath);
        }catch (Exception ex){
            ex.printStackTrace();
            boardView.drawingSaveError();
        }

    }

}
