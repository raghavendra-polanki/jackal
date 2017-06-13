package com.example.raghavendra.vidnet.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


public class Address implements Serializable {

    @Getter @Setter
    @SerializedName("adr_ln")
    private String addressLine;

    @Getter @Setter
    private String area;

    @Getter @Setter
    private String city;

    @Getter @Setter
    private String state;

    @Getter @Setter
    @SerializedName("pin")
    private String pinCode;

    @Getter @Setter
    @SerializedName("cntry")
    private String country;
}
