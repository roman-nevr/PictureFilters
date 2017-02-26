package ru.rubicon.roma.picturefilters;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by roma on 25.02.2017.
 */

// базовый класс для фильтров показа и скрытия.
public abstract class ActionFilter {
    // битмап который отрисовается фильтром.
    protected Bitmap bitmap;
    // количество кадров, которое создает данный фильтр, от
    // начала до конца.
    private int framesCount;
    // отрисовывает следующий кадр.
    public abstract void paintFrame(Canvas canvas, int curFrame);
    // устанавливает следующий фильтр для
    //  составного фильтра.
    public abstract void setNextFilter(ActionFilter filter);
    // получает следующий фильтр для составного
    // фильтра.
    public abstract ActionFilter getNextFilter();

    protected final Bitmap getBitmap(){
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    protected final int getFramesCount(){
        return framesCount;
    }

    public void setFramesCount(int framesCount) {
        this.framesCount = framesCount;
    }
}