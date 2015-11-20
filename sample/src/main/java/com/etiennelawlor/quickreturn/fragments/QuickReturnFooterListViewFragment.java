package com.etiennelawlor.quickreturn.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnAnimationType;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnListViewOnScrollListener;
import com.etiennelawlor.quickreturn.library.listeners.SpeedyQuickReturnListViewOnScrollListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnFooterListViewFragment extends ListFragment {

    // region Member Variables
    private String[] mValues;
    private QuickReturnAnimationType mQuickReturnAnimationType;

    @Bind(android.R.id.list)
    ListView mListView;
    @Bind(R.id.quick_return_tv)
    TextView mQuickReturnTextView;
    // endregion

    // region Constructors
    public QuickReturnFooterListViewFragment() {
    }
    // endregion

    // region Factory Methods
    public static QuickReturnFooterListViewFragment newInstance(Bundle extras) {
        QuickReturnFooterListViewFragment fragment = new QuickReturnFooterListViewFragment();
        fragment.setRetainInstance(true);
        fragment.setArguments(extras);
        return fragment;
    }

    public static QuickReturnFooterListViewFragment newInstance() {
        QuickReturnFooterListViewFragment fragment = new QuickReturnFooterListViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mQuickReturnAnimationType = QuickReturnAnimationType.valueOf(getArguments().getString("quick_return_animation_type"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview_quick_return_footer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mValues = getResources().getStringArray(R.array.countries);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.list_item, R.id.item_tv, mValues);

        mListView.setAdapter(adapter);

        int footerHeight = getActivity().getResources().getDimensionPixelSize(R.dimen.footer_height);

        QuickReturnListViewOnScrollListener scrollListener;
        SpeedyQuickReturnListViewOnScrollListener scrollListener2;

        switch (mQuickReturnAnimationType) {
            case TRANSLATION_SIMPLE:
                scrollListener = new QuickReturnListViewOnScrollListener.Builder(QuickReturnViewType.FOOTER)
                        .footer(mQuickReturnTextView)
                        .minFooterTranslation(footerHeight)
                        .build();
                mListView.setOnScrollListener(scrollListener);
                break;
            case TRANSLATION_SNAP:
                scrollListener = new QuickReturnListViewOnScrollListener.Builder(QuickReturnViewType.FOOTER)
                        .footer(mQuickReturnTextView)
                        .minFooterTranslation(footerHeight)
                        .isSnappable(true)
                        .build();
                mListView.setOnScrollListener(scrollListener);
                break;
            case TRANSLATION_ANTICIPATE_OVERSHOOT:
                scrollListener2 = new SpeedyQuickReturnListViewOnScrollListener.Builder(getActivity(), QuickReturnViewType.FOOTER)
                        .footer(mQuickReturnTextView)
                        .build();
                mListView.setOnScrollListener(scrollListener2);
                break;
            default:
                scrollListener = new QuickReturnListViewOnScrollListener.Builder(QuickReturnViewType.FOOTER)
                        .footer(mQuickReturnTextView)
                        .minFooterTranslation(footerHeight)
                        .build();
                mListView.setOnScrollListener(scrollListener);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    // endregion

}
