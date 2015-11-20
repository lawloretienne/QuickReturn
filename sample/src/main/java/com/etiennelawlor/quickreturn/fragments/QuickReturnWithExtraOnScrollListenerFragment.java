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
public class QuickReturnWithExtraOnScrollListenerFragment extends QuickReturnHeaderListViewFragment implements AbsListView.OnScrollListener {

    // region Constants 
    public static final String TAG = QuickReturnWithExtraOnScrollListenerFragment.class.getSimpleName();
    // endregion

    // region Constructors
    public QuickReturnWithExtraOnScrollListenerFragment() {
    }
    // endregion

    // region Factory Methods
    public static QuickReturnWithExtraOnScrollListenerFragment newInstance(Bundle extras) {
        QuickReturnWithExtraOnScrollListenerFragment fragment = new QuickReturnWithExtraOnScrollListenerFragment();
        fragment.setRetainInstance(true);
        fragment.setArguments(extras);
        return fragment;
    }

    public static QuickReturnWithExtraOnScrollListenerFragment newInstance() {
        QuickReturnWithExtraOnScrollListenerFragment fragment = new QuickReturnWithExtraOnScrollListenerFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // now append our scroll to bottom load-more listener with the quick return effect
        mScrollListener.registerExtraOnScrollListener(this);
    }
    // endregion

    // region AbsListView.OnScrollListener Methods
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // no-op
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
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
    // endregion
}
