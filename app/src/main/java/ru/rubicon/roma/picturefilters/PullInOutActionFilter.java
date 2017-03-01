package ru.rubicon.roma.picturefilters;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 * Created by roma on 26.02.2017.
 */

public class PullInOutActionFilter extends ActionFilter {

    private Paint maskPaint;
    private Path pathFirst, pathSecond;

    private float step;

    public PullInOutActionFilter(int framesCount) {
        super(framesCount);
        maskPaint = new Paint();
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        pathFirst = new Path();
        pathSecond = new Path();
    }

    @Override public void paintFrame(Canvas canvas, int curFrame) {
        drawMask(canvas, curFrame);
    }

    private void drawMask(Canvas canvas, int currentTime) {
        float left, top, right, bottom, center, timeCoef;
        timeCoef = (getFramesCount() - currentTime) / (float) getFramesCount();
        System.out.println("variant " + getVariant());
        switch (getVariant() % 8){
            case 0:{
                drawVerticalFromCenter(canvas, timeCoef);
                break;
            }
            case 1:{
                canvas.save();
                drawVerticalFromCenter(canvas, timeCoef);
                canvas.restore();
                break;
            }
            case 2: {
                center = canvas.getHeight() / 2;
                top = - center;
                bottom = canvas.getHeight() * 1.5f;
                left = center - canvas.getHeight() / 2 * timeCoef;
                right = center + canvas.getHeight() / 2 * timeCoef;
                canvas.drawRect(left, top, right, bottom, maskPaint);
                break;
            }
            case 3: {
                canvas.save();
                canvas.rotate(90, canvas.getWidth()/2, canvas.getHeight()/2);
                center = canvas.getHeight() / 2;
                top = - center;
                bottom = canvas.getHeight() * 1.5f;
                left = center - canvas.getHeight() / 2 * timeCoef;
                right = center + canvas.getHeight() / 2 * timeCoef;
                canvas.drawRect(left, top, right, bottom, maskPaint);
                canvas.restore();
                break;
            }
            case 4:{
                drawDiagonalToCenter(canvas, timeCoef);//!
                break;
            }
            case 5:{
                canvas.save();
                canvas.rotate(90, canvas.getWidth()/2, canvas.getHeight()/2);
                drawDiagonalToCenter(canvas, timeCoef);
                canvas.restore();
                break;
            }
            case 6:{
                drawDiagonalFromCenter(canvas, timeCoef);
                break;
            }
            case 7:{
                canvas.save();
                canvas.rotate(90, canvas.getWidth()/2, canvas.getHeight()/2);
                drawDiagonalFromCenter(canvas, timeCoef);
                canvas.restore();
                break;
            }
        }
    }

    private void drawVerticalFromCenter(Canvas canvas, float timeCoef){
        float left, top, right, bottom;
        top = 0f;
        bottom = canvas.getWidth();
        left = 0f;
        right = canvas.getWidth() / 2 * timeCoef;
        canvas.drawRect(left, top, right, bottom, maskPaint);

        right = canvas.getWidth();
        left = right - canvas.getHeight() / 2 * timeCoef;
        canvas.drawRect(left, top, right, bottom, maskPaint);
    }

    private void drawDiagonalFromCenter(Canvas canvas, float timeCoef){
        pathFirst.reset();
        pathFirst.setLastPoint(0, 0);
        pathFirst.lineTo(canvas.getWidth() * timeCoef, 0);
        pathFirst.lineTo(0, canvas.getHeight() * timeCoef);
        pathFirst.close();

        pathSecond.reset();
        pathSecond.setLastPoint(canvas.getWidth(), canvas.getHeight());
        pathSecond.lineTo(canvas.getWidth(), canvas.getHeight() - canvas.getHeight() * timeCoef);
        pathSecond.lineTo(canvas.getWidth() - canvas.getWidth() * timeCoef, canvas.getHeight());
        pathSecond.close();
        canvas.drawPath(pathFirst, maskPaint);
        canvas.drawPath(pathSecond, maskPaint);
    }

    private void drawDiagonalToCenter(Canvas canvas, float timeCoef){
        pathFirst.reset();
        pathFirst.setLastPoint(0, 0);
        pathFirst.setLastPoint(canvas.getWidth() * (1 - timeCoef), 0);
        pathFirst.lineTo(canvas.getWidth(), 0);
        pathFirst.lineTo(canvas.getWidth(), canvas.getHeight() * timeCoef);
        pathFirst.lineTo(canvas.getWidth() * timeCoef, canvas.getHeight());
        pathFirst.lineTo(0, canvas.getHeight());
        pathFirst.lineTo(0, canvas.getHeight() * (1 -timeCoef));
        pathFirst.close();
        canvas.drawPath(pathFirst, maskPaint);
    }
}
