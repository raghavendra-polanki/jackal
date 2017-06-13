package com.example.raghavendra.vidnet.model.apiRequest;

import com.example.raghavendra.vidnet.VidNetApplication;
import com.example.raghavendra.vidnet.utils.LocalStorage;
import com.google.gson.annotations.SerializedName;


import lombok.Getter;
import lombok.Setter;


public class BaseRequest {

    @Getter @Setter
    @SerializedName("id_token")
    private String idToken;

    @Getter
    private String os = "android";

    @Getter
    @SerializedName("version_code")
    private int versionCode = VidNetApplication.getInstance().getVersionCode();

    public BaseRequest() {
        idToken = LocalStorage.getInstance().getUser().getIdToken();
    }
}
