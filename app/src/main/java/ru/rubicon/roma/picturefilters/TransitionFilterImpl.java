package ru.rubicon.roma.picturefilters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by roma on 26.02.2017.
 */

public class TransitionFilterImpl extends TransitionFilter {

    private final Paint fillPaint;

    public TransitionFilterImpl(int framesCount, ActionFilter showFilter, ActionFilter hideFilter) {
        super(framesCount, showFilter, hideFilter);
        fillPaint = new Paint();
        fillPaint.setColor(Color.BLACK);
//        fillPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
    }

    @Override
    public void paintFrame(Canvas canvas, int currentFrame) {
        long time = System.currentTimeMillis();
        if(getHideFilter() != null){
            ActionFilter actionFilter = getHideFilter();
            while (actionFilter != null){
                actionFilter.paintFrame(canvas, currentFrame);
                actionFilter = actionFilter.getNextFilter();
            }
        }else {
            canvas.drawBitmap(getCurrentBitmap(), 0, 0, null);
        }
        canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), fillPaint);
        ActionFilter actionFilter = getShowFilter();
        if(actionFilter.getNextFilter() == null){
            canvas.drawBitmap(getNextBitmap(), 0, 0, null);
        }
        while (actionFilter != null){
            actionFilter.paintFrame(canvas, currentFrame);
            actionFilter = actionFilter.getNextFilter();
        }
        canvas.restore();
//        Log.d("time", "paint frame time: " + (System.currentTimeMillis() - time));
    }
}
