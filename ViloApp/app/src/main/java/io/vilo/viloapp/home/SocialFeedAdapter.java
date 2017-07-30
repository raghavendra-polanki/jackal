package io.vilo.viloapp.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.vilo.viloapp.FeedManager;
import io.vilo.viloapp.R;
import io.vilo.viloapp.ViloApplication;
import io.vilo.viloapp.model.VideoModel;

/**
 * Created by raghavendra on 14/06/17.
 */

public class SocialFeedAdapter extends RecyclerView.Adapter implements
        View.OnClickListener{

    public interface SocialFeedClickListener {
        void onVideoClicked(VideoModel videoModel);
    }

    private SocialFeedAdapter.SocialFeedClickListener mListener;
    private Context mContext;
    private FeedManager mFeedManager;


    public SocialFeedAdapter(Context context, SocialFeedAdapter.SocialFeedClickListener listener) {
        mContext = context;
        mListener = listener;
        mFeedManager = ViloApplication.getInstance().getFeedManager();
    }

    public void onFeedUpdated(){
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mFeedManager.getSocialFeedSize();
    }

    @Override
    public void onClick(View v) {

        if (v.getTag(R.id.key_asset) != null && v.getTag(R.id.key_asset) instanceof VideoModel) {
            mListener.onVideoClicked((VideoModel) v.getTag(R.id.key_asset));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VideoModel videoModel = mFeedManager.getSocialFeed(position);
        ((VideoViewHolder)holder).bind(videoModel);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new VideoViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_videomodel, parent, false), this);
    }
}
