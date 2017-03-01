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
        switch (getVariant() % 4){
            case 0:{
                canvas.drawBitmap(getBitmap(), 0, canvas.getHeight() * (imageOffset * (float) (getFramesCount() - curFrame) / getFramesCount()), fillPaint);
                break;
            }
            case 1:{
                canvas.drawBitmap(getBitmap(), 0, - imageOffset * canvas.getHeight() * ((getFramesCount() - curFrame) / (float) getFramesCount()), fillPaint);
                break;
            }
            case 2:{
                canvas.drawBitmap(getBitmap(), canvas.getWidth() * (imageOffset * (float) (getFramesCount() - curFrame) / (float) getFramesCount()), 0, fillPaint);
                break;
            }
            case 3:{
                canvas.drawBitmap(getBitmap(), imageOffset * canvas.getWidth() * ((- (getFramesCount() - curFrame) / (float)getFramesCount())), 0, fillPaint);
                break;
            }
        }

    }

}
