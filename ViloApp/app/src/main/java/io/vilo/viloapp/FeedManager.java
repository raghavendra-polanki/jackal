package io.vilo.viloapp;

import android.view.View;

import io.vilo.viloapp.api.BaseCallback;
import io.vilo.viloapp.api.RestError;
import io.vilo.viloapp.model.VideoModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.vilo.viloapp.model.VideoTitle;
import io.vilo.viloapp.model.VideoUrl;
import io.vilo.viloapp.model.apiRequest.GetEngineFeedRequest;
import io.vilo.viloapp.model.apiRequest.GetSocialFeedRequest;
import io.vilo.viloapp.model.apiResponse.GetEngineFeedResponse;
import io.vilo.viloapp.model.apiResponse.GetSocialFeedResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by raghavendra on 14/06/17.
 */

public class FeedManager {

    private List<VideoModel> mEngineFeed;
    private List<VideoModel> mSocialFeed;

    private EngineFeedListener mEngineFeedListner;
    private SocialFeedListener mSocialFeedListner;

    public interface EngineFeedListener {
        void onFeedRefreshed(boolean refreshStatus);
    }

    public void setEngineFeedListner(EngineFeedListener listener){
        mEngineFeedListner = listener;
    }

    public void initializeEngineFeed(){

        GetEngineFeedRequest request = new GetEngineFeedRequest();
        request.setUserId("17072100132");

        ViloApplication.getInstance().getApiHandler().getEngineFeed(request,new GetEngineFeedCallback());

    }

    private void onGetEngineFeedResponse(GetEngineFeedResponse getEngineFeedResponse) {

        mEngineFeed = getEngineFeedResponse.getVideoList();

        if(mEngineFeedListner != null){
            mEngineFeedListner.onFeedRefreshed(true);
        }
    }

    private class GetEngineFeedCallback extends BaseCallback<GetEngineFeedResponse> {
        @Override
        public void failure(RestError restError) {

            if(mEngineFeedListner != null){
                mEngineFeedListner.onFeedRefreshed(true);
            }
        }

        @Override
        public void success(GetEngineFeedResponse getEngineFeedResponse, Response response) {

            onGetEngineFeedResponse(getEngineFeedResponse);
        }
    }

    public VideoModel getEngineFeed(int position){

        return mEngineFeed.get(position);
    }

    public int getEngineFeedSize(){
        return mEngineFeed != null ? mEngineFeed.size() : 0;
    }


    public interface SocialFeedListener {
        void onFeedRefreshed(boolean refreshStatus);
    }

    public void setSocialFeedListener(SocialFeedListener listener){
        mSocialFeedListner = listener;
    }

    public void initializeSocialFeed(){
        GetSocialFeedRequest request = new GetSocialFeedRequest();
        request.setUserId("17072100132");

        ViloApplication.getInstance().getApiHandler().getSocialFeed(request,new GetSocialFeedCallback());

    }

    private void onGetSocialFeedResponse(GetSocialFeedResponse getSocialFeedResponse) {

        mSocialFeed = getSocialFeedResponse.getVideoList();

        if(mSocialFeedListner != null){
            mSocialFeedListner.onFeedRefreshed(true);
        }
    }

    private class GetSocialFeedCallback extends BaseCallback<GetSocialFeedResponse> {
        @Override
        public void failure(RestError restError) {

            if(mSocialFeedListner != null){
                mSocialFeedListner.onFeedRefreshed(true);
            }
        }

        @Override
        public void success(GetSocialFeedResponse getSocialFeedResponse, Response response) {

            onGetSocialFeedResponse(getSocialFeedResponse);
        }
    }
    public VideoModel getSocialFeed(int position){

        return mSocialFeed.get(position);
    }

    public int getSocialFeedSize(){
        return mSocialFeed != null ? mSocialFeed.size() : 0;
    }

}
