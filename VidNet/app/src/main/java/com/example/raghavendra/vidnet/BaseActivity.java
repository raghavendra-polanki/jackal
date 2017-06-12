package com.example.raghavendra.vidnet;

import android.support.v7.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {


    protected void showProgress(String message) {

    }

    protected void hideProgress() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        VidNetApplication.getInstance().setForeground(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        VidNetApplication.getInstance().setForeground(false);
        getResources();
    }
}