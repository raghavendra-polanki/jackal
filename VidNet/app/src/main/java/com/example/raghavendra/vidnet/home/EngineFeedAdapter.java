package com.example.raghavendra.vidnet.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raghavendra.vidnet.FeedManager;
import com.example.raghavendra.vidnet.R;
import com.example.raghavendra.vidnet.VidNetApplication;
import com.example.raghavendra.vidnet.model.VideoModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by raghavendra on 26/05/17.
 */

public class EngineFeedAdapter extends RecyclerView.Adapter implements
        View.OnClickListener{

    public interface EngineFeedClickListener {
        void onVideoClicked(VideoModel videoModel);
    }

    private EngineFeedClickListener mListener;
    private Context mContext;
    private FeedManager mFeedManager;



    public EngineFeedAdapter(Context context, EngineFeedClickListener listener) {
        mContext = context;
        mListener = listener;
        mFeedManager = VidNetApplication.getInstance().getFeedManager();
    }

    public void onFeedUpdated(){
        notifyDataSetChanged();
    }


    public void add(List<VideoModel> videoList) {
        notifyDataSetChanged();
    }

    public void clear() {
        notifyDataSetChanged();
    }

    public void updateViews() {
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {

        return mFeedManager.getEngineFeedSize();
    }

    @Override
    public void onClick(View v) {

        if (v.getTag(R.id.key_asset) != null && v.getTag(R.id.key_asset) instanceof VideoModel) {
            mListener.onVideoClicked((VideoModel) v.getTag(R.id.key_asset));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VideoModel videoModel = mFeedManager.getEngineFeed(position);
        ((VideoViewHolder)holder).bind(videoModel);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new VideoViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_videomodel, parent, false), this);
    }

}
