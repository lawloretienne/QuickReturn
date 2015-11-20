package com.etiennelawlor.quickreturn.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.fragments.QuickReturnFragment;
import com.etiennelawlor.quickreturn.fragments.SpeedyQuickReturnFragment;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;

import butterknife.Bind;
import butterknife.ButterKnife;


public class QuickReturnScrollViewActivity extends QuickReturnBaseActivity {

    // region Member Variables
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Bind(R.id.pager)
    ViewPager mViewPager;
    // endregion

    // region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_return_scrollview);
        ButterKnife.bind(this);

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
            switch (position) {
                case 0:
                    bundle.putString("quick_return_view_type",
                            QuickReturnViewType.HEADER.name());
                    return QuickReturnFragment.newInstance(bundle);
                case 1:
                    bundle.putString("quick_return_view_type",
                            QuickReturnViewType.HEADER.name());
                    return SpeedyQuickReturnFragment.newInstance(bundle);
                case 2:
                    bundle.putString("quick_return_view_type",
                            QuickReturnViewType.FOOTER.name());
                    return QuickReturnFragment.newInstance(bundle);
                case 3:
                    bundle.putString("quick_return_view_type",
                            QuickReturnViewType.FOOTER.name());
                    return SpeedyQuickReturnFragment.newInstance(bundle);
                case 4:
                    bundle.putString("quick_return_view_type",
                            QuickReturnViewType.BOTH.name());
                    return QuickReturnFragment.newInstance(bundle);
                case 5:
                    bundle.putString("quick_return_view_type",
                            QuickReturnViewType.BOTH.name());
                    return SpeedyQuickReturnFragment.newInstance(bundle);
                default:
                    bundle.putString("quick_return_view_type",
                            QuickReturnViewType.HEADER.name());
                    return SpeedyQuickReturnFragment.newInstance(bundle);
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
                    return "QRHeader";
                case 1:
                    return "SpeedyQRHeader";
                case 2:
                    return "QRFooter";
                case 3:
                    return "SpeedyQRFooter";
                case 4:
                    return "QRBoth";
                case 5:
                    return "SpeedyQRBoth";

            }
            return null;
        }
    }

    // endregion
}
