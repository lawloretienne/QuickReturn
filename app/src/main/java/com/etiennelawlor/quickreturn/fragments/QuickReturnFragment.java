package com.etiennelawlor.quickreturn.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.views.NotifyingScrollView;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnFragment extends Fragment {

    // region Member Variables
    private NotifyingScrollView mNotifyingScrollView;
    private QuickReturnType mQuickReturnType;

    private TextView mQuickReturnHeaderTextView;
    private TextView mQuickReturnFooterTextView;
    private boolean mQuickReturnHeaderViewVisible = false;
    private boolean mQuickReturnFooterViewVisible = false;
    // endregion

    //region Listeners

    private NotifyingScrollView.OnScrollChangedListener mOnScrollChangedListener = new NotifyingScrollView.OnScrollChangedListener() {
        public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
            if(t>=oldt){
                switch (mQuickReturnType){
                    case HEADER:
                        if(!mQuickReturnHeaderViewVisible){
                            mQuickReturnHeaderTextView.setVisibility(View.VISIBLE);
                            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_header_down);
                            mQuickReturnHeaderTextView.startAnimation(animation);
                            mQuickReturnHeaderViewVisible = true;
                        }
                        break;
                    case FOOTER:
                        if(mQuickReturnFooterViewVisible){
                            mQuickReturnFooterTextView.setVisibility(View.GONE);
                            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_down);
                            mQuickReturnFooterTextView.startAnimation(animation);
                            mQuickReturnFooterViewVisible = false;
                        }
                        break;
                    case BOTH:
                        if(!mQuickReturnHeaderViewVisible){
                            mQuickReturnHeaderTextView.setVisibility(View.VISIBLE);
                            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_header_down);
                            mQuickReturnHeaderTextView.startAnimation(animation);
                            mQuickReturnHeaderViewVisible = true;
                        }

                        if(mQuickReturnFooterViewVisible){
                            mQuickReturnFooterTextView.setVisibility(View.GONE);
                            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_down);
                            mQuickReturnFooterTextView.startAnimation(animation);
                            mQuickReturnFooterViewVisible = false;
                        }
                        break;
                }


            } else {
                switch (mQuickReturnType){
                    case HEADER:
                        if(mQuickReturnHeaderViewVisible){
                            mQuickReturnHeaderTextView.setVisibility(View.GONE);
                            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_header_up);
                            mQuickReturnHeaderTextView.startAnimation(animation);
                            mQuickReturnHeaderViewVisible = false;
                        }
                        break;
                    case FOOTER:
                        if(!mQuickReturnFooterViewVisible){
                            mQuickReturnFooterTextView.setVisibility(View.VISIBLE);
                            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_up);
                            mQuickReturnFooterTextView.startAnimation(animation);
                            mQuickReturnFooterViewVisible = true;
                        }
                        break;
                    case BOTH:
                        if(mQuickReturnHeaderViewVisible){
                            mQuickReturnHeaderTextView.setVisibility(View.GONE);
                            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_header_up);
                            mQuickReturnHeaderTextView.startAnimation(animation);
                            mQuickReturnHeaderViewVisible = false;
                        }

                        if(!mQuickReturnFooterViewVisible){
                            mQuickReturnFooterTextView.setVisibility(View.VISIBLE);
                            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_up);
                            mQuickReturnFooterTextView.startAnimation(animation);
                            mQuickReturnFooterViewVisible = true;
                        }
                        break;
                }


            }
        }
    };
    //endregion

    // region Constructors
    public static QuickReturnFragment newInstance(Bundle extras) {
        QuickReturnFragment fragment = new QuickReturnFragment();
        fragment.setRetainInstance(true);
        fragment.setArguments(extras);
        return fragment;
    }

    public QuickReturnFragment() {
    }
    // endregion

    // region Lifecycle Methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mQuickReturnType = QuickReturnType.valueOf(getArguments().getString("QUICK_RETURN_TYPE"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quick_return, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindUIElements(view);

        switch (mQuickReturnType){
            case HEADER:
                mQuickReturnHeaderViewVisible = false;
                break;
            case FOOTER:
                mQuickReturnFooterViewVisible = true;
                mQuickReturnFooterTextView.setVisibility(View.VISIBLE);
                break;
            case BOTH:
                mQuickReturnHeaderViewVisible = false;
                mQuickReturnFooterViewVisible = true;
                mQuickReturnFooterTextView.setVisibility(View.VISIBLE);
                break;
        }

        mNotifyingScrollView.setOnScrollChangedListener(mOnScrollChangedListener);
        mNotifyingScrollView.setOverScrollEnabled(false);
    }

    // endregion

    // region Helper Methods
    private void bindUIElements(View view){
        mNotifyingScrollView = (NotifyingScrollView) view.findViewById(R.id.scroll_view);
        mQuickReturnHeaderTextView = (TextView) view.findViewById(R.id.quick_return_header_tv);
        mQuickReturnFooterTextView = (TextView) view.findViewById(R.id.quick_return_footer_tv);
    }
    // endregion

    public enum QuickReturnType {
        HEADER,
        FOOTER,
        BOTH
    }
}

