package com.example.raghavendra.vidnet.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by shubham on 03/11/15.
 */
public class PhoneNumber {

    @Getter @Setter
    private String isd;

    @Getter @Setter
    private String num;

    @Getter @Setter
    private String type;
}
