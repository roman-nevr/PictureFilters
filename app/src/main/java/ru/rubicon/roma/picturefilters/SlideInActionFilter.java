package ru.rubicon.roma.picturefilters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 * Created by roma on 25.02.2017.
 */

public class SlideInActionFilter extends ActionFilter {

    private float imageOffset;
    private Paint fillPaint;

    public SlideInActionFilter(int framesCount, float imageOffset) {
        super(framesCount);
        this.imageOffset = imageOffset;
        fillPaint = new Paint();
        fillPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    @Override public void paintFrame(Canvas canvas, int curFrame) {
//        canvas.translate(0, canvas.getHeight() * (imageOffset * (getFramesCount() - curFrame) / getFramesCount()));
        //canvas.drawRect(0,0,canvas.getHeight(),canvas.getWidth(),fillPaint);
        canvas.drawBitmap(getBitmap(), 0, canvas.getHeight() * (imageOffset * (getFramesCount() - curFrame) / getFramesCount()), fillPaint);
    }

    @Override public void setBitmap(Bitmap bitmap) {
        super.setBitmap(bitmap);
    }
}
