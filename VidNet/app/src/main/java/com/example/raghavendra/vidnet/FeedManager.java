package com.example.raghavendra.vidnet;

import com.example.raghavendra.vidnet.model.VideoModel;

import java.util.List;

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
        void onFeedRefreshed(VideoModel videoModel);
    }

    public void setEngineFeedListner(EngineFeedListener listener){
        mEngineFeedListner = listener;
    }

    public void initializeEngineFeed(){
        VidNetApplication.getInstance().getApiHandler().getVideoList("",
                new Callback<List<VideoModel>>() {
                    @Override
                    public void success(List<VideoModel> videos, Response response) {
                        mEngineFeed = videos;
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }

    public VideoModel getEngineFeed(int position){

        return mEngineFeed.get(position);
    }

    public interface SocialFeedListener {
        void onFeedRefreshed(boolean refreshStatus);
    }

    public void setSocialFeedListener(SocialFeedListener listener){
        mSocialFeedListner = listener;
    }

    public void initializeSocialFeed(){
        VidNetApplication.getInstance().getApiHandler().getVideoList("",
                new Callback<List<VideoModel>>() {
                    @Override
                    public void success(List<VideoModel> videos, Response response) {
                        mSocialFeed = videos;
                        if(mSocialFeedListner != null){
                            mSocialFeedListner.onFeedRefreshed(true);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if(mSocialFeedListner != null){
                            mSocialFeedListner.onFeedRefreshed(false);
                        }
                    }
                });
    }

    public VideoModel getSocialFeed(int position){

        return mSocialFeed.get(position);
    }

    public int getSocialFeedSize(){
        return mSocialFeed != null ? mSocialFeed.size() : 0;
    }
}
