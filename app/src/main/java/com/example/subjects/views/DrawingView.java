package com.example.subjects.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.ThumbnailUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Stack;

public class DrawingView extends View {
    private static final String TAG = DrawingView.class.getSimpleName();

    public int width;
    public  int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mBitmapPaint;
    Context context;

    private Paint mPaint;
    private Paint mPaintClear;

    private Stack<Path> undoStack = new Stack<>();
    private Stack<Path> redoStack = new Stack<>();

    public DrawingView(Context c) {
        super(c);
        context=c;
        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setStrokeWidth(3);

        mPaintClear = new Paint();
        mPaintClear.setAntiAlias(true);
        mPaintClear.setDither(true);
        mPaintClear.setColor(Color.WHITE);
        mPaintClear.setStyle(Paint.Style.STROKE);
        mPaintClear.setStrokeJoin(Paint.Join.ROUND);
        mPaintClear.setStrokeCap(Paint.Cap.SQUARE);
        mPaintClear.setStrokeWidth(4);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap( mBitmap, 0, 0, mBitmapPaint);
        canvas.drawPath( mPath,  mPaint);
//        canvas.drawPath( circlePath,  circlePaint);
    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private void touch_start(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        Log.d(TAG, "touch_move() called with: x = [" + x + "], y = [" + y + "]");
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
        }
    }

    private void touch_up() {
        Log.d(TAG, "touch_up() called :"+mPath);
        mPath.lineTo(mX, mY);

        // commit the path to our offscreen
        mCanvas.drawPath(mPath,  mPaint);

        Path didPath = new Path(mPath);
        undoStack.push(didPath);
        redoStack.clear();


        // kill this so we don't double draw
        mPath.reset();
    }

    public int undoDrawing(){
        if (!undoStack.isEmpty()){
            Path undoPath = undoStack.pop();
            mCanvas.drawPath(undoPath,  mPaintClear);
            redoStack.add(new Path(undoPath));
            undoPath.reset();
            return 0;
        }else {
            return -1;
        }
    }

    public int redoDrawing(){
        if (!redoStack.isEmpty()){
            Path redoPath = redoStack.pop();
            mCanvas.drawPath(redoPath,  mPaint);
            undoStack.add(new Path(redoPath));
            redoPath.reset();
            return 0;
        }else {
            return -1;
        }
    }


    public void clearDrawing(){
        setDrawingCacheEnabled(false);
        // don't forget that one and the match below,
        // or you just keep getting a duplicate when you save.

        onSizeChanged(width, height, width, height);
        invalidate();

        setDrawingCacheEnabled(true);
    }

    public Bitmap saveDrawing(){
        Bitmap whatTheUserDrewBitmap = getDrawingCache();

        whatTheUserDrewBitmap = ThumbnailUtils.extractThumbnail(whatTheUserDrewBitmap, width, height);

        return whatTheUserDrewBitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent() called with: event = [" + event + "]");
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }
}