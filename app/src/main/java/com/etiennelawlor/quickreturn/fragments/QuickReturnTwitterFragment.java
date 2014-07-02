package com.etiennelawlor.quickreturn.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListFragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.interfaces.QuickReturnInterface;
import com.etiennelawlor.quickreturn.views.QuickReturnListView;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnTwitterFragment extends ListFragment {

    // region Constants
    private static final int STATE_ONSCREEN = 0;
    private static final int STATE_OFFSCREEN = 1;
    private static final int STATE_RETURNING = 2;
    // endregion

    // region Member Variables
    private QuickReturnListView mQuickReturnListView;
    private LinearLayout mQuickReturnFooterLinearLayout;
    private boolean mQuickReturnViewVisible = true;
    private View mPlaceHolder;
    private View mHeader;
    private String[] mValues;
    private int mQuickReturnHeight;
    private int mCachedVerticalScrollRange;
    private int mFooterState = STATE_ONSCREEN;
    private int mHeaderState = STATE_ONSCREEN;
    private int mScrollY;
    private int mFooterMinRawY = 0;
    private int mHeaderMinRawY = 0;
    private TranslateAnimation mFooterAnim;
    private TranslateAnimation mHeaderAnim;
    private QuickReturnInterface mCoordinator;
    // endregion

    //region Listeners
    private AbsListView.OnScrollListener mQuickReturnListViewOnScrollListener = new AbsListView.OnScrollListener() {
        @SuppressLint("NewApi")
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
        int visibleItemCount, int totalItemCount) {

            Log.d("QuickReturnTwitterFragment", "onScroll()");

            mScrollY = 0;
            int footerTranslationY = 0;
            int headerTranslationY = 0;

            if (mQuickReturnListView.scrollYIsComputed()) {
                mScrollY = mQuickReturnListView.getComputedScrollY();
            }

            int footerRawY = mScrollY;
            int headerRawY = mPlaceHolder.getTop()
                    - Math.min(
                    mCachedVerticalScrollRange
                            - mQuickReturnListView.getHeight(), mScrollY);

            switch (mFooterState) {
                case STATE_OFFSCREEN:
                    if (footerRawY >= mFooterMinRawY) {
                        mFooterMinRawY = footerRawY;
                    } else {
                        mFooterState = STATE_RETURNING;
                    }
                    footerTranslationY = footerRawY;
                    break;
                case STATE_ONSCREEN:
                    if (footerRawY > mQuickReturnHeight) {
                        mFooterState = STATE_OFFSCREEN;
                        mFooterMinRawY = footerRawY;
                    }
                    footerTranslationY = footerRawY;
                    break;
                case STATE_RETURNING:
                    footerTranslationY = (footerRawY - mFooterMinRawY) + mQuickReturnHeight;

//                    System.out.println(footerTranslationY);
                    if (footerTranslationY < 0) {
                        footerTranslationY = 0;
                        mFooterMinRawY = footerRawY + mQuickReturnHeight;
                    }

                    if (footerRawY == 0) {
                        mFooterState = STATE_ONSCREEN;
                        footerTranslationY = 0;
                    }

                    if (footerTranslationY > mQuickReturnHeight) {
                        mFooterState = STATE_OFFSCREEN;
                        mFooterMinRawY = footerRawY;
                    }

                    break;
            }

            switch (mHeaderState) {
                case STATE_OFFSCREEN:
                    if (headerRawY <= mHeaderMinRawY) {
                        mHeaderMinRawY = headerRawY;
                    } else {
                        mHeaderState = STATE_RETURNING;
                    }
                    headerTranslationY = headerRawY;
                    Log.d("QuickReturnTwitterFragment", "121 : onScroll() : headerTranslationY - "+headerTranslationY);

                    break;

                case STATE_ONSCREEN:
                    if (headerRawY < -mQuickReturnHeight) {
                        mHeaderState = STATE_OFFSCREEN;
                        mHeaderMinRawY = headerRawY;
                    }
                    headerTranslationY = headerRawY;
                    Log.d("QuickReturnTwitterFragment", "130 : onScroll() : headerTranslationY - "+headerTranslationY);

                    break;

                case STATE_RETURNING:
                    headerTranslationY = (headerRawY - mHeaderMinRawY) - mQuickReturnHeight;
                    if (headerTranslationY > 0) {
                        headerTranslationY = 0;
                        Log.d("QuickReturnTwitterFragment", "139 : onScroll() : headerTranslationY - "+headerTranslationY);

                        mHeaderMinRawY = headerRawY - mQuickReturnHeight;
                    }

                    if (headerRawY > 0) {
                        mHeaderState = STATE_ONSCREEN;
                        headerTranslationY = headerRawY;
                        Log.d("QuickReturnTwitterFragment", "147 : onScroll() : headerTranslationY - "+headerTranslationY);

                    }

                    if (headerTranslationY < -mQuickReturnHeight) {
                        mHeaderState = STATE_OFFSCREEN;
                        mHeaderMinRawY = headerRawY;
                    }
                    break;
            }

            /** this can be used if the build is below honeycomb **/
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                mFooterAnim = new TranslateAnimation(0, 0, footerTranslationY,
                        footerTranslationY);
                mFooterAnim.setFillAfter(true);
                mFooterAnim.setDuration(0);
                mQuickReturnFooterLinearLayout.startAnimation(mFooterAnim);

                mHeaderAnim = new TranslateAnimation(0, 0, headerTranslationY,
                        headerTranslationY);
                mHeaderAnim.setFillAfter(true);
                mHeaderAnim.setDuration(0);
                mCoordinator.getTabs().startAnimation(mHeaderAnim);
//                mCoordinator.getViewPager().startAnimation(mHeaderAnim);
            } else {
                mQuickReturnFooterLinearLayout.setTranslationY(footerTranslationY);

                Log.d("QuickReturnTwitterFragment", "175 : onScroll() : headerTranslationY - "+headerTranslationY);

                mCoordinator.getTabs().setTranslationY(headerTranslationY);
//                mCoordinator.getViewPager().setTranslationY(headerTranslationY);

            }

        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
    };

    private ViewTreeObserver.OnGlobalLayoutListener mQuickReturnListViewOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {

            if(mQuickReturnHeight == 0) {
                Log.d("QuickReturnTwitterFragment", "onGlobalLayout() : mQuickReturnHeight - "+mQuickReturnHeight);
                mQuickReturnHeight = mQuickReturnFooterLinearLayout.getHeight();
            }
            mQuickReturnListView.computeScrollY();
//            Log.d(getClass().getSimpleName(), "onGlobalLayout() : mQuickReturnHeight - "+mQuickReturnHeight);


            if(mCachedVerticalScrollRange == 0){
                Log.d("QuickReturnTwitterFragment", "onGlobalLayout() : mCachedVerticalScrollRange - "+mCachedVerticalScrollRange);
                mCachedVerticalScrollRange = mQuickReturnListView.getListHeight();
            }

        }
    };
    //endregion

    // region Constructors
    public static QuickReturnTwitterFragment newInstance() {
        QuickReturnTwitterFragment fragment = new QuickReturnTwitterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public QuickReturnTwitterFragment() {
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof QuickReturnInterface) {
            mCoordinator = (QuickReturnInterface) activity;
        } else
            throw new ClassCastException("Parent container must implement the QuickReturnInterface");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quick_return_twitter, container, false);
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
        mQuickReturnFooterLinearLayout = (LinearLayout) view.findViewById(R.id.quick_return_footer_ll);
    }
    // endregion
}
