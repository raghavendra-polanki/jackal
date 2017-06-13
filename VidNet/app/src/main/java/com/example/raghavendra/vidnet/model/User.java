package com.example.raghavendra.vidnet.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


public class User {

    @Getter @Setter
    private int sts;

    @Getter @Setter
    @SerializedName("id_token")
    private String idToken;

    @Getter @Setter
    @SerializedName("slt")
    private String salutation;

    @Getter @Setter
    @SerializedName("dst_id")
    private String distributorId;

    @Getter @Setter
    @SerializedName("fnm")
    private String firstName;

    @Getter @Setter
    @SerializedName("lnm")
    private String lastName;

    @Getter @Setter
    @SerializedName("eid")
    private List<Email> emailIdList;

    @Getter @Setter
    @SerializedName("phn")
    List<PhoneNumber> phoneNumberList;

    @Getter @Setter
    @SerializedName("dob")
    private String dateOfBirth;

    @Getter @Setter
    @SerializedName("addr")
    private Address address;

    @Getter @Setter
    @SerializedName("has_pub")
    private boolean hasPub;

    @Getter @Setter
    @SerializedName("is_active")
    private boolean isActive;
}
