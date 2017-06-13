package com.example.raghavendra.vidnet.model.apiResponse;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by shubham on 30/10/15.
 */
public class RegisterAppResponse extends BaseResponse {

    @Getter
    @Setter
    @SerializedName("is_new")
    private boolean isNew;
}
