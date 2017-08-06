package io.vilo.viloapp.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by raghavendra on 06/08/17.
 */


public class VideoUrl {

    @Getter
    @Setter
    @SerializedName("url")
    private String url;

    @Getter @Setter
    @SerializedName("src")
    private String src;
}