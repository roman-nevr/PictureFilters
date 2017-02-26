package ru.rubicon.roma.picturefilters;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import java.util.Date;

/**
 * Created by roma on 25.02.2017.
 */

public class AnimatedImageView extends ImageView {

    public static final String TAG = "tag";
    Bitmap day, night, sky;
    private Matrix matrix;
    private Paint maskPaint;
    private int curFrame;
    private boolean drawn;
    private Bitmap bufferBitmap;
    private Canvas bufferCanvas;
    private Paint srcInPaint;
    private TransitionFilter transitionFilter;
    private boolean scaled;

    public AnimatedImageView(Context context) {
        super(context);
        init();
    }

    public AnimatedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimatedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        sky = decodeBitmap(R.drawable.sky_bgr);
        night = decodeBitmap(R.drawable.night);
        day = decodeBitmap(R.drawable.material);
        matrix = new Matrix();
        maskPaint = new Paint();
        srcInPaint = new Paint();
        srcInPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        ActionFilter slideIn = new SlideInActionFilter(0.2f);
        //transitionFilter = new SlideInFilter(120, slideIn, null);
        ActionFilter curtain = new CurtainActionFilter();
        //curtain.setNextFilter(new SlideInActionFilter(0.5f));
        transitionFilter = new SlideInFilter(120, curtain, slideIn);
        scaled = false;
    }

    private Bitmap decodeBitmap(@DrawableRes int resId) {
        return BitmapFactory.decodeResource(getResources(), resId);
    }

    @SuppressLint({"DrawAllocation"})
    @Override
    protected void onDraw(Canvas canvas) {
        if (!drawn) {
            //canvas.drawBitmap(sky, 0, 0, null);
            transitionFilter.paintNext(canvas);
            drawn = true;
            System.out.println("onDraw, frame: " + transitionFilter.getCurFrame());
        }
//        mask(canvas);
    }

    private void mask(Canvas canvas) {
        canvas.drawBitmap(day, 0, 0, null);
        if (!drawn) {
            Bitmap mask = createCurtainMask(transitionFilter.getCurFrame(), canvas, maskPaint);
            bufferBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
            bufferCanvas = new Canvas(bufferBitmap);
            bufferCanvas.drawBitmap(mask, 0, 0, null);
            bufferCanvas.drawBitmap(night, 0, 0, srcInPaint);

            canvas.drawBitmap(bufferBitmap, 0, 0, null);
            drawn = true;
        }
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
            right = left + (1f * curFrame / transitionFilter.getFramesCount()) * canvas.getWidth() / 6f + 1.0f;
            top = 0;
            bottom = canvas.getHeight();
            maskCanvas.drawRect(left, top, right, bottom, paint);
        }
        Log.d(TAG, "curFrame " + curFrame);
        Log.d(TAG, "" + left + ", " + top + ", " + right);
        return mask;
    }

    private Bitmap createRoundMask(int curFrame, Canvas canvas, Paint paint) {
        Bitmap mask = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ALPHA_8);
        Canvas maskCanvas = new Canvas(mask);
        maskCanvas.drawCircle(380, 380, 10 * curFrame, paint);
        return mask;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Расчитано на портретный режим
        int measuredWidth = measureWidth(widthMeasureSpec);
        int measuredHeight = measureWidth(widthMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);

        if (!scaled) {
            night = scale(night, measuredWidth, measuredHeight);
            day = scale(day, measuredWidth, measuredHeight);
            sky = scale(sky, measuredWidth, measuredHeight);
            transitionFilter.setCurrentBitmap(night);
            transitionFilter.setNextBitmap(day);
            scaled = true;
        }
    }

    private int measureWidth(int widthMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        return specSize;
    }


    private Bitmap scale(Bitmap bitmap, int width, int height) {

        Bitmap newBitmap;
        if (bitmap.getWidth() >= bitmap.getHeight()) {
            newBitmap = Bitmap.createBitmap(bitmap, (bitmap.getWidth() - bitmap.getHeight()) / 2, 0,
                    bitmap.getHeight(), bitmap.getHeight());
            bitmap.recycle();
            bitmap = newBitmap;
            newBitmap = Bitmap.createScaledBitmap(bitmap, width, width, false);
        } else {
            newBitmap = Bitmap.createBitmap(bitmap, 0, (bitmap.getHeight() - bitmap.getWidth()) / 2,
                    bitmap.getWidth(), bitmap.getWidth());
            bitmap.recycle();
            bitmap = newBitmap;
            newBitmap = Bitmap.createScaledBitmap(bitmap, width, width, false);
        }
        bitmap.recycle();
        return newBitmap;
    }

    public void nextImage() {
        ValueAnimator animator = ValueAnimator.ofInt(1, 120);
        animator.setDuration(2000);
        final long startTime = getTime();
        curFrame = 0;
        transitionFilter.setCurFrame(0);
        drawn = true;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                int frame = (int) animation.getAnimatedValue();
                if (frame > transitionFilter.getCurFrame()) {
                    transitionFilter.setCurFrame(frame);
                    drawn = false;
                    invalidate();
                }
                System.out.println("frame: " + frame + ", time " + (getTime() - startTime));
            }
        });
        animator.start();
    }

    public long getTime() {
        return new Date().getTime();
    }
}