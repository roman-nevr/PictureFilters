package ru.rubicon.roma.picturefilters;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 * Created by roma on 26.02.2017.
 */

public class CurtainActionFilter extends ActionFilter {

    private static final int CURTAIN_NUMBER = 6;
    private Paint maskPaint;

    private float step;

    public CurtainActionFilter(int framesCount) {
        super(framesCount);
        maskPaint = new Paint();
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        step = 1f / (CURTAIN_NUMBER * framesCount);
    }

    @Override public void paintFrame(Canvas canvas, int curFrame) {
        drawCurtain(canvas, curFrame);
    }

    private void drawCurtain(Canvas canvas, int currentFrame){
        float left, top, right, bottom ;
        switch (getVariant() % 4){
            case 0:{
                for (int i = 0; i < CURTAIN_NUMBER; i++){
                    left = i * canvas.getWidth() / CURTAIN_NUMBER;
                    right = left + (getFramesCount() - currentFrame) * canvas.getWidth() * step;
                    top = 0;
                    bottom = canvas.getHeight();
                    canvas.drawRect(left, top, right, bottom, maskPaint);
                }
                break;
            }
            case 1:{
                for (int i = 0; i < CURTAIN_NUMBER; i++){
                    right = (i + 1) * canvas.getWidth() / CURTAIN_NUMBER;
                    left = right - (getFramesCount() - currentFrame) * canvas.getWidth() * step;
                    top = 0;
                    bottom = canvas.getHeight();
                    canvas.drawRect(left, top, right, bottom, maskPaint);
                }
                break;
            }
            case 2:{
                for (int i = 0; i < CURTAIN_NUMBER; i++){
                    right = canvas.getWidth();
                    left = 0;
                    top = i * canvas.getHeight() / CURTAIN_NUMBER;
                    bottom = top + (getFramesCount() - currentFrame) * canvas.getHeight() * step;
                    canvas.drawRect(left, top, right, bottom, maskPaint);
                }
                break;
            }
            case 3:{
                for (int i = 0; i < CURTAIN_NUMBER; i++){
                    right = canvas.getWidth();
                    left = 0;
                    bottom = (i + 1) * canvas.getHeight() / CURTAIN_NUMBER;
                    top = bottom - (getFramesCount() - currentFrame) * canvas.getHeight() * step;
                    canvas.drawRect(left, top, right, bottom, maskPaint);
                }
                break;
            }
            default:throw new IllegalArgumentException();

        }
    }
}
