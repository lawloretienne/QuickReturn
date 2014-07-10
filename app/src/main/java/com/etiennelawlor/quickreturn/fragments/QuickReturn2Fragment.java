package com.etiennelawlor.quickreturn.fragments;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.activities.QuickReturnScrollViewActivity;
import com.etiennelawlor.quickreturn.views.NotifyingScrollView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturn2Fragment extends Fragment {

    // region Member Variables
    private QuickReturnScrollViewActivity.QuickReturnType mQuickReturnType;
    private boolean mQuickReturnHeaderViewVisible = false;
    private boolean mQuickReturnFooterViewVisible = false;
    private int mHeaderDiffTotal = 0;
    private int mFooterDiffTotal = 0;
    private int mMinHeaderTranslation;
    private int mHeaderHeight;
    private int mMinFooterTranslation;
    private int mFooterHeight;
    private TranslateAnimation mFooterAnim;
    private TranslateAnimation mHeaderAnim;

    @InjectView(R.id.scroll_view) NotifyingScrollView mNotifyingScrollView;
    @InjectView(R.id.quick_return_header_tv) TextView mQuickReturnHeaderTextView;
    @InjectView(R.id.quick_return_footer_tv) TextView mQuickReturnFooterTextView;
    // endregion

    //region Listeners

    private NotifyingScrollView.OnScrollChangedListener mOnScrollChangedListener = new NotifyingScrollView.OnScrollChangedListener() {
        public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {

            int diff = oldt - t;

            switch (mQuickReturnType){
                case HEADER:
                    if(diff <=0){ // scrolling down
                        mHeaderDiffTotal = Math.max(mHeaderDiffTotal+diff, mMinHeaderTranslation);
                    } else { // scrolling up
                        mHeaderDiffTotal = Math.min(Math.max(mHeaderDiffTotal+diff, mMinHeaderTranslation), 0);
                    }

                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {

                        mHeaderAnim = new TranslateAnimation(0, 0, mHeaderDiffTotal,
                                mHeaderDiffTotal);
                        mHeaderAnim.setFillAfter(true);
                        mHeaderAnim.setDuration(0);
                        mQuickReturnHeaderTextView.startAnimation(mHeaderAnim);
                    } else {
                        mQuickReturnHeaderTextView.setTranslationY(mHeaderDiffTotal);
                    }
                    break;
                case FOOTER:
                    if(diff <=0){ // scrolling down
                        mFooterDiffTotal = Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation);
                    } else { // scrolling up
                        mFooterDiffTotal = Math.min(Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation), 0);
                    }

                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                        mFooterAnim = new TranslateAnimation(0, 0, -mFooterDiffTotal,
                                -mFooterDiffTotal);
                        mFooterAnim.setFillAfter(true);
                        mFooterAnim.setDuration(0);
                        mQuickReturnFooterTextView.startAnimation(mFooterAnim);
                    } else {
                        mQuickReturnFooterTextView.setTranslationY(-mFooterDiffTotal);
                    }
                    break;
                case BOTH:
                    if(diff <=0){ // scrolling down
                        mHeaderDiffTotal = Math.max(mHeaderDiffTotal+diff, mMinHeaderTranslation);
                        mFooterDiffTotal = Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation);
                    } else { // scrolling up
                        mHeaderDiffTotal = Math.min(Math.max(mHeaderDiffTotal+diff, mMinHeaderTranslation), 0);
                        mFooterDiffTotal = Math.min(Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation), 0);
                    }

                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {

                        mHeaderAnim = new TranslateAnimation(0, 0, mHeaderDiffTotal,
                                mHeaderDiffTotal);
                        mHeaderAnim.setFillAfter(true);
                        mHeaderAnim.setDuration(0);
                        mQuickReturnHeaderTextView.startAnimation(mHeaderAnim);

                        mFooterAnim = new TranslateAnimation(0, 0, -mFooterDiffTotal,
                                -mFooterDiffTotal);
                        mFooterAnim.setFillAfter(true);
                        mFooterAnim.setDuration(0);
                        mQuickReturnFooterTextView.startAnimation(mFooterAnim);
                    } else {
                        mQuickReturnHeaderTextView.setTranslationY(mHeaderDiffTotal);
                        mQuickReturnFooterTextView.setTranslationY(-mFooterDiffTotal);

                    }
                    break;
            }


