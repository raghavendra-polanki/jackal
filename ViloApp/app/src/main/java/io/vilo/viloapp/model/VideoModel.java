package io.vilo.viloapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by raghavendra on 20/05/17.
 */

public  class VideoModel implements Serializable {

    @Getter
    @Setter
    @SerializedName("videoid")
    private String videoid;

    @Getter
    @Setter
    @SerializedName("title")
    private String title;

    @Getter @Setter
    @SerializedName("description")
    private String description;

    @Getter @Setter
    @SerializedName("age")
    private int age;

    @Getter @Setter
    @SerializedName("language")
    private String language;

    @Getter @Setter
    @SerializedName("duration")
    private int duration;

//    @Getter @Setter
//    @SerializedName("popularity")
//    private String popularity;

    @Getter @Setter
    @SerializedName("category")
    private List<String> category;
}
