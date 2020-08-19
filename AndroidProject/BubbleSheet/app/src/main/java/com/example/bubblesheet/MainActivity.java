package com.example.bubblesheet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout layout;
    ConstraintSet constraintSetOld=new ConstraintSet();
    ConstraintSet constraintSetNew=new ConstraintSet();
    boolean altLayout;
    double seconds;
    TextView text1,text2,text3;

    Animation topAnim,bottomAnim,rtlAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //layout=findViewById(R.id.splash_constraintLayout);
        //checkTest=findViewById(R.id.textView5);
       // constraintSetOld.clone(layout);
       // constraintSetNew.clone(this,R.layout.activity_main);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.left_right_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_top_animation);
        rtlAnim= AnimationUtils.loadAnimation(this,R.anim.right_left_animation);
        text1=findViewById(R.id.textView4);
        text2=findViewById(R.id.textView5);
        text3=findViewById(R.id.textView6);

        text1.setAnimation(topAnim);
        text2.setAnimation(bottomAnim);
        text3.setAnimation(rtlAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // animation
                //TransitionManager.beginDelayedTransition(layout);
                //constraintSetNew.applyTo(layout);

                Support.nextActivity(MainActivity.this, Home.class,"left-to-right");
                finish();
            }
            },3000);

    }

}
