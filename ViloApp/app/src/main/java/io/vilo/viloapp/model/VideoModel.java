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

    public VideoModel(){
       int i = 0;
    }

    @Getter
    @Setter
    @SerializedName("id")
    private String videoId;

    @Getter
    @Setter
    @SerializedName("is_active")
    private boolean isActive;

    @Getter
    @Setter
    @SerializedName("urls")
    private List<VideoUrl> urls;

    @Getter
    @Setter
    @SerializedName("title")
    private VideoTitle title;

    @Getter @Setter
    @SerializedName("desc")
    private VideoDescription description;


    @Getter @Setter
    @SerializedName("lang")
    private List<String> language;

    @Getter @Setter
    @SerializedName("likes")
    private int likes;

    @Getter @Setter
    @SerializedName("rating")
    private String rating;

//    @Getter @Setter
//    @SerializedName("category")
//    private List<String> category;
}
