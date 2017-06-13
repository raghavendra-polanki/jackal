package com.example.raghavendra.vidnet.api;


import com.example.raghavendra.vidnet.model.User;
import com.example.raghavendra.vidnet.model.VideoModel;
import com.example.raghavendra.vidnet.model.apiRequest.AuthenticateOtpRequest;
import com.example.raghavendra.vidnet.model.apiRequest.RegisterAppRequest;
import com.example.raghavendra.vidnet.model.apiResponse.RegisterAppResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface APIHandler {

    @POST("/service/authorisation/app_register")
    void registerApp(@Body RegisterAppRequest request, Callback<RegisterAppResponse> response);

    @POST("/service/authorisation/otp_auth")
    void authenticateOtp(@Body AuthenticateOtpRequest request,
                         Callback<User> response);

    @GET("/videos")
    void getVideoList(@Query("mid") String param1, Callback<List<VideoModel>> response);

}
