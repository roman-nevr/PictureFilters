package ru.rubicon.roma.picturefilters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AnimatedImageView imageView;
    private Bitmap sky, night, day;
    List<Bitmap> bitmaps;
    int currentBitmapIndex;
    TransitionFilter transitionFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_layout);
        imageView = (AnimatedImageView) findViewById(R.id.image);
        sky = decodeBitmap(R.drawable.sky_bgr);
        night = decodeBitmap(R.drawable.night);
        day = decodeBitmap(R.drawable.material);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                imageView.nextImage(bitmaps.get(currentBitmapIndex), transitionFilter);
                currentBitmapIndex++;
                if (currentBitmapIndex == bitmaps.size()){
                    currentBitmapIndex = 0;
                }
            }
        });
        imageView.setCurrentBitmap(night);
        bitmaps = Arrays.asList(sky, night, day);
        int framesCount = 120;
        ActionFilter showFilter = null;
        ActionFilter curtain = new CurtainActionFilter(framesCount);
        ActionFilter slide = new SlideInActionFilter(framesCount, 0.3f);
        showFilter = slide;
        //slide.setNextFilter(curtain);
        //showFilter.setNextFilter(curtain);
        showFilter = curtain;
        showFilter.setNextFilter(slide);
        transitionFilter = new SlideInFilter(framesCount, showFilter, null);
    }

    private Bitmap decodeBitmap(@DrawableRes int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        options.inMutable = true;
        return BitmapFactory.decodeResource(getResources(), resId, options);
    }

}
