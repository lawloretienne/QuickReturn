package com.etiennelawlor.quickreturn.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.library.utils.QuickReturnUtils;
import com.etiennelawlor.quickreturn.models.FacebookPost;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by etiennelawlor on 7/17/14.
 */
public class FacebookAdapter extends RecyclerView.Adapter<FacebookAdapter.ViewHolder> {

    // region Member Variables
    private ArrayList<FacebookPost> mFacebookPosts;
    // endregion

    // region Constructors
    public FacebookAdapter(ArrayList<FacebookPost> facebookPosts) {
        mFacebookPosts = facebookPosts;
    }
    // endregion

    @Override
    public FacebookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.facebook_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FacebookPost post = mFacebookPosts.get(position);

        if (post != null) {
            setUpUserImage(holder.mUserImageView, post);
            setUpDisplayName(holder.mDisplayNameTextView, post);
            setUpTimestamp(holder.mTimestampTextView, post);
            setUpLikeCount(holder.mLikeCountTextView, post);
            setUpCommentCount(holder.mCommentCountTextView, post);
            setUpMessage(holder.mMessageTextView, post);
            setUpPostImage(holder.mPostImageView, post);
        }
    }

    @Override
    public int getItemCount() {
        return mFacebookPosts.size();
    }

    // region Helper Methods
    private void setUpUserImage(ImageView iv, FacebookPost post) {
        Context context = iv.getContext();
        String avatarUrl = post.getAvatarUrl();
        if (!TextUtils.isEmpty(avatarUrl)) {
            Picasso.with(context)
                    .load(avatarUrl)
                    .centerCrop()
                    .resize(QuickReturnUtils.dp2px(context, 34),
                            QuickReturnUtils.dp2px(context, 34))
//                    .placeholder(R.drawable.ic_facebook)
                    .error(android.R.drawable.stat_notify_error)
                    .into(iv);
        }
    }

    private void setUpDisplayName(TextView tv, FacebookPost post) {
        String displayName = post.getDisplayName();
        if (!TextUtils.isEmpty(displayName)) {
            tv.setText(displayName);
        }
    }

    private void setUpCommentCount(TextView tv, FacebookPost post) {
        int commentCount = post.getCommentCount();
        tv.setText(tv.getContext().getResources()
                .getQuantityString(R.plurals.comments, commentCount, commentCount));
    }

    private void setUpLikeCount(TextView tv, FacebookPost post) {
        int likeCount = post.getLikeCount();
        tv.setText(tv.getContext().getResources()
                .getQuantityString(R.plurals.likes, likeCount, likeCount));
    }

    private void setUpTimestamp(TextView tv, FacebookPost post) {
        String timestamp = post.getTimestamp();
        if (!TextUtils.isEmpty(timestamp)) {
            tv.setText(timestamp);
        }
    }

    private void setUpMessage(TextView tv, FacebookPost post) {
        String message = post.getMessage();
        if (!TextUtils.isEmpty(message)) {
            tv.setText(message);
        }
    }

    private void setUpPostImage(ImageView iv, FacebookPost post) {
        Context context = iv.getContext();
        String postImageUrl = post.getPostImageUrl();
        if (!TextUtils.isEmpty(postImageUrl)) {
            Picasso.with(context)
                    .load(postImageUrl)
//                    .placeholder(R.drawable.ic_facebook)
                    .centerCrop()
                    .resize(QuickReturnUtils.dp2px(context, 346),
                            QuickReturnUtils.dp2px(context, 320))
                    .error(android.R.drawable.stat_notify_error)
                    .into(iv);
        }
    }
    // endregion

    // region Inner Classes

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.user_iv)
        ImageView mUserImageView;
        @Bind(R.id.display_name_tv)
        TextView mDisplayNameTextView;
        @Bind(R.id.comment_count_tv)
        TextView mCommentCountTextView;
        @Bind(R.id.like_count_tv)
        TextView mLikeCountTextView;
        @Bind(R.id.timestamp_tv)
        TextView mTimestampTextView;
        @Bind(R.id.message_tv)
        TextView mMessageTextView;
        @Bind(R.id.post_iv)
        ImageView mPostImageView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    // endregion
}
