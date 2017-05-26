package com.example.raghavendra.vidnet.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.raghavendra.vidnet.R;

/**
 *
 * Created by shishank on 22/11/15.
 */
public class NetworkUtils {

    public static boolean isNetworkAvailable(final Context ctx) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static void showNoInternetConnection(final View view, final Context ctx) {
        Snackbar.make(view, ctx.getString(R.string.no_internet), Snackbar.LENGTH_SHORT).show();
    }
}
