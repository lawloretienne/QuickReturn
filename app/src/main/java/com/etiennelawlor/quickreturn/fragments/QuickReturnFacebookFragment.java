package com.etiennelawlor.quickreturn.fragments;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Build;
import android.os.Bundle;
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
import com.etiennelawlor.quickreturn.utils.QuickReturnUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnFacebookFragment extends ListFragment {

    // region Member Variables
    private String[] mValues;
    private int mMinHeaderTranslation;
    private int mHeaderHeight;
    private int mPrevScrollY = 0;
    private int mDiffTotal = 0;
    private TranslateAnimation mFooterAnim;
    private TranslateAnimation mHeaderAnim;

    @InjectView(android.R.id.list) ListView mListView;
    @InjectView(R.id.quick_return_footer_ll) LinearLayout mQuickReturnFooterLinearLayout;
    @InjectView(R.id.quick_return_header_tv) TextView mQuickReturnHeaderTextView;
    // endregion

    //region Listeners
    private AbsListView.OnScrollListener mListViewOnScrollListener = new AbsListView.OnScrollListener() {
        @SuppressLint("NewApi")
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {

            int scrollY = QuickReturnUtils.getScrollY(mListView);
            int diff = mPrevScrollY - scrollY;

            if(diff <=0){ // scrolling down
                mDiffTotal = Math.max(mDiffTotal+diff, mMinHeaderTranslation);
            } else { // scrolling up
                mDiffTotal = Math.min(Math.max(mDiffTotal+diff, mMinHeaderTranslation), 0);
            }

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

        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height3);
        mMinHeaderTranslation = -(mHeaderHeight*2) + QuickReturnUtils.getActionBarHeight(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_return_facebook, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mValues = getResources().getStringArray(R.array.countries);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item, R.id.item_tv, mValues);

        mListView.setAdapter(adapter);

        mListView.setOnScrollListener(mListViewOnScrollListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    // endregion
}
