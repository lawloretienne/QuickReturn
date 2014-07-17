package com.etiennelawlor.quickreturn.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.enums.QuickReturnType;
import com.etiennelawlor.quickreturn.listeners.QuickReturnListViewOnScrollListener;
import com.etiennelawlor.quickreturn.utils.QuickReturnUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnFacebookFragment extends ListFragment {

    // region Member Variables
    private String[] mValues;

    @InjectView(android.R.id.list) ListView mListView;
    @InjectView(R.id.quick_return_footer_ll) LinearLayout mQuickReturnFooterLinearLayout;
    @InjectView(R.id.quick_return_header_tv) TextView mQuickReturnHeaderTextView;
    // endregion

    //region Listeners
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

        int headerHeight = getResources().getDimensionPixelSize(R.dimen.header_height3);
        int headerTranslation = -(headerHeight*2) + QuickReturnUtils.getActionBarHeight(getActivity());
        int footerTranslation = -(headerHeight*2) + QuickReturnUtils.getActionBarHeight(getActivity());

        QuickReturnListViewOnScrollListener scrollListener = new QuickReturnListViewOnScrollListener(QuickReturnType.BOTH,
                mQuickReturnHeaderTextView, headerTranslation, mQuickReturnFooterLinearLayout, -footerTranslation);
        scrollListener.setCanSlideInIdleScrollState(true);
        mListView.setOnScrollListener(scrollListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    // endregion
}
