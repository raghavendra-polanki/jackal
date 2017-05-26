package com.example.raghavendra.vidnet.api;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;


public class BaseRequest {


    @Getter
    private String os = "android";


    public BaseRequest() {

    }
}
