package com.etiennelawlor.quickreturn.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnScrollViewOnScrollChangedListener;
import com.etiennelawlor.quickreturn.library.views.NotifyingScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnFragment extends Fragment {

    // region Member Variables
    private QuickReturnViewType mQuickReturnViewType;

    @Bind(R.id.scroll_view)
    NotifyingScrollView mNotifyingScrollView;
    @Bind(R.id.quick_return_header_tv)
    TextView mQuickReturnHeaderTextView;
    @Bind(R.id.quick_return_footer_tv)
    TextView mQuickReturnFooterTextView;
    // endregion

    // region Constructors
    public QuickReturnFragment() {
    }
    // endregion

    // region Factory Methods
    public static QuickReturnFragment newInstance(Bundle extras) {
        QuickReturnFragment fragment = new QuickReturnFragment();
        fragment.setRetainInstance(true);
        fragment.setArguments(extras);
        return fragment;
    }
    // endregion

    // region Lifecycle Methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mQuickReturnViewType = QuickReturnViewType.valueOf(getArguments().getString("quick_return_view_type"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_return, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int headerHeight = getResources().getDimensionPixelSize(R.dimen.header_height2);
        int headerTranslation = -(headerHeight);

        int footerTranslation = getResources().getDimensionPixelSize(R.dimen.footer_height);

        QuickReturnScrollViewOnScrollChangedListener scrollListener;

        switch (mQuickReturnViewType) {
            case HEADER:
                mQuickReturnHeaderTextView.setVisibility(View.VISIBLE);
                scrollListener = new QuickReturnScrollViewOnScrollChangedListener.Builder(QuickReturnViewType.HEADER)
                        .header(mQuickReturnHeaderTextView)
                        .minHeaderTranslation(headerTranslation)
                        .build();
                mNotifyingScrollView.setOnScrollChangedListener(scrollListener);
                break;
            case FOOTER:
                mQuickReturnFooterTextView.setVisibility(View.VISIBLE);
                scrollListener = new QuickReturnScrollViewOnScrollChangedListener.Builder(QuickReturnViewType.FOOTER)
                        .footer(mQuickReturnFooterTextView)
                        .minFooterTranslation(footerTranslation)
                        .build();
                mNotifyingScrollView.setOnScrollChangedListener(scrollListener);
                break;
            case BOTH:
                mQuickReturnHeaderTextView.setVisibility(View.VISIBLE);
                mQuickReturnFooterTextView.setVisibility(View.VISIBLE);
                scrollListener = new QuickReturnScrollViewOnScrollChangedListener.Builder(QuickReturnViewType.BOTH)
                        .header(mQuickReturnHeaderTextView)
                        .minHeaderTranslation(headerTranslation)
                        .footer(mQuickReturnFooterTextView)
                        .minFooterTranslation(footerTranslation)
                        .build();
                mNotifyingScrollView.setOnScrollChangedListener(scrollListener);
                break;
        }

        mNotifyingScrollView.setOverScrollEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    // endregion
}

