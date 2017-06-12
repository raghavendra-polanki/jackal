package com.example.raghavendra.vidnet.api;


import com.example.raghavendra.vidnet.model.VideoModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface APIHandler {

    @GET("/videos")
    void getVideoList(@Query("mid") String param1, Callback<List<VideoModel>> response);

}
