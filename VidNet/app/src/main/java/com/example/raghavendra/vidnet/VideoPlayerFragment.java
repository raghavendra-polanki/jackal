package com.example.raghavendra.vidnet;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.ListPopupWindow.MATCH_PARENT;


/**
 * Created by raghavendra on 21/05/17.
 */

public class VideoPlayerFragment extends YouTubePlayerFragment implements
             YouTubePlayer.OnInitializedListener ,
             YouTubePlayer.OnFullscreenListener{

    private YouTubePlayer m_youTubePlayer;
    String m_videoId;
    boolean mIsFullScreen;
    View mView;


    public static VideoPlayerFragment newInstance() {
        return new VideoPlayerFragment();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        initialize(DeveloperKey.DEVELOPER_KEY, this);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {


        View view = super.onCreateView(layoutInflater, viewGroup, bundle);


        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)viewGroup.getLayoutParams();
        if(params != null) {
            params.width = MATCH_PARENT;
            params.height = MATCH_PARENT;
            params.gravity = Gravity.CENTER;
            view.setLayoutParams(params);
        }


        return  view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        m_youTubePlayer.release();
    }

    public void setVideoId(String videoId) {
        if (videoId != null && !videoId.equals(this.m_videoId)) {
            this.m_videoId = videoId;
        }
    }

    public void pause() {
        if (m_youTubePlayer != null) {
            m_youTubePlayer.pause();
        }
    }

    public boolean onBackButtonPressed(){

        if(mIsFullScreen){
            m_youTubePlayer.setFullscreen(false);
            return false;
        }else{
            return true;
        }

    }

    @Override
    public void onFullscreen(boolean b) {
        mIsFullScreen = b;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {

        this.m_youTubePlayer = youTubePlayer;
        m_youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
        m_youTubePlayer.setOnFullscreenListener(this);
        mView = getView();
//        youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
//        youTubePlayer.setOnFullscreenListener((VideoListDemoActivity) getActivity());
        if (!restored && this.m_videoId != null) {
            youTubePlayer.loadVideo(this.m_videoId);
        }else{
            youTubePlayer.play();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        this.m_youTubePlayer = null;
    }
}
