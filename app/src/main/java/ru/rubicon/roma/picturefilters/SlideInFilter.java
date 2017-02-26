package ru.rubicon.roma.picturefilters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by roma on 26.02.2017.
 */

public class SlideInFilter extends TransitionFilter {


    public SlideInFilter(int framesCount, ActionFilter showFilter, ActionFilter hideFilter) {
        super(framesCount, hideFilter, showFilter);

    }

    @Override public void paintNext(Canvas canvas) {
        if(getHideFilter() != null){
            setBitmapToFilter(getHideFilter(), getCurrentBitmap());
            getHideFilter().paintFrame(canvas, getCurFrame());
        }else {
            canvas.drawBitmap(getCurrentBitmap(), 0, 0, null);
        }
        setBitmapToFilter(getShowFilter(), getNextBitmap());
        getShowFilter().paintFrame(canvas, getCurFrame());
        incCurFrame();
    }

    private void setBitmapToFilter(ActionFilter actionFilter, Bitmap bitmapToFilter){
        actionFilter.setBitmap(bitmapToFilter);
        while (actionFilter.getNextFilter()!=null){
            actionFilter = actionFilter.getNextFilter();
            actionFilter.setBitmap(bitmapToFilter);
        }
    }
}
