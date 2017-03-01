package ru.rubicon.roma.picturefilters;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by roma on 25.02.2017.
 */

// базовый класс для Transition Filters.
public abstract class TransitionFilter {

    public TransitionFilter(int framesCount, ActionFilter showFilter, ActionFilter hideFilter) {
        this.framesCount = framesCount;
        this.hideFilter = hideFilter;
        this.showFilter = showFilter;
        if (hideFilter != null) {
            setFramesCountToFilter(hideFilter, framesCount);
        }
        setFramesCountToFilter(showFilter, framesCount);
    }

    private void setFramesCountToFilter(ActionFilter actionFilter, int framesCount) {
        actionFilter.setFramesCount(framesCount);
        while (actionFilter.getNextFilter() != null) {
            actionFilter = actionFilter.getNextFilter();
            actionFilter.setFramesCount(framesCount);
        }
    }

    // canvas, на котором производится всё рисование
    //private Canvas canvas; каждый такт меняется
    // количество кадров, которое создает данный фильтр, от
    // начала до конца.
    protected int framesCount;
    // текущий номер кадра.
    //protected int curFrame;
    // фильтр скрытия
    private ActionFilter hideFilter;
    // фильтр показа
    private ActionFilter showFilter;
    protected Bitmap currentBitmap, nextBitmap;

    // отрисовывает нужный кадр.
    public abstract void paintFrame(Canvas canvas, int currentFrame);

    public ActionFilter getHideFilter() {
        return hideFilter;
    }

    public ActionFilter getShowFilter() {
        return showFilter;
    }

    public void setFramesCount(int framesCount) {
        this.framesCount = framesCount;
    }

    public final int getFramesCount() {
        return framesCount;
    }

    public final Bitmap getNextBitmap() {
        return nextBitmap;
    }

    public final void setNextBitmap(Bitmap nextBitmap) {
        this.nextBitmap = nextBitmap;
        ActionFilter actionFilter = showFilter;
        actionFilter.setBitmap(nextBitmap);
        if(actionFilter.getNextFilter() != null){
            actionFilter = actionFilter.getNextFilter();
            actionFilter.setBitmap(nextBitmap);
        }
    }

    public final Bitmap getCurrentBitmap() {
        return currentBitmap;
    }

    public final void setCurrentBitmap(Bitmap currentBitmap) {
        this.currentBitmap = currentBitmap;
        if (hideFilter == null){
            return;
        }
        ActionFilter actionFilter = hideFilter;
        actionFilter.setBitmap(currentBitmap);
        if(actionFilter.getNextFilter() != null){
            actionFilter = actionFilter.getNextFilter();
            actionFilter.setBitmap(nextBitmap);
        }
    }
}