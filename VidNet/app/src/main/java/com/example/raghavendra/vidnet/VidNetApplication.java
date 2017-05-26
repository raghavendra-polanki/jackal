package com.example.raghavendra.vidnet;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;


import com.example.raghavendra.vidnet.api.APIHandler;
import com.example.raghavendra.vidnet.utils.Constants;
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


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;


        initApiHandler();

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

    public void setForeground(boolean foregound) {
        isAppInForeground = foregound;
    }

    public boolean isAppInForeground() {
        return isAppInForeground;
    }

}
