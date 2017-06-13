package com.example.raghavendra.vidnet.model.apiRequest;

import com.google.gson.annotations.SerializedName;

import lombok.Setter;

public class AuthenticateOtpRequest {

    private String mno;

    private String otp;

    private String os = "android";

    @Setter
    @SerializedName("device_id")
    private String deviceId;

    @Setter
    @SerializedName("version_code")
    private int versionCode;

    public AuthenticateOtpRequest(String mno, String otp) {
        this.mno = mno;
        this.otp = otp;
    }
}
