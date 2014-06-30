package com.etiennelawlor.quickreturn.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.android.vending.billing.IInAppBillingService;
import com.astuetz.PagerSlidingTabStrip;
import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.fragments.QuickReturnFacebookFragment;
import com.etiennelawlor.quickreturn.fragments.QuickReturnFooterListFragment;
import com.etiennelawlor.quickreturn.fragments.QuickReturnFragment;
import com.etiennelawlor.quickreturn.fragments.QuickReturnHeaderListFragment;
import com.etiennelawlor.quickreturn.fragments.QuickReturnTwitterFragment;
import com.etiennelawlor.quickreturn.interfaces.QuickReturnInterface;
import com.etiennelawlor.quickreturn.utils.QuickReturnUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class QuickReturnListViewActivity extends QuickReturnBaseActivity implements ActionBar.TabListener, QuickReturnInterface {

    // region Member Variables
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private PagerSlidingTabStrip mTabs;
    private ViewPager mViewPager;
    // endregion

    // region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_return_listview);

        final ActionBar actionBar = getActionBar();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        mViewPager.setPageMargin(pageMargin);

        mTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mTabs.setAllCaps(false);
        mTabs.setShouldExpand(true);
        mTabs.setTextSize(QuickReturnUtils.dp2px(this, 16));
//        mTabs.setTabBackground(R.color.indigo);
        mTabs.setIndicatorColorResource(R.color.steel_blue);
//        mTabs.setTextColor(getResources().getColor(android.R.color.white));

        mTabs.setViewPager(mViewPager);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
//        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                int navigationMode = actionBar.getNavigationMode();
//                if(navigationMode == ActionBar.NAVIGATION_MODE_TABS)
//                    actionBar.setSelectedNavigationItem(position);
//            }
//
////            @Override
////            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
////                int alphaCurrent = (int) (255 - (128*Math.abs(positionOffset)));
////                int alphaNext = (int) (128 + (128*Math.abs(positionOffset)));
////                if (positionOffset != 0) {
////                    switch(position) {
////                        case 0:
////                            actionBar.getTabAt(0).setsetTheAlpha(alphaCurrent);
////                            tab0.setTheAlpha(alphaCurrent);
////                            tab1.setTheAlpha(alphaNext);
////                            break;
////                        case 1:
////                            tab1.setTheAlpha(alphaCurrent);
////                            tab2.setTheAlpha(alphaNext);
////                            break;
////                        case 2:
////                            tab2.setTheAlpha(alphaCurrent);
////                            tab3.setTheAlpha(alphaNext);
////                            break;
////                    }
////                }
////            }
//        });

//        // For each of the sections in the app, add a tab to the action bar.
//        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
//            // Create a tab with text corresponding to the page title defined by
//            // the adapter. Also specify this Activity object, which implements
//            // the TabListener interface, as the callback (listener) for when
//            // this tab is selected.
//            actionBar.addTab(
//                    actionBar.newTab()
//                            .setText(mSectionsPagerAdapter.getPageTitle(i))
//                            .setTabListener(this));
//        }
    }
    // endregion

    // region ActionBar.TabListener Methods
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
    // endregion

    // region QuickReturnInterface Methods
    @Override
    public PagerSlidingTabStrip getTabs() {
        return mTabs;
    }

    @Override
    public ViewPager getViewPager() {
        return mViewPager;
    }
    // endregion


    // region Inner Classes

    /**
     * A {@link android.support.v13.app.FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return QuickReturnHeaderListFragment.newInstance();
                case 1:
                    return QuickReturnFooterListFragment.newInstance();
                default:
                    return QuickReturnHeaderListFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.header);
                case 1:
                    return getString(R.string.footer);
            }
            return null;
        }
    }

    // endregion

}
