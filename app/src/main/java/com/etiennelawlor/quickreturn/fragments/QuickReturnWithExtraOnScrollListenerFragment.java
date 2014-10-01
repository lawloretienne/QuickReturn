package com.etiennelawlor.quickreturn.fragments;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

/**
 * Quick return effect with extra scroll-to-bottom {@link android.widget.AbsListView.OnScrollListener}
 *
 * @author longkai
 */
public class QuickReturnWithExtraOnScrollListenerFragment extends QuickReturnHeaderListFragment3 implements AbsListView.OnScrollListener {
    public static final String TAG = QuickReturnWithExtraOnScrollListenerFragment.class.getSimpleName();

    public static QuickReturnWithExtraOnScrollListenerFragment newInstance() {
        QuickReturnWithExtraOnScrollListenerFragment fragment = new QuickReturnWithExtraOnScrollListenerFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // now append our scroll to bottom load-more listener with the quick return effect
        mQuickReturnListViewOnScrollListener.registerExtraOnScrollListener(this);
    }

    @Override public void onScrollStateChanged(AbsListView view, int scrollState) {
        // no-op
    }

    @Override public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount == totalItemCount && getListView().getFooterViewsCount() == 0) {
            ProgressBar progressBar = new ProgressBar(getActivity());
            progressBar.setLayoutParams(new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            progressBar.setPadding(8, 8, 8, 8);
            getListView().addFooterView(progressBar);
        }
    }
}
