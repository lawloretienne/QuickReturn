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
public class QuickReturnHeaderFooterFragment extends Fragment {

    // region Member Variables
    private NotifyingScrollView mNotifyingScrollView;
    private TextView mQuickReturnHeaderTextView;
    private TextView mQuickReturnFooterTextView;
    private boolean mQuickReturnHeaderViewVisible = false;
    private boolean mQuickReturnFooterViewVisible = true;
    // endregion

    //region Listeners

    private NotifyingScrollView.OnScrollChangedListener mOnScrollChangedListener = new NotifyingScrollView.OnScrollChangedListener() {
        public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
            if(t>=oldt){
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
            } else {
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
            }
        }
    };
    //endregion

    // region Constructors
    public static QuickReturnHeaderFooterFragment newInstance() {
        QuickReturnHeaderFooterFragment fragment = new QuickReturnHeaderFooterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public QuickReturnHeaderFooterFragment() {
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quick_return_header_footer, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindUIElements(view);

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
}
