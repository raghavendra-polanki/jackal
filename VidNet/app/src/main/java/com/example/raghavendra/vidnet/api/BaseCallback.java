package com.example.raghavendra.vidnet.api;

import android.content.Intent;

import com.example.raghavendra.vidnet.VidNetApplication;
import com.example.raghavendra.vidnet.model.apiResponse.BaseResponse;
import com.example.raghavendra.vidnet.utils.Constants;


public abstract class BaseCallback<T extends BaseResponse> extends RestCallback<T> {

    protected boolean isLoggedOut(T t) {
        if (t.isLoggedOut()) {
            Intent intent = new Intent();
            intent.setAction(Constants.BROADCAST_LOGOUT);
            VidNetApplication.getInstance().sendBroadcast(intent);
            return true;
        }
        return false;
    }
}
