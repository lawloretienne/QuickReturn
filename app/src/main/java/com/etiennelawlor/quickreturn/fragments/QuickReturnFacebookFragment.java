package com.etiennelawlor.quickreturn.fragments;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnFacebookFragment extends ListFragment {

    // region Constants
    // endregion

    // region Member Variables
    private ListView mListView;
    private TextView mQuickReturnHeaderTextView;
    private LinearLayout mQuickReturnFooterLinearLayout;
    private String[] mValues;
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
                mQuickReturnHeaderTextView.startAnimation(mHeaderAnim);
            } else {
                mQuickReturnFooterLinearLayout.setTranslationY(-mDiffTotal);
                mQuickReturnHeaderTextView.setTranslationY(mDiffTotal);
            }

            mPrevScrollY = scrollY;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
    };
    //endregion

    // region Constructors
    public static QuickReturnFacebookFragment newInstance() {
        QuickReturnFacebookFragment fragment = new QuickReturnFacebookFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public QuickReturnFacebookFragment() {
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -(mHeaderHeight*2) + getActionBarHeight();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quick_return_facebook, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindUIElements(view);

        mValues = getResources().getStringArray(R.array.countries);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item, R.id.item_tv, mValues);

        mListView.setAdapter(adapter);

        mListView.setOnScrollListener(mListViewOnScrollListener);
    }

    // endregion

    // region Helper Methods
    private void bindUIElements(View view){
        mListView = (ListView) view.findViewById(android.R.id.list);
        mQuickReturnFooterLinearLayout = (LinearLayout) view.findViewById(R.id.quick_return_footer_ll);
        mQuickReturnHeaderTextView = (TextView) view.findViewById(R.id.quick_return_header_tv);
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
            headerHeight = mQuickReturnHeaderTextView.getHeight();
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
