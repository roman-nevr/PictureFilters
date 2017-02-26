package ru.rubicon.roma.picturefilters.ball_bounces;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.rubicon.roma.picturefilters.R;

public class SurfaceViewActivity extends AppCompatActivity {
    BallBounces ball;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ball = new BallBounces(this);
        setContentView(R.layout.activity_main);
    }
}