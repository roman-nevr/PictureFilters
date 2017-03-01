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

    private static final String TAG = "tag";
    private static final int CURTAIN_NUMBER = 6;
    private Paint srcInPaint;
    private Paint maskPaint;

    private float step;

    public CurtainActionFilter(int framesCount) {
        super(framesCount);
        maskPaint = new Paint();
        maskPaint.setColor(Color.TRANSPARENT);
        srcInPaint = new Paint();
        srcInPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        step = 1f / (CURTAIN_NUMBER * framesCount);
    }

    @Override public void paintFrame(Canvas canvas, int curFrame) {
//        canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        drawCurtain(canvas, curFrame);
//        canvas.restore();
    }

    private void drawCurtain(Canvas canvas, int currentFrame){
        float left, top, right, bottom ;
        for (int i = 0; i < 6; i++){
            left = i * canvas.getWidth() / CURTAIN_NUMBER;
            right = left + (getFramesCount() - currentFrame) * canvas.getWidth() * step;
            top = 0;
            bottom = canvas.getHeight();
            canvas.drawRect(left, top, right, bottom, srcInPaint);
        }
    }
}
