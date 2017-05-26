package com.example.raghavendra.vidnet;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by raghavendra on 20/05/17.
 */

public class VideoListFragment extends ListFragment {

    private VideoListAdapter m_videoAdapter;
    private List<VideoEntry> m_videoList;
    private View m_videoView;
    private VideoPlayerFragment m_videoPlayerFragment;
    private LayoutInflater m_inflator;
//    static {
//        List<VideoEntry> list = new ArrayList<VideoEntry>();
//        list.add(new VideoEntry("YouTube Collection", "Y_UmWdcTrrc"));
//        list.add(new VideoEntry("GMail Tap", "1KhZKNZO8mQ"));
//        list.add(new VideoEntry("Chrome Multitask", "UiLSiqyDf4Y"));
//        list.add(new VideoEntry("Google Fiber", "re0VRK6ouwI"));
//        list.add(new VideoEntry("Autocompleter", "blB_X38YSxQ"));
//        list.add(new VideoEntry("GMail Motion", "Bu927_ul_X0"));
//        list.add(new VideoEntry("Translate for Animals", "3I24bSteJpw"));
//        VIDEO_LIST = Collections.unmodifiableList(list);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        VideoListRequest request = new VideoListRequest();

        m_videoList = new ArrayList<VideoEntry>();
        VideoEntry videoEntry = new VideoEntry();
        videoEntry.setTitle("YouTube Collection");
        videoEntry.setVideoid("Y_UmWdcTrrc");
        m_videoList.add(videoEntry);

        m_videoAdapter = new VideoListAdapter(getActivity(),m_videoList);


        VidNetApplication.getInstance().getApiHandler().getVideoList("",
                new Callback<List<VideoEntry>>() {
                    @Override
                    public void success(List<VideoEntry> videos, Response response) {

                        m_videoList = videos;
                        m_videoAdapter.setVideos(videos);
                    }

                    @Override
                    public void failure(RetrofitError error) {


                    }
                });



        m_videoPlayerFragment = new VideoPlayerFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        m_videoView = getActivity().findViewById(R.id.video_box);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        setListAdapter(m_videoAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

//        String videoId = m_videoList.get(position).getVideoid();
//
//        this.getFragmentManager().beginTransaction()
//                .replace(R.id.videolist_container, m_videoPlayerFragment, null)
//                .addToBackStack(null)
//                .commit();
//        m_videoPlayerFragment.setVideoId(videoId);


    }
}
