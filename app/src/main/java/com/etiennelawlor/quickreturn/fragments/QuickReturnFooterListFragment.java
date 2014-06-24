package com.etiennelawlor.quickreturn.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.views.NotifyingListView;
import com.etiennelawlor.quickreturn.views.NotifyingScrollView;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnFooterListFragment extends ListFragment {

    // region Member Variables
    private NotifyingListView mNotifyingListView;
    private TextView mQuickReturnTextView;
    private boolean mQuickReturnViewVisible = true;
    private String[] mValues;
    // endregion

    //region Listeners

    private NotifyingListView.OnScrollChangedListener mOnScrollChangedListener = new NotifyingListView.OnScrollChangedListener() {
        public void onScrollChanged(ListView who, int l, int t, int oldl, int oldt) {
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
    public static QuickReturnFooterListFragment newInstance() {
        QuickReturnFooterListFragment fragment = new QuickReturnFooterListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public QuickReturnFooterListFragment() {
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_quick_return_footer, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindUIElements(view);

        mValues = getResources().getStringArray(R.array.languages);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, mValues);

        mNotifyingListView.setAdapter(adapter);

        mNotifyingListView.setOnScrollChangedListener(mOnScrollChangedListener);
        mNotifyingListView.setOverScrollEnabled(false);

    }

    // endregion

    // region Helper Methods
    private void bindUIElements(View view){
        mNotifyingListView = (NotifyingListView) view.findViewById(android.R.id.list);
        mQuickReturnTextView = (TextView) view.findViewById(R.id.quick_return_tv);
    }
    // endregion
}

