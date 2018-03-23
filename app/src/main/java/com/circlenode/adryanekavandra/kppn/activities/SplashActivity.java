package com.circlenode.adryanekavandra.kppn.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.circlenode.adryanekavandra.kppn.R;
import com.circlenode.adryanekavandra.kppn.utils.SessionManager;
import com.facebook.stetho.Stetho;
import com.squareup.picasso.Picasso;

import khangtran.preferenceshelper.PreferencesHelper;

/**
 * Created by Adryan Eka Vandra on 3/22/2018.
 */

public class SplashActivity extends AppCompatActivity {


    SessionManager sessionManager;
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        sessionManager = new SessionManager(this);
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        imageView = (ImageView)findViewById(R.id.gambarKPPN);

        changeStatusBarColor();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Boolean isLoggedIn = sessionManager.isLoggedIn();

                if(isLoggedIn){
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();
                }

            }
        },3000);

    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
