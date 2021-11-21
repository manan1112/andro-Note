package com.example.andronote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
//libraries for animation
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
//
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private static int splash_screen=7000;    //7sec

    //variables animation
    Animation topAnim,bottomAnim;
    ImageView image,design;
    TextView  title,tagline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //animation loading and storing it to variable
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        image=findViewById(R.id.imageView);
        title=findViewById(R.id.textView);
        design=findViewById(R.id.imageView2);
        tagline=findViewById(R.id.textView3);

        image.setAnimation(topAnim);
        design.setAnimation(topAnim);
        title.setAnimation(bottomAnim);
        tagline.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(getApplicationContext(),home.class);
                startActivity(i);
                finish();                   //remove splash screen, so that it wont appear even on back press
            }
        },splash_screen);

    }
}