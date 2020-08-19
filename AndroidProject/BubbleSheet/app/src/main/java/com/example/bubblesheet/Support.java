package com.example.bubblesheet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;


import com.tapadoo.alerter.Alerter;

import maes.tech.intentanim.CustomIntent;

public class Support {
    public static void nextActivity(Context context, Class c,String action){

        Intent i =new Intent(context,c);
        context.startActivity(i);
        //"left-to-right"
        CustomIntent.customType(context,action);

    }

    public static void Alerter(Activity activity, String title, String text){
        Alerter.create(activity)
                .setTitle(title)
                .setText(text)
                .enableSwipeToDismiss()
                .setDuration(4000)
                .setBackgroundColorRes(R.color.colorPrimary)
                .show();
    }
}
