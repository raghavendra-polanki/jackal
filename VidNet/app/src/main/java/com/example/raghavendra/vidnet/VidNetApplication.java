package com.example.raghavendra.vidnet;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;


import com.example.raghavendra.vidnet.api.APIHandler;
import com.example.raghavendra.vidnet.utils.Constants;
import com.example.raghavendra.vidnet.utils.LocalStorage;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import lombok.Getter;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;

/**
 * Created by raghavendra on 25/05/17.
 */

public class VidNetApplication extends Application {


    private static VidNetApplication sInstance;

    /**
     * Is the app in foreground. Place this here instead of MemoryStore as it is needed in the GCM
     * service, and MemoryStore registers itself on the bus, hence can't be used from outside the
     * main thread.
     */
    private boolean isAppInForeground = true;

    @Getter
    private APIHandler apiHandler;

    @Getter
    private FeedManager feedManager;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        LocalStorage.getInstance(this);


        initApiHandler();

        feedManager = new FeedManager();
        feedManager.initializeEngineFeed();
        feedManager.initializeSocialFeed();


    }

    public static VidNetApplication getInstance() {
        return sInstance;
    }

    private void initApiHandler() {
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.setConnectTimeout(20, TimeUnit.SECONDS);
        httpClient.setReadTimeout(30, TimeUnit.SECONDS);
        httpClient.setWriteTimeout(30, TimeUnit.SECONDS);
        OkClient client = new OkClient(httpClient);

        apiHandler = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)
                .setClient(client)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("API"))
                .build()
                .create(APIHandler.class);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public int getVersionCode() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    public void setForeground(boolean foregound) {
        isAppInForeground = foregound;
    }

    public boolean isAppInForeground() {
        return isAppInForeground;
    }

}
