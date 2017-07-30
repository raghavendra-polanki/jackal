package io.vilo.viloapp;

import io.vilo.viloapp.model.VideoModel;

import java.util.Collections;
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
        void onFeedRefreshed(boolean refreshStatus);
    }

    public void setEngineFeedListner(EngineFeedListener listener){
        mEngineFeedListner = listener;
    }

    public void initializeEngineFeed(){
        ViloApplication.getInstance().getApiHandler().getVideoList("",
                new Callback<List<VideoModel>>() {
                    @Override
                    public void success(List<VideoModel> videos, Response response) {
                        mEngineFeed = videos;
                        Collections.reverse(mEngineFeed);
                        if(mEngineFeedListner != null){
                            mEngineFeedListner.onFeedRefreshed(true);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if(mEngineFeedListner != null){
                            mEngineFeedListner.onFeedRefreshed(true);
                        }
                    }
                });
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
        ViloApplication.getInstance().getApiHandler().getVideoList("",
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
