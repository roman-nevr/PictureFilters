package ru.rubicon.roma.picturefilters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private AnimatedImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_layout);
        imageView = (AnimatedImageView) findViewById(R.id.image);
        imageView.nextImage();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                imageView.nextImage();
            }
        });
    }
}
