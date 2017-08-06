package io.vilo.viloapp.home;

import android.support.v4.app.Fragment;

/**
 * Created by raghavendra on 05/08/17.
 */

public class SettingsFragment extends Fragment {


    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static SettingsFragment create(int pageNumber) {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    public SettingsFragment() {

    }
}
