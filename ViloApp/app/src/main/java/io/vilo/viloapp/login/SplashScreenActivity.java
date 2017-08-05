package io.vilo.viloapp.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import io.vilo.viloapp.R;
import io.vilo.viloapp.home.HomeSliderActivity;
import io.vilo.viloapp.utils.LocalStorage;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 500;


    @Bind(R.id.splash_screen_logo)
    ImageView mSplashScreenLogo;

    @Bind(R.id.splash_screen_name)
    ImageView mSplashScreenName;

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
                mSplashScreenLogo.setColorFilter(Color.rgb(56,146,181));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSplashScreenLogo.setColorFilter(Color.rgb(230,79,244));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mSplashScreenLogo.setColorFilter(Color.rgb(4,251,27));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        goForward();
                                    }
                                }, SPLASH_TIME_OUT);
                            }
                        }, SPLASH_TIME_OUT);
                    }
                }, SPLASH_TIME_OUT);
            }
        }, SPLASH_TIME_OUT);


    }


    private void goForward() {
//        PushNotificationContent content = null;
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null && bundle.containsKey(KEY_PUSH_CONTENT)) {
//            content = bundle.getParcelable(KEY_PUSH_CONTENT);
//        }

        //if(LocalStorage.getInstance().getUser() != null) {
            Intent intent = new Intent(SplashScreenActivity.this, HomeSliderActivity.class);
//            if (content != null) {
//                intent.putExtra(KEY_PUSH_CONTENT, content);
//            }
            startActivity(intent);
            finish();
//        } else {
//            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
//            finish();
//        }

    }

}
