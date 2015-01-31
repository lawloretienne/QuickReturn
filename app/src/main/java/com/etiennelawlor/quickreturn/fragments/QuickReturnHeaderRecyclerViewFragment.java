package com.etiennelawlor.quickreturn.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.adapters.CountriesAdapter;
import com.etiennelawlor.quickreturn.itemdecorations.DividerItemDecoration;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnAnimationType;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnRecyclerViewOnScrollListener;
import com.etiennelawlor.quickreturn.library.listeners.SpeedyQuickReturnRecyclerViewOnScrollListener;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnHeaderRecyclerViewFragment extends Fragment {

    // region Member Variables
    private String[] mValues;
    private QuickReturnAnimationType mQuickReturnAnimationType;

    @InjectView(R.id.rv)
    RecyclerView mRecyclerView;
    @InjectView(R.id.quick_return_tv)
    TextView mQuickReturnTextView;
    // endregion

    // region Constructors
    public static QuickReturnHeaderRecyclerViewFragment newInstance(Bundle extras) {
        QuickReturnHeaderRecyclerViewFragment fragment = new QuickReturnHeaderRecyclerViewFragment();
        fragment.setRetainInstance(true);
        fragment.setArguments(extras);
        return fragment;
    }
    
    public static QuickReturnHeaderRecyclerViewFragment newInstance() {
        QuickReturnHeaderRecyclerViewFragment fragment = new QuickReturnHeaderRecyclerViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public QuickReturnHeaderRecyclerViewFragment() {
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mQuickReturnAnimationType = QuickReturnAnimationType.valueOf(getArguments().getString("quick_return_animation_type"));
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_quick_return_header, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mValues = getResources().getStringArray(R.array.countries);

        CountriesAdapter countriesAdapter = new CountriesAdapter(getActivity(), Arrays.asList(mValues));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));

        mRecyclerView.setAdapter(countriesAdapter);

        int headerHeight = getActivity().getResources().getDimensionPixelSize(R.dimen.header_height2);

        QuickReturnRecyclerViewOnScrollListener scrollListener;
        SpeedyQuickReturnRecyclerViewOnScrollListener scrollListener2;
                
        switch (mQuickReturnAnimationType){
            case TRANSLATION_SIMPLE:
                scrollListener = new QuickReturnRecyclerViewOnScrollListener(QuickReturnViewType.HEADER, mQuickReturnTextView, -headerHeight, null, 0);
                mRecyclerView.setOnScrollListener(scrollListener);
                break;
            case TRANSLATION_SNAP:
                scrollListener = new QuickReturnRecyclerViewOnScrollListener(QuickReturnViewType.HEADER,
                        mQuickReturnTextView, -headerHeight, null, 0);
                scrollListener.setCanSlideInIdleScrollState(true);
                mRecyclerView.setOnScrollListener(scrollListener);
                break;
            case TRANSLATION_ANTICIPATE_OVERSHOOT:
                scrollListener2 = new SpeedyQuickReturnRecyclerViewOnScrollListener(getActivity(), QuickReturnViewType.HEADER, mQuickReturnTextView, null);
                mRecyclerView.setOnScrollListener(scrollListener2);
                break;
            default:
                scrollListener = new QuickReturnRecyclerViewOnScrollListener(QuickReturnViewType.HEADER, mQuickReturnTextView, -headerHeight, null, 0);
                mRecyclerView.setOnScrollListener(scrollListener);
                break;
        }
        

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    // endregion
}
