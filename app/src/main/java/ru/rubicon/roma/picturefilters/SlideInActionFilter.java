package ru.rubicon.roma.picturefilters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Created by roma on 25.02.2017.
 */

public class SlideInActionFilter extends ActionFilter {

    private ActionFilter nextFilter;
    private float imageOffset;

    public SlideInActionFilter(float imageOffset) {
        this.imageOffset = imageOffset;
    }

    @Override public void paintFrame(Canvas canvas, int curFrame) {
        Bitmap bufferBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas buffer = new Canvas(bufferBitmap);
        if (hasNextFilter()) {
            getNextFilter().paintFrame(buffer, curFrame);
        } else {
            buffer.drawBitmap(getBitmap(), 0, 0, null);
        }
        canvas.drawBitmap(bufferBitmap, 0, canvas.getHeight() * (imageOffset * (getFramesCount() - curFrame) / getFramesCount()), null);

        bufferBitmap.recycle();
    }

    @Override public void setNextFilter(ActionFilter nextFilter) {
        this.nextFilter = nextFilter;
    }

    @Override public ActionFilter getNextFilter() {
        return nextFilter;
    }

    public boolean hasNextFilter() {
        return nextFilter != null;
    }

}
