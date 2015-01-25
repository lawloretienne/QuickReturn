package com.etiennelawlor.quickreturn.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

import butterknife.ButterKnife;
import butterknife.InjectView;

//import java.util.logging.Handler;

/**
 * Created by etiennelawlor on 7/17/14.
 */
public class FacebookAdapter extends RecyclerView.Adapter<FacebookAdapter.ViewHolder> {

    // region Constants
    // endregion

    // region Member Variables
    private Context mContext;
    private ArrayList<FacebookPost> mFacebookPosts;
    private final LayoutInflater mInflater;

    // endregion

    // region Constructors
    public FacebookAdapter(Context context, ArrayList<FacebookPost> facebookPosts) {
        mContext = context;
        mFacebookPosts = facebookPosts;

        mInflater = LayoutInflater.from(mContext);
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

        if(post != null){
            holder.mDisplayNameTextView.setText(post.getDisplayName());
            holder.mTimestampTextView.setText(post.getTimestamp());
            holder.mLikeCountTextView.setText(mContext.getResources()
                    .getQuantityString(R.plurals.likes, post.getLikeCount(), post.getLikeCount()));

            holder.mCommentCountTextView.setText(mContext.getResources()
                    .getQuantityString(R.plurals.comments, post.getCommentCount(), post.getCommentCount()));

            holder.mMessageTextView.setText(post.getMessage());

            Picasso.with(holder.mUserImageView.getContext())
                    .load(post.getAvatarUrl())
                    .centerCrop()
                    .resize(QuickReturnUtils.dp2px(mContext, 34),
                            QuickReturnUtils.dp2px(mContext, 34))
//                    .placeholder(R.drawable.ic_facebook)
                    .error(android.R.drawable.stat_notify_error)
                    .into(holder.mUserImageView);

            Picasso.with(holder.mPostImageView.getContext())
                    .load(post.getPostImageUrl())
//                    .placeholder(R.drawable.ic_facebook)
                    .centerCrop()
                    .resize(QuickReturnUtils.dp2px(mContext, 346),
                            QuickReturnUtils.dp2px(mContext, 320))
                    .error(android.R.drawable.stat_notify_error)
                    .into(holder.mPostImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mFacebookPosts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.user_iv) ImageView mUserImageView;
        @InjectView(R.id.display_name_tv) TextView mDisplayNameTextView;
        @InjectView(R.id.comment_count_tv) TextView mCommentCountTextView;
        @InjectView(R.id.like_count_tv) TextView mLikeCountTextView;
        @InjectView(R.id.timestamp_tv) TextView mTimestampTextView;
        @InjectView(R.id.message_tv) TextView mMessageTextView;
        @InjectView(R.id.post_iv) ImageView mPostImageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
