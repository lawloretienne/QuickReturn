package com.etiennelawlor.quickreturn.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.fragments.QuickReturnFooterRecyclerViewFragment;
import com.etiennelawlor.quickreturn.fragments.QuickReturnHeaderRecyclerViewFragment;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnAnimationType;

import butterknife.Bind;
import butterknife.ButterKnife;


public class QuickReturnRecyclerViewActivity extends QuickReturnBaseActivity {

    // region Member Variables
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static String sLayoutManagerType;

    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Bind(R.id.pager)
    ViewPager mViewPager;
    // endregion

    // region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_return_recyclerview);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                sLayoutManagerType = bundle.getString("layout_manager");
            }
        }

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }
    // endregion

    // region Inner Classes

    /**
     * A {@link android.support.v13.app.FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        // region Constructors
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        // endregion

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString("layout_manager", sLayoutManagerType);

            switch (position) {
                case 0:
                    bundle.putString("quick_return_animation_type",
                            QuickReturnAnimationType.TRANSLATION_SIMPLE.name());
                    return QuickReturnHeaderRecyclerViewFragment.newInstance(bundle);
                case 1:
                    bundle.putString("quick_return_animation_type",
                            QuickReturnAnimationType.TRANSLATION_SNAP.name());
                    return QuickReturnHeaderRecyclerViewFragment.newInstance(bundle);
                case 2:
                    bundle.putString("quick_return_animation_type",
                            QuickReturnAnimationType.TRANSLATION_ANTICIPATE_OVERSHOOT.name());
                    return QuickReturnHeaderRecyclerViewFragment.newInstance(bundle);
                case 3:
                    bundle.putString("quick_return_animation_type",
                            QuickReturnAnimationType.TRANSLATION_SIMPLE.name());
                    return QuickReturnFooterRecyclerViewFragment.newInstance(bundle);
                case 4:
                    bundle.putString("quick_return_animation_type",
                            QuickReturnAnimationType.TRANSLATION_SNAP.name());
                    return QuickReturnFooterRecyclerViewFragment.newInstance(bundle);
                case 5:
                    bundle.putString("quick_return_animation_type",
                            QuickReturnAnimationType.TRANSLATION_ANTICIPATE_OVERSHOOT.name());
                    return QuickReturnFooterRecyclerViewFragment.newInstance(bundle);
                default:
                    bundle.putString("quick_return_animation_type",
                            QuickReturnAnimationType.TRANSLATION_SIMPLE.name());
                    return QuickReturnHeaderRecyclerViewFragment.newInstance(bundle);
            }
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "QRHeaderSimple";
                case 1:
                    return "QRHeaderSnap";
                case 2:
                    return "QRHeaderAnticipateOvershoot";
                case 3:
                    return "QRFooterSimple";
                case 4:
                    return "QRFooterSnap";
                case 5:
                    return "QRFooterAnticipateOvershoot";
            }
            return null;
        }

    }

    // endregion

}
