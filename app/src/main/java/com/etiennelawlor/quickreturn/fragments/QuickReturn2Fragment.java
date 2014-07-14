package com.etiennelawlor.quickreturn.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.activities.QuickReturnScrollViewActivity;
import com.etiennelawlor.quickreturn.enums.QuickReturnType;
import com.etiennelawlor.quickreturn.listeners.QuickReturnScrollViewOnScrollChangedListener;
import com.etiennelawlor.quickreturn.views.NotifyingScrollView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturn2Fragment extends Fragment {

    // region Member Variables
    private QuickReturnScrollViewActivity.QuickReturnType mQuickReturnType;

    @InjectView(R.id.scroll_view) NotifyingScrollView mNotifyingScrollView;
    @InjectView(R.id.quick_return_header_tv) TextView mQuickReturnHeaderTextView;
    @InjectView(R.id.quick_return_footer_tv) TextView mQuickReturnFooterTextView;
    // endregion

    //region Listeners
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

        int headerHeight = getResources().getDimensionPixelSize(R.dimen.header_height2);
        int headerTranslation = -(headerHeight);

        int footerHeight = getResources().getDimensionPixelSize(R.dimen.footer_height);
        int footerTranslation = footerHeight;

        switch (mQuickReturnType){
            case HEADER:
                mQuickReturnHeaderTextView.setVisibility(View.VISIBLE);
                mNotifyingScrollView.setOnScrollChangedListener(new QuickReturnScrollViewOnScrollChangedListener(QuickReturnType.HEADER,
                        mQuickReturnHeaderTextView, headerTranslation, null, 0));
                break;
            case FOOTER:
                mQuickReturnFooterTextView.setVisibility(View.VISIBLE);
                mNotifyingScrollView.setOnScrollChangedListener(new QuickReturnScrollViewOnScrollChangedListener(QuickReturnType.FOOTER,
                        null, 0, mQuickReturnFooterTextView, footerTranslation));
                break;
            case BOTH:
                mQuickReturnHeaderTextView.setVisibility(View.VISIBLE);
                mQuickReturnFooterTextView.setVisibility(View.VISIBLE);
                mNotifyingScrollView.setOnScrollChangedListener(new QuickReturnScrollViewOnScrollChangedListener(QuickReturnType.BOTH,
                        mQuickReturnHeaderTextView, headerTranslation, mQuickReturnFooterTextView, footerTranslation));
                break;
        }

        mNotifyingScrollView.setOverScrollEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    // endregion
}

