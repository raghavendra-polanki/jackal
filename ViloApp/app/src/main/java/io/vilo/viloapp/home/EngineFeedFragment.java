package io.vilo.viloapp.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.vilo.viloapp.FeedManager;
import io.vilo.viloapp.R;
import io.vilo.viloapp.ViloApplication;
import io.vilo.viloapp.model.VideoModel;
import io.vilo.viloapp.utils.NetworkUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

import static io.vilo.viloapp.utils.Constants.RV_CACHE_COUNT;

/**
 * Created by raghavendra on 14/06/17.
 */

public class EngineFeedFragment extends Fragment implements
        EngineFeedAdapter.EngineFeedClickListener,
        FeedManager.EngineFeedListener{

    @Bind(R.id.enginefeed_swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.enginefeed_recyclerview)
    RecyclerView mRecyclerView;

    @Bind(R.id.layout_enginefeed)
    View placeHolderLayout;

    @Bind(R.id.tv_enginefeed)
    TextView placeHolderTextView;

    private FeedManager mFeedManager;
    private EngineFeedAdapter mAdapter;

    LinearLayoutManager linearLayoutManager;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static EngineFeedFragment create(int pageNumber) {
        EngineFeedFragment fragment = new EngineFeedFragment();
        return fragment;
    }

    public EngineFeedFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_enginefeed, container, false);

        ButterKnife.bind(this,rootView);

        mFeedManager = ViloApplication.getInstance().getFeedManager();

        mFeedManager.setEngineFeedListner(this);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                mFeedManager.initializeSocialFeed();
            }
        });


        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemViewCacheSize(RV_CACHE_COUNT);

        mAdapter = new EngineFeedAdapter(getContext(), this);

        if(!NetworkUtils.isNetworkAvailable(getContext())) {
            placeHolderLayout.setVisibility(View.VISIBLE);
        }


        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onVideoClicked(VideoModel videoModel) {

    }

    @Override
    public void onFeedRefreshed(boolean refreshStatus){
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.onFeedUpdated();
    }
}
