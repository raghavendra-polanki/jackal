package io.vilo.viloapp.model.apiRequest;

import com.google.gson.annotations.SerializedName;

import io.vilo.viloapp.utils.LocalStorage;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by raghavendra on 06/08/17.
 */

public class GetEngineFeedRequest extends BaseRequest {

    @Getter
    @Setter
    @SerializedName("user_id")
    private String userId;

    @Getter
    @SerializedName("device_id")
    private String deviceId = LocalStorage.getInstance().getGcmId();
}