//            if(t>=oldt){ // scrolling down
//                switch (mQuickReturnType){
//                    case HEADER:
//                        if(!mQuickReturnHeaderViewVisible){
//                            mQuickReturnHeaderTextView.setVisibility(View.VISIBLE);
//                            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_header_down);
//                            mQuickReturnHeaderTextView.startAnimation(animation);
//                            mQuickReturnHeaderViewVisible = true;
//                        }
//                        break;
//                    case FOOTER:
//                        if(mQuickReturnFooterViewVisible){
//                            mQuickReturnFooterTextView.setVisibility(View.GONE);
//                            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_down);
//                            mQuickReturnFooterTextView.startAnimation(animation);
//                            mQuickReturnFooterViewVisible = false;
//                        }
//                        break;
//                    case BOTH:
//                        if(!mQuickReturnHeaderViewVisible){
//                            mQuickReturnHeaderTextView.setVisibility(View.VISIBLE);
//                            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_header_down);
//                            mQuickReturnHeaderTextView.startAnimation(animation);
//                            mQuickReturnHeaderViewVisible = true;
//                        }
//
//                        if(mQuickReturnFooterViewVisible){
//                            mQuickReturnFooterTextView.setVisibility(View.GONE);
//                            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_down);
//                            mQuickReturnFooterTextView.startAnimation(animation);
//                            mQuickReturnFooterViewVisible = false;
//                        }
//                        break;
//                }
//
//
//            } else { // scrolling up
//                switch (mQuickReturnType){
//                    case HEADER:
//                        if(mQuickReturnHeaderViewVisible){
//                            mQuickReturnHeaderTextView.setVisibility(View.GONE);
//                            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_header_up);
//                            mQuickReturnHeaderTextView.startAnimation(animation);
//                            mQuickReturnHeaderViewVisible = false;
//                        }
//                        break;
//                    case FOOTER:
//                        if(!mQuickReturnFooterViewVisible){
//                            mQuickReturnFooterTextView.setVisibility(View.VISIBLE);
//                            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_up);
//                            mQuickReturnFooterTextView.startAnimation(animation);
//                            mQuickReturnFooterViewVisible = true;
//                        }
//                        break;
//                    case BOTH:
//                        if(mQuickReturnHeaderViewVisible){
//                            mQuickReturnHeaderTextView.setVisibility(View.GONE);
//                            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_header_up);
//                            mQuickReturnHeaderTextView.startAnimation(animation);
//                            mQuickReturnHeaderViewVisible = false;
//                        }
//
//                        if(!mQuickReturnFooterViewVisible){
//                            mQuickReturnFooterTextView.setVisibility(View.VISIBLE);
//                            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_up);
//                            mQuickReturnFooterTextView.startAnimation(animation);
//                            mQuickReturnFooterViewVisible = true;
//                        }
//                        break;
//                }
//
//
//            }
        }
    };
    //endregion

    // region Constructors
    public static QuickReturn2Fragment newInstance(Bundle extras) {
        QuickReturn2Fragment fragment = new QuickReturn2Fragment();
        fragment.setRetainInstance(true);
        fragment.setArguments(extras);
        return fragment;
    }

    public QuickReturn2Fragment() {
    }
    // endregion

    // region Lifecycle Methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mQuickReturnType = QuickReturnScrollViewActivity.QuickReturnType.valueOf(getArguments().getString("QUICK_RETURN_TYPE"));
        }

        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height2);
        mMinHeaderTranslation = -(mHeaderHeight);

        mFooterHeight = getResources().getDimensionPixelSize(R.dimen.footer_height);
        mMinFooterTranslation = mFooterHeight;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_return, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switch (mQuickReturnType){
            case HEADER:
                mQuickReturnHeaderViewVisible = false;
                mQuickReturnHeaderTextView.setVisibility(View.VISIBLE);
                break;
            case FOOTER:
                mQuickReturnFooterViewVisible = true;
                mQuickReturnFooterTextView.setVisibility(View.VISIBLE);
                break;
            case BOTH:
                mQuickReturnHeaderViewVisible = false;
                mQuickReturnFooterViewVisible = true;
                mQuickReturnHeaderTextView.setVisibility(View.VISIBLE);
                mQuickReturnFooterTextView.setVisibility(View.VISIBLE);
                break;
        }

        mNotifyingScrollView.setOnScrollChangedListener(mOnScrollChangedListener);
        mNotifyingScrollView.setOverScrollEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    // endregion
}

