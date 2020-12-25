package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIMER = 5000;

    ImageView titleImage;
    ImageView logo;
//    ViewSwitcher logo;
    TextView poweredBy;

    Animation zoomIn;
    Animation zoomOut;
    Animation fade_in;
    Animation fade_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // Hook
        this.titleImage = (ImageView) findViewById(R.id.title);
        this.logo = (ImageView) findViewById(R.id.logo);
//        this.logo = (ViewSwitcher) findViewById(R.id.logo);
        this.poweredBy = (TextView) findViewById(R.id.powered_by);

        // Animations
        this.fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        this.fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);




        this.titleImage.setAnimation(fade_in);
        this.logo.setAnimation(fade_in);
        this.poweredBy.setAnimation(fade_in);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(SPLASH_TIMER);
                } catch (Exception e) {

                } finally {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            }
        };
        thread.start();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                finish();
//            }
//        }, SPLASH_TIMER);
    }
}