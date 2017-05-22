package com.example.raghavendra.vidnet;

/**
 * Created by raghavendra on 20/05/17.
 */

public  class VideoEntry {
    private final String text;
    private final String videoId;

    public VideoEntry(String text, String videoId) {
        this.text = text;
        this.videoId = videoId;
    }

    public String getText(){
        return this.text;
    }

    public String getVideoId(){
        return this.videoId;
    }
}
