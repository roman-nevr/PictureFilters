package ru.rubicon.roma.picturefilters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;

/**
 * Created by roma on 26.02.2017.
 */

public class CurtainActionFilter extends ActionFilter {

    private static final String TAG = "tag";
    private Paint srcInPaint;
    private ActionFilter nextFilter;
    private Paint paint;

    public CurtainActionFilter() {
        paint = new Paint();
        srcInPaint = new Paint();
        srcInPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
    }

    @Override public void paintFrame(Canvas canvas, int curFrame) {
        Bitmap bufferBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas buffer = new Canvas(bufferBitmap);
        if (hasNextFilter()) {
            getNextFilter().paintFrame(buffer, curFrame);
        } else {
            buffer.drawBitmap(getBitmap(), 0, 0, null);
        }
        Bitmap mask = createCurtainMask(curFrame, buffer, paint);
        Bitmap maskBufferBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas maskBufferCanvas = new Canvas(maskBufferBitmap);
        maskBufferCanvas.drawBitmap(mask, 0, 0, null);
        maskBufferCanvas.drawBitmap(bufferBitmap, 0, 0, srcInPaint);

        canvas.drawBitmap(maskBufferBitmap, 0,0, null);
        maskBufferBitmap.recycle();
        bufferBitmap.recycle();
    }

    private Bitmap createCurtainMask(int curFrame, Canvas canvas, Paint paint){
        Bitmap mask = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ALPHA_8);
        Canvas maskCanvas = new Canvas(mask);
        float left = 0f;
        float top = 0f;
        float right = 0f;
        float bottom = 0f;
        for (int i = 0; i < 6; i++){
            left = i * canvas.getWidth() / 6;
            right = left + (1f * curFrame / getFramesCount()) * canvas.getWidth() / 6f + 1f;
            top = 0;
            bottom = canvas.getHeight();
            maskCanvas.drawRect(left, top, right, bottom, paint);
        }
        Log.d(TAG, "curFrame " + curFrame);
        Log.d(TAG, "" + left + ", " + top + ", " + right);
        return mask;
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
