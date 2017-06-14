package com.example.raghavendra.vidnet.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.raghavendra.vidnet.R;
import com.example.raghavendra.vidnet.home.EngineFeedActivity;
import com.example.raghavendra.vidnet.home.HomeSliderActivity;
import com.example.raghavendra.vidnet.utils.LocalStorage;


import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;


    @Bind(R.id.splash_image)
    ImageView mSplashScreen;

    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goForward();
//                finish();
            }
        }, SPLASH_TIME_OUT);


    }


    private void goForward() {
//        PushNotificationContent content = null;
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null && bundle.containsKey(KEY_PUSH_CONTENT)) {
//            content = bundle.getParcelable(KEY_PUSH_CONTENT);
//        }

        if(LocalStorage.getInstance().getUser() != null) {
            Intent intent = new Intent(SplashScreenActivity.this, HomeSliderActivity.class);
//            if (content != null) {
//                intent.putExtra(KEY_PUSH_CONTENT, content);
//            }
            startActivity(intent);
            finish();
        } else {
            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            finish();
        }

    }

}
