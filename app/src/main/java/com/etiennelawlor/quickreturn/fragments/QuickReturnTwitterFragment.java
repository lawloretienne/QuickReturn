package com.etiennelawlor.quickreturn.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.adapters.TwitterAdapter;
import com.etiennelawlor.quickreturn.enums.QuickReturnType;
import com.etiennelawlor.quickreturn.interfaces.QuickReturnInterface;
import com.etiennelawlor.quickreturn.listeners.QuickReturnListViewOnScrollListener;
import com.etiennelawlor.quickreturn.models.Tweet;
import com.etiennelawlor.quickreturn.utils.QuickReturnUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnTwitterFragment extends ListFragment {

    // region Member Variables
    private String[] mValues;
    private QuickReturnInterface mCoordinator;
    private View mPlaceHolderView;

    @InjectView(android.R.id.list) ListView mListView;
    @InjectView(R.id.quick_return_footer_ll) LinearLayout mQuickReturnFooterLinearLayout;
    // endregion

    //region Listeners
    //endregion

    // region Constructors
    public static QuickReturnTwitterFragment newInstance(Bundle extras) {
        QuickReturnTwitterFragment fragment = new QuickReturnTwitterFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    public static QuickReturnTwitterFragment newInstance() {
        QuickReturnTwitterFragment fragment = new QuickReturnTwitterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public QuickReturnTwitterFragment() {
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof QuickReturnInterface) {
            mCoordinator = (QuickReturnInterface) activity;
        } else
            throw new ClassCastException("Parent container must implement the QuickReturnInterface");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_return_twitter, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int headerHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        int indicatorHeight =  QuickReturnUtils.dp2px(getActivity(), 5);
        int headerTranslation = -headerHeight + QuickReturnUtils.getActionBarHeight(getActivity()) + indicatorHeight;
        int footerTranslation = -headerHeight + QuickReturnUtils.getActionBarHeight(getActivity());

        QuickReturnListViewOnScrollListener scrollListener = new QuickReturnListViewOnScrollListener(QuickReturnType.BOTH,
                mCoordinator.getTabs(), headerTranslation, mQuickReturnFooterLinearLayout, -footerTranslation);
        scrollListener.setCanSlideInIdleScrollState(true);
        mListView.setOnScrollListener(scrollListener);

        mPlaceHolderView = getActivity().getLayoutInflater().inflate(R.layout.view_header_placeholder, mListView, false);
        mListView.addHeaderView(mPlaceHolderView);

//        mValues = getResources().getStringArray(R.array.countries);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
//                R.layout.list_item, R.id.item_tv, mValues);

        Tweet[] tweets = {
            new Tweet("abeefdsafsafa"),
            new Tweet("rwbregfdbs"),
            new Tweet("vddafe"),
            new Tweet("vrgresegr"),
            new Tweet("vrhthtr"),
            new Tweet("bthsths"),
            new Tweet("muyhtss"),
            new Tweet("zeeag"),
            new Tweet("nytsgsgr"),
            new Tweet("bytjd"),
            new Tweet("arershsherzhbvrgr")

        };

        TwitterAdapter adapter = new TwitterAdapter(getActivity(), tweets);

        mListView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    // endregion
}
