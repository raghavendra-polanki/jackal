package io.vilo.viloapp.api;

import android.content.Intent;

import io.vilo.viloapp.ViloApplication;
import io.vilo.viloapp.model.apiResponse.BaseResponse;
import io.vilo.viloapp.utils.Constants;


public abstract class BaseCallback<T extends BaseResponse> extends RestCallback<T> {

    protected boolean isLoggedOut(T t) {
        if (t.isLoggedOut()) {
            Intent intent = new Intent();
            intent.setAction(Constants.BROADCAST_LOGOUT);
            ViloApplication.getInstance().sendBroadcast(intent);
            return true;
        }
        return false;
    }
}
