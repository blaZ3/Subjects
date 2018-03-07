package com.example.subjects.app.drawing;

/**
 * Created by vivek on 07/03/18.
 */

public interface DrawingBoardView {

    void doInit();

    void drawingSaved(String filePath);
    void drawingSaveError();

}
