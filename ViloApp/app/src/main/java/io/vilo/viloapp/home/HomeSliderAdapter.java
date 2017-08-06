package io.vilo.viloapp.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by raghavendra on 14/06/17.
 */


public class HomeSliderAdapter extends FragmentStatePagerAdapter {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 3;
    public HomeSliderAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position){
            case 0 : fragment = EngineFeedFragment.create(position);
                break;
            case 1 : fragment = SocialFeedFragment.create(position);
                break;
            case 2 : fragment = SettingsFragment.create(position);
                break;


        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String title = "";
        switch (position){
            case 0 : title = "Videos We Love";
                break;
            case 1 : title = "Videos Friends Love";
                break;
            case 2 : title = "Settings";
                break;


        }
        return title;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
