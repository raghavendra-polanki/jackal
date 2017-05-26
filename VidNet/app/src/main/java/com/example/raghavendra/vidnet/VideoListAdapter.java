package com.example.raghavendra.vidnet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by raghavendra on 20/05/17.
 */

public class VideoListAdapter extends BaseAdapter {

    private List<VideoEntry> m_videoEntries;
    private List<View> m_videoViews;
    LayoutInflater m_inflator;
    private final Map<YouTubeThumbnailView, YouTubeThumbnailLoader> m_thumbnailViewToLoaderMap;
    private final ThumbnailListener m_thumbnailListener;

    public VideoListAdapter(Context context, List<VideoEntry> entries) {
        this.m_videoEntries = entries;
        this.m_videoViews = new ArrayList<View>();

        m_inflator = LayoutInflater.from(context);

        m_thumbnailViewToLoaderMap = new HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>();
        m_thumbnailListener = new ThumbnailListener();

    }

    public void setVideos(List<VideoEntry> videos){
        m_videoEntries.clear();
        m_videoEntries.addAll(videos);
        notifyDataSetChanged();
    };

    @Override
    public int getCount() {
        return this.m_videoEntries.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public VideoEntry getItem(int position) {
        return m_videoEntries.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        VideoEntry entry = m_videoEntries.get(position);

        if(view == null){
            view = m_inflator.inflate(R.layout.videolist_element, parent, false);
            YouTubeThumbnailView thumbnail = (YouTubeThumbnailView) view.findViewById(R.id.thumbnail);
            thumbnail.setTag(entry.getVideoid());
            thumbnail.initialize(DeveloperKey.DEVELOPER_KEY, m_thumbnailListener);

        }else {
            YouTubeThumbnailView thumbnail = (YouTubeThumbnailView) view.findViewById(R.id.thumbnail);
            YouTubeThumbnailLoader loader = m_thumbnailViewToLoaderMap.get(thumbnail);
            if (loader == null) {
                // 2) The view is already created, and is currently being initialized. We store the
                //    current videoId in the tag.
                thumbnail.setTag(entry.getVideoid());
            } else {
                // 3) The view is already created and already initialized. Simply set the right videoId
                //    on the loader.
                thumbnail.setImageResource(R.drawable.loading_thumbnail);
                loader.setVideo(entry.getVideoid());
            }
        }
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(entry.getTitle());
        return  view;
    }

    private final class ThumbnailListener implements
            YouTubeThumbnailView.OnInitializedListener,
            YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        @Override
        public void onInitializationSuccess(
                YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
            loader.setOnThumbnailLoadedListener(this);
            m_thumbnailViewToLoaderMap.put(view, loader);
            view.setImageResource(R.drawable.loading_thumbnail);
            String videoId = (String) view.getTag();
            loader.setVideo(videoId);
        }

        @Override
        public void onInitializationFailure(
                YouTubeThumbnailView view, YouTubeInitializationResult loader) {
            view.setImageResource(R.drawable.no_thumbnail);
        }

        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView view, String videoId) {
        }

        @Override
        public void onThumbnailError(YouTubeThumbnailView view, YouTubeThumbnailLoader.ErrorReason errorReason) {
            view.setImageResource(R.drawable.no_thumbnail);
        }
    }
}
