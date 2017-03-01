package ru.rubicon.roma.picturefilters;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by roma on 25.02.2017.
 */

// базовый класс для фильтров показа и скрытия.
public abstract class ActionFilter {

    private int framesCount;

    private Bitmap bitmap;

    public ActionFilter(int framesCount) {
        this.framesCount = framesCount;
    }

    private ActionFilter nextFilter;

    public abstract void paintFrame(Canvas canvas, int currentFrame);

    public final void setNextFilter(ActionFilter filter){
        this.nextFilter = filter;
    }

    public final ActionFilter getNextFilter(){
        return nextFilter;
    }

    protected final Bitmap getBitmap(){
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    protected final int getFramesCount(){
        return framesCount;
    }

    public final void setFramesCount(int framesCount) {
        this.framesCount = framesCount;
    }
}