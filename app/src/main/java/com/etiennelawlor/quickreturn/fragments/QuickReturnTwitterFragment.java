package com.etiennelawlor.quickreturn.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.interfaces.QuickReturnInterface;
import com.etiennelawlor.quickreturn.utils.QuickReturnUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnTwitterFragment extends ListFragment {


    // region Member Variables
    private String[] mValues;
    private QuickReturnInterface mCoordinator;
    private int mMinHeaderTranslation;
    private int mMinFooterTranslation;
    private int mHeaderHeight;
    private View mPlaceHolderView;
    private int mPrevScrollY = 0;
    private int mHeaderDiffTotal = 0;
    private int mFooterDiffTotal = 0;
    private TranslateAnimation mFooterAnim;
    private TranslateAnimation mHeaderAnim;

    @InjectView(android.R.id.list) ListView mListView;
    @InjectView(R.id.quick_return_footer_ll) LinearLayout mQuickReturnFooterLinearLayout;
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
                mHeaderDiffTotal = Math.max(mHeaderDiffTotal +diff, mMinHeaderTranslation);
                mFooterDiffTotal = Math.max(mFooterDiffTotal +diff, mMinFooterTranslation);
            } else { // scrolling up
                mHeaderDiffTotal = Math.min(Math.max(mHeaderDiffTotal +diff, mMinHeaderTranslation), 0);
                mFooterDiffTotal = Math.min(Math.max(mFooterDiffTotal +diff, mMinFooterTranslation), 0);
            }

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                mHeaderAnim = new TranslateAnimation(0, 0, mHeaderDiffTotal,
                        mHeaderDiffTotal);
                mHeaderAnim.setFillAfter(true);
                mHeaderAnim.setDuration(0);
                mCoordinator.getTabs().startAnimation(mHeaderAnim);

                mFooterAnim = new TranslateAnimation(0, 0, -mFooterDiffTotal, -mFooterDiffTotal);
                mFooterAnim.setFillAfter(true);
                mFooterAnim.setDuration(0);
                mQuickReturnFooterLinearLayout.startAnimation(mFooterAnim);
            } else {
                mCoordinator.getTabs().setTranslationY(mHeaderDiffTotal);
                mQuickReturnFooterLinearLayout.setTranslationY(-mFooterDiffTotal);
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
        int indicatorHeight =  QuickReturnUtils.dp2px(getActivity(), 5);
        mMinHeaderTranslation = -mHeaderHeight + QuickReturnUtils.getActionBarHeight(getActivity()) + indicatorHeight;
        mMinFooterTranslation = -mHeaderHeight + QuickReturnUtils.getActionBarHeight(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_return_twitter, container, false);
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

        mPlaceHolderView = getActivity().getLayoutInflater().inflate(R.layout.view_header_placeholder, mListView, false);
        mListView.addHeaderView(mPlaceHolderView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    // endregion
}
