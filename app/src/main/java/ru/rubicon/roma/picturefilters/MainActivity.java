package ru.rubicon.roma.picturefilters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AnimatedImageView imageView;
    private Bitmap sky, night, day;
    List<Bitmap> bitmaps;
    int currentBitmapIndex, currentVariant;
    TransitionFilter transitionFilter, curtainFilter, pullInOutFilter;
    private Button nextFilterButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadImages();
        setFirstBitmap();
        prepareFilters();
    }

    private void setFirstBitmap() {
        imageView.setCurrentBitmap(bitmaps.get(currentBitmapIndex));
    }

    private void prepareFilters() {
        int framesCount = 120;
        curtainFilter = CurtainFilter.getInstance(framesCount);
        pullInOutFilter = PullInOutFilter.getInstance(framesCount);
        transitionFilter = pullInOutFilter;
    }


    private void loadImages() {
        sky = decodeBitmap(R.drawable.sky_bgr);
        night = decodeBitmap(R.drawable.night);
        day = decodeBitmap(R.drawable.material);
        bitmaps = Arrays.asList(sky, night, day);

    }


    private void initView() {
        setContentView(R.layout.activity_main);
        imageView = (AnimatedImageView) findViewById(R.id.image);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                currentBitmapIndex++;
                currentVariant++;
                if (currentBitmapIndex == bitmaps.size()) {
                    currentBitmapIndex = 0;
                    System.out.println("currentBitmapIndex " + currentBitmapIndex);
                }
                if (currentVariant == 8){
                    currentVariant = 0;
                    System.out.println("currentVariant " + currentVariant);
                }
                transitionFilter.setVariant(currentVariant);
                imageView.nextImage(bitmaps.get(currentBitmapIndex), transitionFilter);
            }
        });

        textView = (TextView) findViewById(R.id.textView);

        nextFilterButton = (Button) findViewById(R.id.change_button);
        nextFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(transitionFilter == curtainFilter){
                    transitionFilter = pullInOutFilter;
                    textView.setText("PullInOutFilter");
                }else {
                    transitionFilter = curtainFilter;
                    textView.setText("CurtainFilter");
                }
            }
        });
    }

    private Bitmap decodeBitmap(@DrawableRes int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        options.inMutable = true;
        return BitmapFactory.decodeResource(getResources(), resId, options);
    }

}
