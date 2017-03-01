package ru.rubicon.roma.picturefilters;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
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
    private int currentFrame;
    private boolean animated;
    private Bitmap currentBitmap, nextBitmap, tmp;
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

        matrix = new Matrix();
        maskPaint = new Paint();
        srcInPaint = new Paint();
        srcInPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    @SuppressLint({"DrawAllocation"})
    @Override
    protected void onDraw(Canvas canvas) {
        if (animated){
            transitionFilter.paintFrame(canvas, currentFrame);
        }else {
            if (currentBitmap != null){
                canvas.drawBitmap(currentBitmap, 0, 0, null);
            }else {
                super.onDraw(canvas);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Расчитано на портретный режим
        int measuredWidth = measureWidth(widthMeasureSpec);
        int measuredHeight = measureWidth(widthMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);

        currentBitmap = checkSize(currentBitmap, measuredWidth, measuredHeight);
        nextBitmap = checkSize(nextBitmap, measuredWidth, measuredHeight);
    }

    private Bitmap checkSize(Bitmap bitmap, int width, int height){
        if(bitmap != null && (bitmap.getWidth() > width || bitmap.getHeight() > height)){
            return scale(bitmap, width, height);
        }
        return bitmap;
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
            bitmap = newBitmap;
            newBitmap = Bitmap.createScaledBitmap(bitmap, width, width, false);
        } else {
            newBitmap = Bitmap.createBitmap(bitmap, 0, (bitmap.getHeight() - bitmap.getWidth()) / 2,
                    bitmap.getWidth(), bitmap.getWidth());
            bitmap = newBitmap;
            newBitmap = Bitmap.createScaledBitmap(bitmap, width, width, false);
        }
        return newBitmap;
    }

    public void nextImage(Bitmap bitmap, final TransitionFilter transitionFilter) {
        if(getWidth() > 0){
            nextBitmap = checkSize(bitmap, getWidth(), getHeight());
        }else {
            nextBitmap = bitmap;
        }
        this.transitionFilter = transitionFilter;
        transitionFilter.setCurrentBitmap(currentBitmap);
        transitionFilter.setNextBitmap(nextBitmap);
        currentBitmap = nextBitmap;
        ValueAnimator animator = ValueAnimator.ofInt(0, transitionFilter.getFramesCount());
        animator.setDuration((int)(transitionFilter.getFramesCount() * 16.6667));
        animated = true;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                currentFrame = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    public long getTime() {
        return new Date().getTime();
    }

    public void setCurrentBitmap(Bitmap currentBitmap) {
        if(getWidth() > 0){
            currentBitmap = scale(currentBitmap, getWidth(), getHeight());
        }
        this.currentBitmap = currentBitmap;
        invalidate();
    }
}