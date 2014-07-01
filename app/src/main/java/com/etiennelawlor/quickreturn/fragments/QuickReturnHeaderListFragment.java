package com.etiennelawlor.quickreturn.fragments;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.views.QuickReturnListView;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnHeaderListFragment extends ListFragment {

    // region Constants
    private static final int STATE_ONSCREEN = 0;
    private static final int STATE_OFFSCREEN = 1;
    private static final int STATE_RETURNING = 2;
    // endregion

    // region Member Variables
    private QuickReturnListView mQuickReturnListView;
    private TextView mQuickReturnTextView;
    private boolean mQuickReturnViewVisible = true;
    private View mPlaceHolder;
    private View mHeader;
    private String[] mValues;
    private int mQuickReturnHeight;
    private int mCachedVerticalScrollRange;
    private int mState = STATE_ONSCREEN;
    private int mScrollY;
    private int mMinRawY = 0;
    private TranslateAnimation mAnim;
    // endregion

    //region Listeners
    private AbsListView.OnScrollListener mQuickReturnListViewOnScrollListener = new AbsListView.OnScrollListener() {
        @SuppressLint("NewApi")
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
        int visibleItemCount, int totalItemCount) {

            mScrollY = 0;
            int translationY = 0;

            if (mQuickReturnListView.scrollYIsComputed()) {
                mScrollY = mQuickReturnListView.getComputedScrollY();
            }

            int rawY = mPlaceHolder.getTop()
                    - Math.min(
                    mCachedVerticalScrollRange
                            - mQuickReturnListView.getHeight(), mScrollY);

            switch (mState) {
                case STATE_OFFSCREEN:
                    if (rawY <= mMinRawY) {
                        mMinRawY = rawY;
                    } else {
                        mState = STATE_RETURNING;
                    }
                    translationY = rawY;
                    break;

                case STATE_ONSCREEN:
                    if (rawY < -mQuickReturnHeight) {
                        mState = STATE_OFFSCREEN;
                        mMinRawY = rawY;
                    }
                    translationY = rawY;
                    break;

                case STATE_RETURNING:
                    translationY = (rawY - mMinRawY) - mQuickReturnHeight;
                    if (translationY > 0) {
                        translationY = 0;
                        mMinRawY = rawY - mQuickReturnHeight;
                    }

                    if (rawY > 0) {
                        mState = STATE_ONSCREEN;
                        translationY = rawY;
                    }

                    if (translationY < -mQuickReturnHeight) {
                        mState = STATE_OFFSCREEN;
                        mMinRawY = rawY;
                    }
                    break;
            }

            /** this can be used if the build is below honeycomb **/
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                mAnim = new TranslateAnimation(0, 0, translationY,
                        translationY);
                mAnim.setFillAfter(true);
                mAnim.setDuration(0);
                mQuickReturnTextView.startAnimation(mAnim);
            } else {
                mQuickReturnTextView.setTranslationY(translationY);
            }


        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
    };

    private ViewTreeObserver.OnGlobalLayoutListener mQuickReturnListViewOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            mQuickReturnHeight = mQuickReturnTextView.getHeight();
            mQuickReturnListView.computeScrollY();
            mCachedVerticalScrollRange = mQuickReturnListView.getListHeight();
        }
    };
    //endregion

    // region Constructors
    public static QuickReturnHeaderListFragment newInstance() {
        QuickReturnHeaderListFragment fragment = new QuickReturnHeaderListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public QuickReturnHeaderListFragment() {
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_quick_return_header, container, false);
        mHeader = inflater.inflate(R.layout.header, null);
        mPlaceHolder = mHeader.findViewById(R.id.placeholder);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindUIElements(view);

        mValues = getResources().getStringArray(R.array.countries);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item, R.id.item_tv, mValues);

        mQuickReturnListView.setAdapter(adapter);

        mQuickReturnListView.getViewTreeObserver().addOnGlobalLayoutListener(mQuickReturnListViewOnGlobalLayoutListener);

        mQuickReturnListView.setOnScrollListener(mQuickReturnListViewOnScrollListener);
    }

    // endregion

    // region Helper Methods
    private void bindUIElements(View view){
        mQuickReturnListView = (QuickReturnListView) view.findViewById(android.R.id.list);
        mQuickReturnTextView = (TextView) view.findViewById(R.id.quick_return_tv);
    }
    // endregion
}
