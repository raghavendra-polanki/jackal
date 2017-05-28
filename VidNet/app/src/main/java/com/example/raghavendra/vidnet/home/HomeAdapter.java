package com.example.raghavendra.vidnet.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raghavendra.vidnet.R;
import com.example.raghavendra.vidnet.VideoEntry;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by raghavendra on 26/05/17.
 */

public class HomeAdapter extends RecyclerView.Adapter implements
        View.OnClickListener{

    public interface HomeClickListener {
        void onVideoClicked(VideoEntry videoEntry);
    }

    private List<VideoEntry> mvideoEntries;
    private HomeClickListener mListener;
    private Context mContext;


    public HomeAdapter(Context context, HomeClickListener listener) {
        mContext = context;
        mListener = listener;
        mvideoEntries = new ArrayList<>();
    }

    public void add(List<VideoEntry> videoList) {
        mvideoEntries = videoList;
        notifyDataSetChanged();
    }

    public void clear() {
        mvideoEntries.clear();
        notifyDataSetChanged();
    }

    public void updateViews() {
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return mvideoEntries != null ? mvideoEntries.size() : 0;
    }

    @Override
    public void onClick(View v) {

        if (v.getTag(R.id.key_asset) != null && v.getTag(R.id.key_asset) instanceof VideoEntry) {
            mListener.onVideoClicked((VideoEntry) v.getTag(R.id.key_asset));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VideoEntry videoEntry = mvideoEntries.get(position);
        ((VideoViewHolder)holder).bind(videoEntry);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new VideoViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.videolist_element, parent, false), this);
    }

}
