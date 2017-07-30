package io.vilo.viloapp;

import android.support.v7.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {


    protected void showProgress(String message) {

    }

    protected void hideProgress() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        ViloApplication.getInstance().setForeground(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ViloApplication.getInstance().setForeground(false);
        getResources();
    }
}