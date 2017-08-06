package io.vilo.viloapp.api;


import io.vilo.viloapp.model.User;
import io.vilo.viloapp.model.VideoModel;
import io.vilo.viloapp.model.apiRequest.AuthenticateOtpRequest;
import io.vilo.viloapp.model.apiRequest.GetEngineFeedRequest;
import io.vilo.viloapp.model.apiRequest.GetSocialFeedRequest;
import io.vilo.viloapp.model.apiRequest.RegisterAppRequest;
import io.vilo.viloapp.model.apiResponse.GetEngineFeedResponse;
import io.vilo.viloapp.model.apiResponse.GetSocialFeedResponse;
import io.vilo.viloapp.model.apiResponse.RegisterAppResponse;

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

    @POST("/feeds/engine")
    void getEngineFeed(@Body GetEngineFeedRequest request, Callback<GetEngineFeedResponse> response);

    @POST("/feeds/social")
    void getSocialFeed(@Body GetSocialFeedRequest request, Callback<GetSocialFeedResponse> response);

}
