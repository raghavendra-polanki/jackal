package io.vilo.viloapp;

import android.os.Bundle;

import io.vilo.viloapp.home.EngineFeedActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;


/**
 * Created by raghavendra on 21/05/17.
 */

public class VideoPlayerFragment extends YouTubePlayerFragment implements
             YouTubePlayer.OnInitializedListener {

    private YouTubePlayer m_youTubePlayer;
    String m_videoId;

    public static VideoPlayerFragment newInstance() {
        return new VideoPlayerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize(DeveloperKey.DEVELOPER_KEY, this);
    }

    @Override
    public void onDestroy() {
        if (m_youTubePlayer != null) {
            m_youTubePlayer.release();
        }
        super.onDestroy();
    }

    public void setVideoId(String videoId) {
        if (videoId != null) {
            if(!videoId.equals(this.m_videoId)) {
                this.m_videoId = videoId;
                if (m_youTubePlayer != null) {
                    m_youTubePlayer.loadVideo(this.m_videoId);
                }
            }else{
                m_youTubePlayer.play();
            }
        }
    }

    public void pause() {
        if (m_youTubePlayer != null) {
            m_youTubePlayer.pause();
        }
    }

//    public boolean onBackButtonPressed(){
//
//        if(mIsFullScreen){
//            m_youTubePlayer.setFullscreen(false);
//            return false;
//        }else{
//            return true;
//        }
//
//    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {

        this.m_youTubePlayer = youTubePlayer;
        youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        youTubePlayer.setOnFullscreenListener((EngineFeedActivity) getActivity());
        if (!restored && this.m_videoId != null) {
            youTubePlayer.loadVideo(this.m_videoId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        this.m_youTubePlayer = null;
    }
}
