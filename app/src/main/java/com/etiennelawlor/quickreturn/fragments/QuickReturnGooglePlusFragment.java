package com.etiennelawlor.quickreturn.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.adapters.GooglePlusAdapter;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnType;
import com.etiennelawlor.quickreturn.library.listeners.SpeedyQuickReturnListViewOnScrollListener;
import com.etiennelawlor.quickreturn.models.GooglePlusPost;

import java.util.ArrayList;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnGooglePlusFragment extends ListFragment {

    // region Member Variables
    private String[] mValues;
    private String[] mAvatarUrls;
    private String[] mDisplayNames;
    private String[] mTimestamps;
    private String[] mMessages;
    private String[] mPostImageUrls;
    private String[] mComments;
    private int[] mCommentCounts;
    private int[] mPlusOneCounts;

    @InjectView(android.R.id.list) ListView mListView;
    @InjectView(R.id.quick_return_footer_iv) ImageView mQuickReturnFooterImageView;
    @InjectView(R.id.quick_return_footer_tv) TextView mQuickReturnFooterTextView;
    // endregion

    //region Listeners
    //endregion

    // region Constructors
    public static QuickReturnGooglePlusFragment newInstance() {
        QuickReturnGooglePlusFragment fragment = new QuickReturnGooglePlusFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public QuickReturnGooglePlusFragment() {
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
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        mValues = getResources().getStringArray(R.array.countries);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
//                R.layout.google_plus_list_item, R.id.item_tv, mValues);

        ArrayList<GooglePlusPost> posts = new ArrayList<GooglePlusPost>();
        for(int i=0; i<23; i++){
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

//        AnimationAdapter animAdapter = new SwingBottomInAnimationAdapter(adapter);
//        animAdapter.setAbsListView(getListView());
//        mListView.setAdapter(animAdapter);

        mListView.addFooterView(new View(getActivity()), null, false);
        mListView.addHeaderView(new View(getActivity()), null, false);

        mListView.setAdapter(adapter);

//        int headerHeight = getResources().getDimensionPixelSize(R.dimen.header_height3);
//        int headerTranslation = -(headerHeight*2) + QuickReturnUtils.getActionBarHeight(getActivity());
//        int footerTranslation = -(headerHeight*2) + QuickReturnUtils.getActionBarHeight(getActivity());

//        mListView.setOnScrollListener(new QuickReturnListViewOnScrollListener(QuickReturnType.BOTH,
//                mQuickReturnHeaderTextView, headerTranslation, mQuickReturnFooterLinearLayout, -footerTranslation));

        ArrayList<View> headerViews = new ArrayList<View>();
        headerViews.add(getActionBarView());

        ArrayList<View> footerViews = new ArrayList<View>();
        mQuickReturnFooterTextView.setTag(R.id.scroll_threshold_key, 2);
        footerViews.add(mQuickReturnFooterTextView);
        mQuickReturnFooterImageView.setTag(R.id.scroll_threshold_key, 4);
        footerViews.add(mQuickReturnFooterImageView);

        SpeedyQuickReturnListViewOnScrollListener scrollListener = new SpeedyQuickReturnListViewOnScrollListener(getActivity(), QuickReturnType.GOOGLE_PLUS, null, footerViews);
        scrollListener.setSlideHeaderUpAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_header_up));
        scrollListener.setSlideHeaderDownAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_header_down));
        scrollListener.setSlideFooterUpAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_up));
        scrollListener.setSlideFooterDownAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_down));

        mListView.setOnScrollListener(scrollListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    // endregion

    // region Helper Methods
    public View getActionBarView() {
        Window window = getActivity().getWindow();
        View v = window.getDecorView();
        int resId = getResources().getIdentifier("action_bar_container", "id", "android");
        return v.findViewById(resId);
    }
    // endregion
}
