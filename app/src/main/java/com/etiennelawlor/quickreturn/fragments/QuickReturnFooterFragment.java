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
public class QuickReturnFooterFragment extends Fragment {

    // region Member Variables
    private NotifyingScrollView mNotifyingScrollView;
    private TextView mQuickReturnTextView;
    private boolean mQuickReturnViewVisible = true;
    // endregion

    //region Listeners

    private NotifyingScrollView.OnScrollChangedListener mOnScrollChangedListener = new NotifyingScrollView.OnScrollChangedListener() {
        public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
            if(t>=oldt){
                if(mQuickReturnViewVisible){
                    mQuickReturnTextView.setVisibility(View.GONE);
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_down);
                    mQuickReturnTextView.startAnimation(animation);
                    mQuickReturnViewVisible = false;
                }
            } else {
                if(!mQuickReturnViewVisible){
                    mQuickReturnTextView.setVisibility(View.VISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_up);
                    mQuickReturnTextView.startAnimation(animation);
                    mQuickReturnViewVisible = true;
                }
            }
        }
    };
    //endregion

    // region Constructors
    public static QuickReturnFooterFragment newInstance() {
        QuickReturnFooterFragment fragment = new QuickReturnFooterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public QuickReturnFooterFragment() {
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quick_return_footer, container, false);
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
        mQuickReturnTextView = (TextView) view.findViewById(R.id.quick_return_tv);
    }
    // endregion
}

