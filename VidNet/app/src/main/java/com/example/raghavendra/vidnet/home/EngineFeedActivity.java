package com.example.raghavendra.vidnet.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raghavendra.vidnet.BaseActivity;
import com.example.raghavendra.vidnet.R;
import com.example.raghavendra.vidnet.VidNetApplication;
import com.example.raghavendra.vidnet.model.VideoModel;
import com.example.raghavendra.vidnet.VideoPlayerFragment;
import com.example.raghavendra.vidnet.utils.NetworkUtils;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.example.raghavendra.vidnet.utils.Constants.RV_CACHE_COUNT;

/**
 * Created by raghavendra on 26/05/17.
 */

public class EngineFeedActivity extends BaseActivity implements
        EngineFeedAdapter.EngineFeedClickListener,
        YouTubePlayer.OnFullscreenListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    private static final int LANDSCAPE_VIDEO_PADDING_DP = 5;

    private static final int ANIMATION_DURATION_MILLIS = 300;


    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(android.R.id.empty)
    View mEmpty;

    @Bind(R.id.tv_placeholder)
    TextView mPlaceholderTv;

    VideoPlayerFragment mVideoPlayerFragment;

    @Bind(R.id.videoplayer_container)
    FrameLayout mVideoPlayerContainer;

    LinearLayoutManager linearLayoutManager;
    private EngineFeedAdapter mAdapter;

    private boolean mIsFullscreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_enginefeed);

        ButterKnife.bind(this);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshVideos();
            }
        });

        refreshVideos();

        mVideoPlayerFragment =
                (VideoPlayerFragment) getFragmentManager().findFragmentById(R.id.videoplayer_fragment);

        mVideoPlayerContainer.setVisibility(View.INVISIBLE);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemViewCacheSize(RV_CACHE_COUNT);

        mAdapter = new EngineFeedAdapter(this, this);

        if(!NetworkUtils.isNetworkAvailable(this)) {
            mEmpty.setVisibility(View.VISIBLE);
        }

        checkYouTubeApi();

        populateUi();

        layout();

//        Intent mIntent = new Intent(this, SplashScreenActivity.class);
//        startActivity(mIntent);

    }

    private void checkYouTubeApi() {
        YouTubeInitializationResult errorReason =
                YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this);
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else if (errorReason != YouTubeInitializationResult.SUCCESS) {
            String errorMessage =
                    String.format(getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    private void refreshVideos(){
        VidNetApplication.getInstance().getApiHandler().getVideoList("",
                new Callback<List<VideoModel>>() {
                    @Override
                    public void success(List<VideoModel> videos, Response response) {
                        mAdapter.add(videos);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                });
    }

    private void layout() {
        boolean isPortrait =
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

//        mRecyclerView.setVisibility(mIsFullscreen ? View.GONE : View.VISIBLE);

        if (mIsFullscreen) {
            mVideoPlayerContainer.setTranslationY(0); // Reset any translation that was applied in portrait.
            setLayoutSize(mVideoPlayerFragment.getView(), MATCH_PARENT, MATCH_PARENT);
            setLayoutSizeAndGravity(mVideoPlayerContainer, MATCH_PARENT, MATCH_PARENT, Gravity.TOP | Gravity.LEFT);
        } else if (isPortrait) {
            setLayoutSize(mRecyclerView, MATCH_PARENT, MATCH_PARENT);
            setLayoutSize(mVideoPlayerFragment.getView(), MATCH_PARENT, MATCH_PARENT);
            setLayoutSizeAndGravity(mVideoPlayerContainer, MATCH_PARENT, MATCH_PARENT, Gravity.BOTTOM);
        } else {
            mVideoPlayerContainer.setTranslationY(0); // Reset any translation that was applied in portrait.
//            int screenWidth = dpToPx(getResources().getConfiguration().screenWidthDp);
            setLayoutSize(mRecyclerView, MATCH_PARENT, MATCH_PARENT);
            setLayoutSize(mVideoPlayerFragment.getView(), MATCH_PARENT, MATCH_PARENT);
            setLayoutSizeAndGravity(mVideoPlayerContainer, MATCH_PARENT, MATCH_PARENT,
                    Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        }
    }
    private void populateUi() {
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Recreate the activity if user performed a recovery action
            recreate();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        layout();
    }

    @Override
    public void onFullscreen(boolean isFullscreen) {
        this.mIsFullscreen = isFullscreen;

//        if(!isFullscreen){
//            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }

        layout();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onVideoClicked(VideoModel videoModel) {

        String videoId = videoModel.getVideoid();

        mVideoPlayerFragment.setVideoId(videoId);


        // The videoBox is INVISIBLE if no video was previously selected, so we need to show it now.
        if (mVideoPlayerContainer.getVisibility() != View.VISIBLE) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                // Initially translate off the screen so that it can be animated in from below.
//                mVideoPlayerContainer.setTranslationY(mVideoPlayerContainer.getHeight());
            }
            mVideoPlayerContainer.setVisibility(View.VISIBLE);
        }

//        // If the fragment is off the screen, we animate it in.
//        if (mVideoPlayerContainer.getTranslationY() > 0) {
//            mVideoPlayerContainer.animate().translationY(0).setDuration(ANIMATION_DURATION_MILLIS);
//        }

    }

    @Override
    public void onBackPressed() {

        if (mVideoPlayerContainer.getVisibility() != View.VISIBLE) {
            super.onBackPressed();
            return;
        }

        mVideoPlayerFragment.pause();
        mVideoPlayerContainer.setVisibility(View.INVISIBLE);


    }

    // Utility methods for layouting.

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }

    private static void setLayoutSize(View view, int width, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    private static void setLayoutSizeAndGravity(View view, int width, int height, int gravity) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        params.gravity = gravity;
        view.setLayoutParams(params);
    }

}
