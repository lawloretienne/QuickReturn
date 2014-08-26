package com.etiennelawlor.quickreturn.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnScrollViewOnScrollChangedListener;
import com.etiennelawlor.quickreturn.library.views.NotifyingScrollView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnFragment extends Fragment {

    // region Member Variables
    private QuickReturnType mQuickReturnType;

    @InjectView(R.id.scroll_view) NotifyingScrollView mNotifyingScrollView;
    @InjectView(R.id.quick_return_header_tv) TextView mQuickReturnHeaderTextView;
    @InjectView(R.id.quick_return_footer_tv) TextView mQuickReturnFooterTextView;
    // endregion

    //region Listeners
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

