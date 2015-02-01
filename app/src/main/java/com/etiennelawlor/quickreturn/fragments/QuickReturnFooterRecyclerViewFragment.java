package com.etiennelawlor.quickreturn.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.adapters.CountriesGridLayoutAdapter;
import com.etiennelawlor.quickreturn.adapters.CountriesLinearLayoutAdapter;
import com.etiennelawlor.quickreturn.itemdecorations.DividerItemDecoration;
import com.etiennelawlor.quickreturn.itemdecorations.GridSpacesItemDecoration;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnAnimationType;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnRecyclerViewOnScrollListener;
import com.etiennelawlor.quickreturn.library.listeners.SpeedyQuickReturnRecyclerViewOnScrollListener;
import com.etiennelawlor.quickreturn.library.utils.QuickReturnUtils;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnFooterRecyclerViewFragment extends Fragment {

    // region Member Variables
    private String[] mValues;
    private QuickReturnAnimationType mQuickReturnAnimationType;
    private String mLayoutManagerType;

    @InjectView(R.id.rv)
    RecyclerView mRecyclerView;
    @InjectView(R.id.quick_return_tv)
    TextView mQuickReturnTextView;
    // endregion

    // region Constructors
    public static QuickReturnFooterRecyclerViewFragment newInstance(Bundle extras) {
        QuickReturnFooterRecyclerViewFragment fragment = new QuickReturnFooterRecyclerViewFragment();
        fragment.setRetainInstance(true);
        fragment.setArguments(extras);
        return fragment;
    }
    
    public static QuickReturnFooterRecyclerViewFragment newInstance() {
        QuickReturnFooterRecyclerViewFragment fragment = new QuickReturnFooterRecyclerViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public QuickReturnFooterRecyclerViewFragment() {
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mQuickReturnAnimationType = QuickReturnAnimationType.valueOf(getArguments().getString("quick_return_animation_type"));
            mLayoutManagerType = getArguments().getString("layout_manager");
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_quick_return_footer, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mValues = getResources().getStringArray(R.array.countries);

        if(mLayoutManagerType.equals("linear")){
            CountriesLinearLayoutAdapter countriesLinearLayoutAdapter = new CountriesLinearLayoutAdapter(getActivity(), Arrays.asList(mValues));

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));

            mRecyclerView.setAdapter(countriesLinearLayoutAdapter);
        } else if(mLayoutManagerType.equals("grid")) {
            CountriesGridLayoutAdapter countriesGridLayoutAdapter = new CountriesGridLayoutAdapter(getActivity(), Arrays.asList(mValues));

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.addItemDecoration(new GridSpacesItemDecoration(QuickReturnUtils.dp2px(getActivity(), 8)));

            mRecyclerView.setAdapter(countriesGridLayoutAdapter);
        }

        int footerHeight = getActivity().getResources().getDimensionPixelSize(R.dimen.footer_height);

        QuickReturnRecyclerViewOnScrollListener scrollListener;
        SpeedyQuickReturnRecyclerViewOnScrollListener scrollListener2;

        switch (mQuickReturnAnimationType){
            case TRANSLATION_SIMPLE:
                if(mLayoutManagerType.equals("grid")){
                    scrollListener = new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.FOOTER)
                            .footer(mQuickReturnTextView)
                            .minFooterTranslation(footerHeight)
                            .columnCount(2)
                            .build();
                } else {
                    scrollListener = new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.FOOTER)
                            .footer(mQuickReturnTextView)
                            .minFooterTranslation(footerHeight)
                            .build();
                }
                mRecyclerView.setOnScrollListener(scrollListener);
                break;
            case TRANSLATION_SNAP:
                if(mLayoutManagerType.equals("grid")){
                    scrollListener = new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.FOOTER)
                            .footer(mQuickReturnTextView)
                            .minFooterTranslation(footerHeight)
                            .columnCount(2)
                            .isSnappable(true)
                            .build();
                } else {
                    scrollListener = new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.FOOTER)
                            .footer(mQuickReturnTextView)
                            .minFooterTranslation(footerHeight)
                            .isSnappable(true)
                            .build();
                }
                mRecyclerView.setOnScrollListener(scrollListener);
                break;
            case TRANSLATION_ANTICIPATE_OVERSHOOT:
                if(mLayoutManagerType.equals("grid")){
                    scrollListener2 = new SpeedyQuickReturnRecyclerViewOnScrollListener.Builder(getActivity(), QuickReturnViewType.FOOTER)
                            .footer(mQuickReturnTextView)
                            .columnCount(2)
                            .build();
                } else {
                    scrollListener2 = new SpeedyQuickReturnRecyclerViewOnScrollListener.Builder(getActivity(), QuickReturnViewType.FOOTER)
                            .footer(mQuickReturnTextView)
                            .build();
                }
                mRecyclerView.setOnScrollListener(scrollListener2);
                break;
            default:
                if(mLayoutManagerType.equals("grid")){
                    scrollListener = new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.FOOTER)
                            .footer(mQuickReturnTextView)
                            .minFooterTranslation(footerHeight)
                            .columnCount(2)
                            .build();
                } else {
                    scrollListener = new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.FOOTER)
                            .footer(mQuickReturnTextView)
                            .minFooterTranslation(footerHeight)
                            .build();
                }
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
