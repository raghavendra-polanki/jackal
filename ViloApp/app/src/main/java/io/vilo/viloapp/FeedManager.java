package io.vilo.viloapp;

import org.json.JSONException;
import org.json.JSONObject;

import io.vilo.viloapp.model.VideoModel;

import java.util.ArrayList;
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
//        ViloApplication.getInstance().getApiHandler().getVideoList("",
//                new Callback<List<VideoModel>>() {
//                    @Override
//                    public void success(List<VideoModel> videos, Response response) {
//                        mEngineFeed = videos;
//                        Collections.reverse(mEngineFeed);
//                        if(mEngineFeedListner != null){
//                            mEngineFeedListner.onFeedRefreshed(true);
//                        }
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        if(mEngineFeedListner != null){
//                            mEngineFeedListner.onFeedRefreshed(true);
//                        }
//                    }
//                });

        List<VideoModel> videos = this.getVideos();

        mEngineFeed = videos;
        Collections.reverse(mEngineFeed);
        if(mEngineFeedListner != null){
            mEngineFeedListner.onFeedRefreshed(true);
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
//        ViloApplication.getInstance().getApiHandler().getVideoList("",
//                new Callback<List<VideoModel>>() {
//                    @Override
//                    public void success(List<VideoModel> videos, Response response) {
//                        mSocialFeed = videos;
//                        if(mSocialFeedListner != null){
//                            mSocialFeedListner.onFeedRefreshed(true);
//                        }
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        if(mSocialFeedListner != null){
//                            mSocialFeedListner.onFeedRefreshed(false);
//                        }
//                    }
//                });

        List<VideoModel> videos = this.getVideos();

        mSocialFeed = videos;
        if(mSocialFeedListner != null){
            mSocialFeedListner.onFeedRefreshed(true);
        }
    }

    public VideoModel getSocialFeed(int position){

        return mSocialFeed.get(position);
    }

    public int getSocialFeedSize(){
        return mSocialFeed != null ? mSocialFeed.size() : 0;
    }

    private ArrayList<VideoModel> getVideos(){
//        JSONObject videoModel = new JSONObject();
//        try {
//            videoModel.put("id", "3");
//            videoModel.put("title", "Try not laughing - Pranks");
//            videoModel.put("description", "Try not to laugh while watching this funniest video of chinese funny pranks. This video is really hilarious and impossible to not laugh");
//            videoModel.put("videoid", "J73lp3etxE");
//            videoModel.put("age", 18);
//            videoModel.put("language", "language");
//            videoModel.put("duration", 10);
//            videoModel.put("source_site", "youtube.com");
//            videoModel.put("updated_at", "2017-05-24T05:37:39.318Z");
//            videoModel.put("category", "funny");
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        ArrayList<VideoModel> videos = new ArrayList<VideoModel>();

        VideoModel video = new VideoModel();
        video.setVideoid("A_UEdAZ9hU0");
        video.setTitle("Try not laughing - Pranks");

        videos.add(video);
        videos.add(video);
        videos.add(video);
        videos.add(video);

        return videos;


    }
}
