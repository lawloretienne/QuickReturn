package com.etiennelawlor.quickreturn.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListFragment;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.interfaces.QuickReturnInterface;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnTwitterFragment extends ListFragment {

    // region Constants
    // endregion

    // region Member Variables
    private ListView mListView;
    private LinearLayout mQuickReturnFooterLinearLayout;
    private String[] mValues;
    private QuickReturnInterface mCoordinator;
    private int mActionBarHeight;
    private int mMinHeaderTranslation;
    private int mHeaderHeight;
    private View mPlaceHolderView;
    private int mPrevScrollY = 0;
    private int mDiffTotal = 0;
    private TranslateAnimation mFooterAnim;
    private TranslateAnimation mHeaderAnim;

    private TypedValue mTypedValue = new TypedValue();
    // endregion

    //region Listeners
    private AbsListView.OnScrollListener mListViewOnScrollListener = new AbsListView.OnScrollListener() {
        @SuppressLint("NewApi")
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
        int visibleItemCount, int totalItemCount) {

            int scrollY = getScrollY();
            int diff = mPrevScrollY - scrollY;

            if(diff <=0){ // scrolling down
                mDiffTotal = Math.max(mDiffTotal+diff, mMinHeaderTranslation);
            } else { // scrolling up
                mDiffTotal = Math.min(Math.max(mDiffTotal+diff, mMinHeaderTranslation), 0);
            }

            /** this can be used if the build is below honeycomb **/
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                mFooterAnim = new TranslateAnimation(0, 0, -mDiffTotal, -mDiffTotal);
                mFooterAnim.setFillAfter(true);
                mFooterAnim.setDuration(0);
                mQuickReturnFooterLinearLayout.startAnimation(mFooterAnim);

                mHeaderAnim = new TranslateAnimation(0, 0, mDiffTotal,
                        mDiffTotal);
                mHeaderAnim.setFillAfter(true);
                mHeaderAnim.setDuration(0);
                mCoordinator.getTabs().startAnimation(mHeaderAnim);
            } else {
                mQuickReturnFooterLinearLayout.setTranslationY(-mDiffTotal);
                mCoordinator.getTabs().setTranslationY(mDiffTotal);
            }

            mPrevScrollY = scrollY;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
    };

    //endregion

    // region Constructors
    public static QuickReturnTwitterFragment newInstance(Bundle extras) {
        QuickReturnTwitterFragment fragment = new QuickReturnTwitterFragment();
        fragment.setArguments(extras);
        return fragment;
    }

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mHeaderHeight + getActionBarHeight();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quick_return_twitter, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindUIElements(view);

        mValues = getResources().getStringArray(R.array.countries2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item, R.id.item_tv, mValues);

        mListView.setAdapter(adapter);

        mListView.setOnScrollListener(mListViewOnScrollListener);

//        mPlaceHolderView = getActivity().getLayoutInflater().inflate(R.layout.view_header_placeholder, mListView, false);
//        mListView.addHeaderView(mPlaceHolderView);
    }

    // endregion

    // region Helper Methods
    private void bindUIElements(View view){
        mListView = (ListView) view.findViewById(android.R.id.list);
        mQuickReturnFooterLinearLayout = (LinearLayout) view.findViewById(R.id.quick_return_footer_ll);
    }

    public int getScrollY() {
        View c = mListView.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = mListView.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mCoordinator.getTabs().getHeight();
//            headerHeight = mPlaceHolderView.getHeight();
        }

        int scrollY = -top + firstVisiblePosition * c.getHeight() + headerHeight;
        return scrollY;
    }

    public int getActionBarHeight() {
        if (mActionBarHeight != 0) {
            return mActionBarHeight;
        }

        getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, mTypedValue, true);
        mActionBarHeight = TypedValue.complexToDimensionPixelSize(mTypedValue.data, getResources().getDisplayMetrics());
        return mActionBarHeight;
    }
    // endregion
}
