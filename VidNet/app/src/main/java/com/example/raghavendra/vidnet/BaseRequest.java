package com.example.raghavendra.vidnet;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;


public class BaseRequest {


    @Getter
    private String os = "android";


    public BaseRequest() {

    }
}
