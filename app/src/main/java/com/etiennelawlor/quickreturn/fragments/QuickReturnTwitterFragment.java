package com.etiennelawlor.quickreturn.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.adapters.TwitterAdapter;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnType;
import com.etiennelawlor.quickreturn.interfaces.QuickReturnInterface;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnListViewOnScrollListener;
import com.etiennelawlor.quickreturn.models.Tweet;
import com.etiennelawlor.quickreturn.library.utils.QuickReturnUtils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnTwitterFragment extends ListFragment {

    // region Member Variables
    private QuickReturnInterface mCoordinator;
    private View mPlaceHolderView;
    private String[] mAvatarUrls;
    private String[] mDisplayNames;
    private String[] mUsernames;
    private String[] mTimestamps;
    private String[] mMessages;
    private int[] mStars;
    private int[] mRetweets;

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

        mAvatarUrls = getActivity().getResources().getStringArray(R.array.avatar_urls);
        mDisplayNames = getActivity().getResources().getStringArray(R.array.display_names);
        mUsernames = getActivity().getResources().getStringArray(R.array.usernames);
        mStars = getActivity().getResources().getIntArray(R.array.stars);
        mRetweets = getActivity().getResources().getIntArray(R.array.retweets);
        mTimestamps = getActivity().getResources().getStringArray(R.array.twitter_timestamps);
        mMessages = getActivity().getResources().getStringArray(R.array.twitter_messages);
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

//        int headerHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
//        int indicatorHeight =  QuickReturnUtils.dp2px(getActivity(), 5);
//        int headerTranslation = -headerHeight + QuickReturnUtils.getActionBarHeight(getActivity()) + indicatorHeight;
//        int footerTranslation = -headerHeight + QuickReturnUtils.getActionBarHeight(getActivity());

        int headerHeight = getResources().getDimensionPixelSize(R.dimen.twitter_header_height);
        int footerHeight = getResources().getDimensionPixelSize(R.dimen.twitter_footer_height);
        int indicatorHeight =  QuickReturnUtils.dp2px(getActivity(), 4);
        int headerTranslation = -headerHeight + indicatorHeight;
        int footerTranslation = -footerHeight + indicatorHeight;

        QuickReturnListViewOnScrollListener scrollListener = new QuickReturnListViewOnScrollListener(QuickReturnType.TWITTER,
                mCoordinator.getTabs(), headerTranslation, mQuickReturnFooterLinearLayout, -footerTranslation);
        scrollListener.setCanSlideInIdleScrollState(true);
        mListView.setOnScrollListener(scrollListener);

        mPlaceHolderView = getActivity().getLayoutInflater().inflate(R.layout.view_header_placeholder, mListView, false);
        mListView.addHeaderView(mPlaceHolderView);

        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        for(int i=0; i<23; i++){
            Tweet tweet = new Tweet();
            tweet.setAvatarUrl(mAvatarUrls[i]);
            tweet.setDisplayName(mDisplayNames[i]);
            tweet.setUsername(mUsernames[i]);
            tweet.setTimestamp(mTimestamps[i]);
            tweet.setStarCount(mStars[i]);
            tweet.setRetweetCount(mRetweets[i]);
            tweet.setMessage(mMessages[i]);
            tweets.add(tweet);
        }

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
