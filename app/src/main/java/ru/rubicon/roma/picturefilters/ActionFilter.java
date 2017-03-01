package ru.rubicon.roma.picturefilters;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by roma on 25.02.2017.
 */

// базовый класс для фильтров показа и скрытия.
public abstract class ActionFilter {

    private int framesCount, variant;

    private Bitmap bitmap;

    private ActionFilter nextFilter;

    public ActionFilter(int framesCount) {
        this.framesCount = framesCount;
    }

    public abstract void paintFrame(Canvas canvas, int currentFrame);

    public final ActionFilter setNextFilter(ActionFilter filter){
        this.nextFilter = filter;
        return this;
    }

    public final ActionFilter getNextFilter(){
        return nextFilter;
    }

    protected final Bitmap getBitmap(){
        return bitmap;
    }

    public final ActionFilter setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        return this;
    }

    protected final int getFramesCount(){
        return framesCount;
    }

    public final ActionFilter setFramesCount(int framesCount) {
        this.framesCount = framesCount;
        return this;
    }

    protected final int getVariant() {
        return variant;
    }

    public final ActionFilter setVariant(int variant) {
        this.variant = variant;
        return this;
    }
}