package com.etiennelawlor.quickreturn.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.adapters.TwitterAdapter;
import com.etiennelawlor.quickreturn.interfaces.QuickReturnInterface;
import com.etiennelawlor.quickreturn.itemdecorations.DividerItemDecoration;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnRecyclerViewOnScrollListener;
import com.etiennelawlor.quickreturn.library.utils.QuickReturnUtils;
import com.etiennelawlor.quickreturn.models.Tweet;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnTwitterFragment extends Fragment {

    // region Member Variables
    private QuickReturnInterface mCoordinator;
    private String[] mAvatarUrls;
    private String[] mDisplayNames;
    private String[] mUsernames;
    private String[] mTimestamps;
    private String[] mMessages;
    private int[] mStars;
    private int[] mRetweets;
    private QuickReturnRecyclerViewOnScrollListener mScrollListener;

    @Bind(R.id.rv)
    RecyclerView mRecyclerView;
    @Bind(R.id.quick_return_footer_ll)
    LinearLayout mQuickReturnFooterLinearLayout;
    // endregion

    // region Constructors
    public QuickReturnTwitterFragment() {
    }
    // endregion

    // region Factory Methods
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
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
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

        TwitterAdapter adapter = new TwitterAdapter(tweets);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));

        mRecyclerView.setAdapter(adapter);

        int headerHeight = getResources().getDimensionPixelSize(R.dimen.twitter_header_height);
        int footerHeight = getResources().getDimensionPixelSize(R.dimen.twitter_footer_height);
        int indicatorHeight = QuickReturnUtils.dp2px(getActivity(), 4);
        int headerTranslation = -headerHeight + indicatorHeight;
        int footerTranslation = -footerHeight + indicatorHeight;

        mScrollListener =
                new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.TWITTER)
                        .header(mCoordinator.getTabs())
                        .minHeaderTranslation(headerTranslation)
                        .footer(mQuickReturnFooterLinearLayout)
                        .minFooterTranslation(-footerTranslation)
                        .isSnappable(true)
                        .build();

        mRecyclerView.addOnScrollListener(mScrollListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        removeListeners();

        ButterKnife.unbind(this);
    }
    // endregion

    // region Helper Methods
    private void removeListeners() {
        mRecyclerView.removeOnScrollListener(mScrollListener);
    }
    // endregion
}
