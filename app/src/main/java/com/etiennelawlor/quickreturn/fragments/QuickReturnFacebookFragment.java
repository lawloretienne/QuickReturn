package com.etiennelawlor.quickreturn.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.adapters.FacebookAdapter;
import com.etiennelawlor.quickreturn.itemdecorations.SpacesItemDecoration;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnRecyclerViewOnScrollListener;
import com.etiennelawlor.quickreturn.library.utils.QuickReturnUtils;
import com.etiennelawlor.quickreturn.models.FacebookPost;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnFacebookFragment extends Fragment {

    // region Member Variables
    private String[] mAvatarUrls;
    private String[] mDisplayNames;
    private String[] mTimestamps;
    private String[] mMessages;
    private String[] mPostImageUrls;
    private int[] mCommentCounts;
    private int[] mLikeCounts;

    @InjectView(R.id.rv)
    RecyclerView mRecyclerView;
    @InjectView(R.id.quick_return_footer_ll)
    LinearLayout mQuickReturnFooterLinearLayout;
    @InjectView(R.id.quick_return_header_tv)
    TextView mQuickReturnHeaderTextView;
    // endregion

    //region Listeners
    //endregion

    // region Constructors
    public static QuickReturnFacebookFragment newInstance() {
        QuickReturnFacebookFragment fragment = new QuickReturnFacebookFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public QuickReturnFacebookFragment() {
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAvatarUrls = getActivity().getResources().getStringArray(R.array.avatar_urls);
        mDisplayNames = getActivity().getResources().getStringArray(R.array.display_names);
        mTimestamps = getActivity().getResources().getStringArray(R.array.facebook_timestamps);
        mMessages = getActivity().getResources().getStringArray(R.array.facebook_messages);
        mPostImageUrls = getActivity().getResources().getStringArray(R.array.facebook_post_image_urls);
        mCommentCounts = getActivity().getResources().getIntArray(R.array.facebook_comment_counts);
        mLikeCounts = getActivity().getResources().getIntArray(R.array.facebook_like_counts);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_return_facebook, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<FacebookPost> posts = new ArrayList<>();
        for(int i=0; i<23; i++){
            FacebookPost post = new FacebookPost();
            post.setAvatarUrl(mAvatarUrls[i]);
            post.setDisplayName(mDisplayNames[i]);
            post.setTimestamp(mTimestamps[i]);
            post.setCommentCount(mCommentCounts[i]);
            post.setLikeCount(mLikeCounts[i]);
            post.setPostImageUrl(mPostImageUrls[i]);
            post.setMessage(mMessages[i]);
            posts.add(post);
        }

        FacebookAdapter adapter = new FacebookAdapter(getActivity(), posts);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(QuickReturnUtils.dp2px(getActivity(), 8)));

        int headerHeight = getResources().getDimensionPixelSize(R.dimen.facebook_header_height);
        int footerHeight = getResources().getDimensionPixelSize(R.dimen.facebook_footer_height);

        int headerTranslation = -headerHeight;
        int footerTranslation = -footerHeight;

        QuickReturnRecyclerViewOnScrollListener scrollListener = new QuickReturnRecyclerViewOnScrollListener(QuickReturnViewType.BOTH,
                mQuickReturnHeaderTextView, headerTranslation, mQuickReturnFooterLinearLayout, -footerTranslation);
        scrollListener.setCanSlideInIdleScrollState(true);
        mRecyclerView.setOnScrollListener(scrollListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    // endregion
}
