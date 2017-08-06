package io.vilo.viloapp.home;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import io.vilo.viloapp.R;

/**
 * Created by raghavendra on 14/06/17.
 */

public class HomeSliderActivity extends FragmentActivity {


    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    private PagerTitleStrip mPagerTitle;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.viewPager);
        mPagerAdapter = new HomeSliderAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mPagerTitle = (PagerTitleStrip)findViewById(R.id.viewPagerTitleStrip);

        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/Abel-Regular.ttf");
        for (int counter = 0 ; counter<mPagerTitle.getChildCount(); counter++) {

            if (mPagerTitle.getChildAt(counter) instanceof TextView) {
                ((TextView)mPagerTitle.getChildAt(counter)).setTypeface(font);
                ((TextView)mPagerTitle.getChildAt(counter)).setTextSize(20);
                ((TextView)mPagerTitle.getChildAt(counter)).setTypeface(null, Typeface.BOLD);
            }

        }


//        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                // When changing pages, reset the action bar actions since they are dependent
//                // on which page is currently active. An alternative approach is to have each
//                // fragment expose actions itself (rather than the activity exposing actions),
//                // but for simplicity, the activity provides the actions in this sample.
//                invalidateOptionsMenu();
//            }
//        });
        mPager.setPageTransformer(true, new DepthPageTransformer());

        int margin = getResources().getDisplayMetrics().widthPixels / 15;
//        mPager.setPageMargin(margin);
//        mPager.setPageMarginDrawable(R.drawable.shadow);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.activity_screen_slide, menu);
//
//        menu.findItem(R.id.action_previous).setEnabled(mPager.getCurrentItem() > 0);
//
//        // Add either a "next" or "finish" button to the action bar, depending on which page
//        // is currently selected.
//        MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
//                (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1)
//                        ? R.string.action_finish
//                        : R.string.action_next);
//        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                // Navigate "up" the demo structure to the launchpad activity.
//                // See http://developer.android.com/design/patterns/navigation.html for more.
//                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
//                return true;
//
//            case R.id.action_previous:
//                // Go to the previous step in the wizard. If there is no previous step,
//                // setCurrentItem will do nothing.
//                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
//                return true;
//
//            case R.id.action_next:
//                // Advance to the next step in the wizard. If there is no next step, setCurrentItem
//                // will do nothing.
//                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
//                return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.90f;
        private static final float MIN_ALPHA = 1.0f;
        private static final float MAX_ALPHA = 1.5f;
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
//                view.setTranslationX(0);
//                view.setScaleX(1);
//                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1.5f - position);
//                view.setElevation(5.0f);

                // Counteract the default slide transition
//                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                float newPageWidth = pageWidth/2.0f - (pageWidth * scaleFactor)/2.0f;
                view.setTranslationX((pageWidth * -position) + newPageWidth);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
