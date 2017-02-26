package ru.rubicon.roma.picturefilters;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by roma on 25.02.2017.
 */

// базовый класс для Transition Filters.
public abstract class TransitionFilter {

    public TransitionFilter(int framesCount, ActionFilter hideFilter, ActionFilter showFilter) {
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
    private Canvas canvas;
    // количество кадров, которое создает данный фильтр, от
    // начала до конца.
    protected int framesCount;
    // текущий номер кадра.
    protected int curFrame;
    // фильтр скрытия
    private ActionFilter hideFilter;
    // фильтр показа
    private ActionFilter showFilter;
    protected Bitmap currentBitmap, nextBitmap;

    // отрисовывает следующий кадр.
    public abstract void paintNext(Canvas canvas);

    public ActionFilter getHideFilter() {
        return hideFilter;
    }

    public ActionFilter getShowFilter() {
        return showFilter;
    }

    public void setFramesCount(int framesCount) {
        this.framesCount = framesCount;
    }

    public int getFramesCount() {
        return framesCount;
    }

    public int getCurFrame() {
        return curFrame;
    }

    //если отстаем
    public void setCurFrame(int curFrame) {
        this.curFrame = curFrame;
    }


    public void incCurFrame() {
        curFrame++;
    }

    public Bitmap getNextBitmap() {
        return nextBitmap;
    }

    public void setNextBitmap(Bitmap nextBitmap) {
        this.nextBitmap = nextBitmap;
    }

    public Bitmap getCurrentBitmap() {
        return currentBitmap;
    }

    public void setCurrentBitmap(Bitmap currentBitmap) {
        this.currentBitmap = currentBitmap;
    }
}