package io.vilo.viloapp.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import io.vilo.viloapp.DeveloperKey;
import io.vilo.viloapp.R;
import io.vilo.viloapp.model.VideoModel;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by raghavendra on 26/05/17.
 */

public class VideoViewHolder extends RecyclerView.ViewHolder{

    @Bind(R.id.thumbnail)
    YouTubeThumbnailView thumbnailView;

    @Bind(R.id.textView)
    TextView textView;

    private final Map<YouTubeThumbnailView, YouTubeThumbnailLoader> m_thumbnailViewToLoaderMap;
    private final ThumbnailListener m_thumbnailListener;

    private View.OnClickListener onClickListener;

    public VideoViewHolder(View view, View.OnClickListener listener) {
        super(view);
        ButterKnife.bind(this, view);
        onClickListener = listener;

        m_thumbnailViewToLoaderMap = new HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>();
        m_thumbnailListener = new ThumbnailListener();
    }

    public void bind(VideoModel videoModel) {

        //if(view == null){
            thumbnailView.setTag(R.id.key_asset, videoModel);
            thumbnailView.setTag(videoModel.getVideoid());
            thumbnailView.setOnClickListener(onClickListener);
            thumbnailView.initialize(DeveloperKey.DEVELOPER_KEY, m_thumbnailListener);

//        }else {
//            YouTubeThumbnailView thumbnail = (YouTubeThumbnailView) view.findViewById(R.id.thumbnail);
//            YouTubeThumbnailLoader loader = m_thumbnailViewToLoaderMap.get(thumbnail);
//            if (loader == null) {
//                // 2) The view is already created, and is currently being initialized. We store the
//                //    current videoId in the tag.
//                thumbnail.setTag(entry.getVideoid());
//            } else {
//                // 3) The view is already created and already initialized. Simply set the right videoId
//                //    on the loader.
//                thumbnail.setImageResource(R.drawable.loading_thumbnail);
//                loader.setVideo(entry.getVideoid());
//            }
//        }

        textView.setText(videoModel.getTitle());

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

