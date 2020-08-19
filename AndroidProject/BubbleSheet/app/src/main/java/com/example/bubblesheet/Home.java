package com.example.bubblesheet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import maes.tech.intentanim.CustomIntent;

public class Home extends AppCompatActivity {

    Button btn_log,btn_register;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        btn_log=findViewById(R.id.btn_login);
        btn_register=findViewById(R.id.home_btn_reg);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Support.nextActivity(Home.this, register.class,"bottom-to-up");
            }
        });

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Support.nextActivity(Home.this, Login.class,"left-to-right");
            }
        });

    }

}
