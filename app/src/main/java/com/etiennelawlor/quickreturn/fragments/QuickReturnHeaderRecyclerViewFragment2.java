package com.etiennelawlor.quickreturn.fragments;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.adapters.CountriesAdapter;
import com.etiennelawlor.quickreturn.itemdecorations.DividerItemDecoration;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnType;
import com.etiennelawlor.quickreturn.library.listeners.SpeedyQuickReturnListViewOnScrollListener;
import com.etiennelawlor.quickreturn.library.listeners.SpeedyQuickReturnRecyclerViewOnScrollListener;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnHeaderRecyclerViewFragment2 extends Fragment {

    // region Member Variables
    private String[] mValues;

    @InjectView(R.id.rv)
    RecyclerView mRecyclerView;
    @InjectView(R.id.quick_return_tv)
    TextView mQuickReturnTextView;
    // endregion

    // region Constructors
    public static QuickReturnHeaderRecyclerViewFragment2 newInstance() {
        QuickReturnHeaderRecyclerViewFragment2 fragment = new QuickReturnHeaderRecyclerViewFragment2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public QuickReturnHeaderRecyclerViewFragment2() {
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view_quick_return_header2, container, false);
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
        
        SpeedyQuickReturnRecyclerViewOnScrollListener scrollListener = 
                new SpeedyQuickReturnRecyclerViewOnScrollListener(getActivity(), QuickReturnType.HEADER, mQuickReturnTextView, null);    
        mRecyclerView.setOnScrollListener(scrollListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    // endregion
}
