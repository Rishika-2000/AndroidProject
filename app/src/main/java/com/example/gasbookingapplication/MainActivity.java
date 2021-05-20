package com.example.gasbookingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
private static int SPLASH_SCREEN=5000;
    Animation top,bot;
    ImageView imageView;
    TextView t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        top= AnimationUtils.loadAnimation(this,R.anim.topanimation);
        bot= AnimationUtils.loadAnimation(this,R.anim.bottomanimation);
        imageView=(ImageView)findViewById(R.id.imageView);
        t1=(TextView)findViewById(R.id.textView);
        t2=(TextView)findViewById(R.id.textView2);
        imageView.setAnimation(top);
        t1.setAnimation(bot);
        t2.setAnimation(bot);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,login.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);


    }
}