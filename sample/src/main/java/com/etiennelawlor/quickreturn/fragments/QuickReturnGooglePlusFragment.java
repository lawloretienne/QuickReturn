package com.etiennelawlor.quickreturn.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.adapters.GooglePlusAdapter;
import com.etiennelawlor.quickreturn.itemdecorations.SpacesItemDecoration;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.SpeedyQuickReturnRecyclerViewOnScrollListener;
import com.etiennelawlor.quickreturn.library.utils.QuickReturnUtils;
import com.etiennelawlor.quickreturn.models.GooglePlusPost;

import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnGooglePlusFragment extends Fragment {

    // region Member Variables
    private String[] mAvatarUrls;
    private String[] mDisplayNames;
    private String[] mTimestamps;
    private String[] mMessages;
    private String[] mPostImageUrls;
    private String[] mComments;
    private int[] mCommentCounts;
    private int[] mPlusOneCounts;
    private SpeedyQuickReturnRecyclerViewOnScrollListener mScrollListener;

    @Bind(R.id.rv)
    RecyclerView mRecyclerView;
    @Bind(R.id.quick_return_footer_iv)
    ImageView mQuickReturnFooterImageView;
    @Bind(R.id.quick_return_footer_tv)
    TextView mQuickReturnFooterTextView;
    // endregion

    // region Constructors
    public QuickReturnGooglePlusFragment() {
    }
    // endregion

    // region Factory Methods
    public static QuickReturnGooglePlusFragment newInstance() {
        QuickReturnGooglePlusFragment fragment = new QuickReturnGooglePlusFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAvatarUrls = getActivity().getResources().getStringArray(R.array.avatar_urls);
        mDisplayNames = getActivity().getResources().getStringArray(R.array.display_names);
        mTimestamps = getActivity().getResources().getStringArray(R.array.google_plus_timestamps);
        mMessages = getActivity().getResources().getStringArray(R.array.google_plus_messages);
        mPostImageUrls = getActivity().getResources().getStringArray(R.array.google_plus_post_image_urls);
        mComments = getActivity().getResources().getStringArray(R.array.google_plus_comments);
        mCommentCounts = getActivity().getResources().getIntArray(R.array.google_plus_comment_counts);
        mPlusOneCounts = getActivity().getResources().getIntArray(R.array.google_plus_plus_one_counts);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_return_google_plus, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<GooglePlusPost> posts = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            GooglePlusPost post = new GooglePlusPost();
            post.setAvatarUrl(mAvatarUrls[i]);
            post.setDisplayName(mDisplayNames[i]);
//            tweet.setUsername(mUsernames[i]);
            post.setTimestamp(mTimestamps[i]);
            post.setCommentCount(mCommentCounts[i]);
            post.setPlusOneCount(mPlusOneCounts[i]);
            post.setPostImageUrl(mPostImageUrls[i]);
            post.setComment(mComments[i]);

            int randOne = new Random().nextInt(mAvatarUrls.length);
            post.setCommenterOneDisplayName(mDisplayNames[randOne]);
            post.setCommenterOneAvatarUrl(mAvatarUrls[randOne]);


            int randTwo = new Random().nextInt(mAvatarUrls.length);
            post.setCommenterTwoDisplayName(mDisplayNames[randTwo]);
            post.setCommenterTwoAvatarUrl(mAvatarUrls[randTwo]);

            int randThree = new Random().nextInt(mAvatarUrls.length);
            post.setCommenterThreeDisplayName(mDisplayNames[randThree]);
            post.setCommenterThreeAvatarUrl(mAvatarUrls[randThree]);

//            tweet.setStarCount(mStars[i]);
//            tweet.setRetweetCount(mRetweets[i]);
            post.setMessage(mMessages[i]);
            posts.add(post);
        }

        GooglePlusAdapter adapter = new GooglePlusAdapter(getActivity(), posts);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(QuickReturnUtils.dp2px(getActivity(), 8)));

        ArrayList<View> headerViews = new ArrayList<>();
        headerViews.add(getActionBarView());

        ArrayList<View> footerViews = new ArrayList<>();
        mQuickReturnFooterTextView.setTag(R.id.scroll_threshold_key, 1);
        footerViews.add(mQuickReturnFooterTextView);
        mQuickReturnFooterImageView.setTag(R.id.scroll_threshold_key, 3);
        footerViews.add(mQuickReturnFooterImageView);

        mScrollListener = new SpeedyQuickReturnRecyclerViewOnScrollListener.Builder(getActivity(), QuickReturnViewType.GOOGLE_PLUS)
                .footerViews(footerViews)
                .slideHeaderUpAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_header_up))
                .slideHeaderDownAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_header_down))
                .slideFooterUpAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_up))
                .slideFooterDownAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_down))
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
    private View getActionBarView() {
        Window window = getActivity().getWindow();
        View v = window.getDecorView();
        int resId = getResources().getIdentifier("action_bar_container", "id", "android");
        return v.findViewById(resId);
    }

    private void removeListeners() {
        mRecyclerView.removeOnScrollListener(mScrollListener);
    }
    // endregion
}
