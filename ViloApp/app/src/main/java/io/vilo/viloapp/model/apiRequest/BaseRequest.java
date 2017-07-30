package io.vilo.viloapp.model.apiRequest;

import io.vilo.viloapp.ViloApplication;
import io.vilo.viloapp.utils.LocalStorage;
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
    private int versionCode = ViloApplication.getInstance().getVersionCode();

    public BaseRequest() {
        idToken = LocalStorage.getInstance().getUser().getIdToken();
    }
}
