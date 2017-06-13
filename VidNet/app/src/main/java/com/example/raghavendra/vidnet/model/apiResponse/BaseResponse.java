package com.example.raghavendra.vidnet.model.apiResponse;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class BaseResponse {

    private static final String ERROR_CODE_NOT_REGISTERED = "NOT_REG";

    @Getter
    @Setter
    @SerializedName("sts")
    int status;

    @Getter
    @Setter
    @SerializedName("err_code")
    String errorCode;

    @Getter
    @Setter
    @SerializedName("err_msg")
    String errorMessage;

    public boolean isLoggedOut() {
        return !TextUtils.isEmpty(errorCode) && errorCode.equals(ERROR_CODE_NOT_REGISTERED);
    }
}
