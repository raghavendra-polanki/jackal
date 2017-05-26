package com.example.raghavendra.vidnet.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.raghavendra.vidnet.BaseActivity;
import com.example.raghavendra.vidnet.R;
import com.example.raghavendra.vidnet.VidNetApplication;
import com.example.raghavendra.vidnet.VideoEntry;
import com.example.raghavendra.vidnet.VideoPlayerFragment;
import com.example.raghavendra.vidnet.utils.NetworkUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by raghavendra on 26/05/17.
 */

public class HomeActivity extends BaseActivity implements
        HomeAdapter.HomeClickListener {

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(android.R.id.empty)
    View mEmpty;

    @Bind(R.id.tv_placeholder)
    TextView mPlaceholderTv;

//    @Bind(android.R.id.progress)
//    View mProgress;

    LinearLayoutManager linearLayoutManager;
    private HomeAdapter mAdapter;

    private VideoPlayerFragment mVideoPlayerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new HomeAdapter(this);

        populateUi();
        if(!NetworkUtils.isNetworkAvailable(this)) {
            mEmpty.setVisibility(View.VISIBLE);
//            mProgress.setVisibility(View.GONE);
        }


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshVideos();
            }
        });

        refreshVideos();

        mVideoPlayerFragment = new VideoPlayerFragment();
    }

    private void refreshVideos(){
        VidNetApplication.getInstance().getApiHandler().getVideoList("",
                new Callback<List<VideoEntry>>() {
                    @Override
                    public void success(List<VideoEntry> videos, Response response) {
                        mAdapter.add(videos);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                });
    }

    private void populateUi() {
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.updateViews();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onVideoClicked(VideoEntry videoEntry) {

        String videoId = videoEntry.getVideoid();

        getFragmentManager().beginTransaction()
                .replace(R.id.videolist_container, mVideoPlayerFragment, null)
                .addToBackStack(null)
                .commit();
        mVideoPlayerFragment.setVideoId(videoId);

    }

}
