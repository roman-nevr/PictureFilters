package ru.rubicon.roma.picturefilters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by roma on 26.02.2017.
 */

public class SlideInFilter extends TransitionFilter {

    private final Paint fillPaint;

    public SlideInFilter(int framesCount, ActionFilter showFilter, ActionFilter hideFilter) {
        super(framesCount, showFilter, hideFilter);
        fillPaint = new Paint();
        fillPaint.setColor(Color.BLACK);
    }

    @Override
    public void paintFrame(Canvas canvas, int currentFrame) {
        if(getHideFilter() != null){
            getHideFilter().paintFrame(canvas, currentFrame);
        }else {
            canvas.drawBitmap(getCurrentBitmap(), 0, 0, null);
        }
        canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), fillPaint);
        ActionFilter actionFilter = getShowFilter();
        while (actionFilter != null){
            actionFilter.paintFrame(canvas, currentFrame);
            actionFilter = actionFilter.getNextFilter();
        }
        canvas.restore();
    }

    private void setBitmapToFilter(ActionFilter actionFilter, Bitmap bitmapToFilter){
        actionFilter.setBitmap(bitmapToFilter);
        while (actionFilter.getNextFilter()!=null){
            actionFilter = actionFilter.getNextFilter();
            actionFilter.setBitmap(bitmapToFilter);
        }
    }
}
