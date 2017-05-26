package com.example.raghavendra.vidnet;

import android.support.v7.app.AppCompatActivity;

import com.example.raghavendra.vidnet.widgets.VidNetProgressDialog;

public abstract class BaseActivity extends AppCompatActivity {

    private VidNetProgressDialog mDialog;

    protected void showProgress(String message) {
        mDialog = VidNetProgressDialog.newInstance(message);
        mDialog.show(getSupportFragmentManager(), "progress");
    }

    protected void hideProgress() {
        if(mDialog != null) {
            mDialog.dismiss();
        }
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
    }
}