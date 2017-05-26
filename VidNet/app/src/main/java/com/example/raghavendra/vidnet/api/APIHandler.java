package com.example.raghavendra.vidnet.api;


import com.example.raghavendra.vidnet.VideoEntry;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Query;

public interface APIHandler {

    @GET("/videos")
    void getVideoList(@Query("mid") String param1, Callback<List<VideoEntry>> response);

}
