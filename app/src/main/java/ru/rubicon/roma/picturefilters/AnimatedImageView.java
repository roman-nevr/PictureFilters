package ru.rubicon.roma.picturefilters;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by roma on 25.02.2017.
 */

public class AnimatedImageView extends ImageView {

    private int currentFrame;
    private boolean animated;
    private Bitmap currentBitmap, showBitmap, hideBitmap;
    private TransitionFilter transitionFilter;

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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(animated){
            if(!isBitmapFitView(showBitmap)){
                showBitmap = fitToSize(showBitmap, getWidth(), getHeight());
                transitionFilter.setNextBitmap(showBitmap);
            }
            if(!isBitmapFitView(hideBitmap)){
                hideBitmap = fitToSize(hideBitmap, getWidth(), getHeight());
                transitionFilter.setCurrentBitmap(hideBitmap);
            }
            transitionFilter.paintFrame(canvas, currentFrame);
            //System.out.println(currentFrame);
        }else {
            showStaticBitmap(canvas);
        }
    }

    @SuppressLint({"DrawAllocation", "WrongCall"})
    private void showStaticBitmap(Canvas canvas) {
        if(currentBitmap ==null){
            super.onDraw(canvas);
            return;
        }
        if(!isBitmapFitView(currentBitmap)){
            currentBitmap = fitToSize(currentBitmap, getWidth(), getHeight());
        }
        canvas.drawBitmap(currentBitmap, 0, 0, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Расчитано на портретный режим
        int measuredWidth = measureWidth(widthMeasureSpec);
        int measuredHeight = measureWidth(widthMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    private int measureWidth(int widthMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        return specSize;
    }

    public void nextImage(Bitmap bitmap, final TransitionFilter transitionFilter) {
        this.showBitmap = bitmap;
        this.hideBitmap = currentBitmap;
        this.transitionFilter = transitionFilter;

        this.transitionFilter.setNextBitmap(showBitmap);
        this.transitionFilter.setCurrentBitmap(hideBitmap);
        currentBitmap = showBitmap;

        animated = true;
        ValueAnimator animator = ValueAnimator.ofInt(0, transitionFilter.getFramesCount());
        animator.setDuration((int) (transitionFilter.getFramesCount() * 16.6667));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentFrame = (int)animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) {

            }

            @Override public void onAnimationEnd(Animator animation) {
                animated = false;
                invalidate();
            }

            @Override public void onAnimationCancel(Animator animation) {
                animated = false;
                invalidate();
            }

            @Override public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    public void setCurrentBitmap(Bitmap currentBitmap) {
        this.currentBitmap = currentBitmap;
        invalidate();
    }

    private boolean isBitmapFitView(Bitmap bitmap){
        if(bitmap == null){
            return true;
        }
        if(bitmap.getWidth() != getWidth() || bitmap.getHeight() != getHeight()){
            return false;
        }else {
            return true;
        }
    }

    private Bitmap fitToSize(Bitmap bitmap, int width, int height) {
        Bitmap croppedBitmap;
        if (bitmap.getWidth() >= bitmap.getHeight()) {
            croppedBitmap = Bitmap.createBitmap(bitmap, (bitmap.getWidth() - bitmap.getHeight()) / 2, 0,
                    bitmap.getHeight(), bitmap.getHeight());

        } else {
            croppedBitmap = Bitmap.createBitmap(bitmap, 0, (bitmap.getHeight() - bitmap.getWidth()) / 2,
                    bitmap.getWidth(), bitmap.getWidth());
        }
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(croppedBitmap, width, height, false);
        croppedBitmap.recycle();
        return scaledBitmap;
    }
}