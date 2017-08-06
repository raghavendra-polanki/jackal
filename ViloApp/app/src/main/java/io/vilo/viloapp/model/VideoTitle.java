package io.vilo.viloapp.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by raghavendra on 06/08/17.
 */

public class VideoTitle {

    @Getter
    @Setter
    @SerializedName("english")
    private String english;

    @Getter
    @Setter
    @SerializedName("hindi")
    private String hindi;
}
