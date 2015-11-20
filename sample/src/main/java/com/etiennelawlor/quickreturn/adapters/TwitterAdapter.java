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
import com.etiennelawlor.quickreturn.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by etiennelawlor on 7/17/14.
 */
public class TwitterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // region Constants
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    // endregion

    // region Member Variables
    private ArrayList<Tweet> mTweets;
    // endregion

    // region Constructors
    public TwitterAdapter(ArrayList<Tweet> tweets) {
        mTweets = tweets;
    }
    // endregion

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.twitter_row, parent, false);
            return new ItemViewHolder(v);
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_header_placeholder, parent, false);
            return new HeaderViewHolder(v);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {

            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            Tweet tweet = getItem(position);

            if (tweet != null) {
                setUpDisplayName(itemViewHolder.mDisplayNameTextView, tweet);
                setUpRetweet(itemViewHolder.mRetweetTextView, tweet);
                setUpStar(itemViewHolder.mStarTextView, tweet);
                setUpTimestamp(itemViewHolder.mTimestampTextView, tweet);
                setUpUserImage(itemViewHolder.mUserImageView, tweet);
                setUpUsername(itemViewHolder.mUsernameTextView, tweet);
                setUpMessage(itemViewHolder.mMessageTextView, tweet);
            }
        } else if (holder instanceof HeaderViewHolder) {

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mTweets.size() + 1;
    }

    // region Helper Methods
    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private Tweet getItem(int position) {
        return mTweets.get(position - 1);
    }

    private void setUpUserImage(ImageView iv, Tweet tweet) {
        Context context = iv.getContext();
        String avatarUrl = tweet.getAvatarUrl();
        if (!TextUtils.isEmpty(avatarUrl)) {
            Picasso.with(context)
                    .load(avatarUrl)
                    .centerCrop()
                    .resize(QuickReturnUtils.dp2px(context, 50),
                            QuickReturnUtils.dp2px(context, 50))
//                    .placeholder(R.drawable.ic_facebook)
                    .error(android.R.drawable.stat_notify_error)
                    .into(iv);
        }
    }

    private void setUpDisplayName(TextView tv, Tweet tweet) {
        String displayName = tweet.getDisplayName();
        if (!TextUtils.isEmpty(displayName)) {
            tv.setText(displayName);
        }
    }

    private void setUpUsername(TextView tv, Tweet tweet) {
        String username = tweet.getUsername();
        if (!TextUtils.isEmpty(username)) {
            tv.setText(username);
        }
    }

    private void setUpTimestamp(TextView tv, Tweet tweet) {
        String timestamp = tweet.getTimestamp();
        if (!TextUtils.isEmpty(timestamp)) {
            tv.setText(timestamp);
        }
    }

    private void setUpRetweet(TextView tv, Tweet tweet) {
        int retweetCount = tweet.getRetweetCount();
        tv.setText(String.valueOf(retweetCount));
    }

    private void setUpStar(TextView tv, Tweet tweet) {
        int starCount = tweet.getStarCount();
        tv.setText(String.valueOf(starCount));
    }

    private void setUpMessage(TextView tv, Tweet tweet) {
        String message = tweet.getMessage();
        if (message.length() > 160) {
            message = message.substring(0, 159);
        }

        if (!TextUtils.isEmpty(message)) {
            tv.setText(message);
        }
    }
    // endregion

    // region Inner Classes

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.user_iv)
        ImageView mUserImageView;
        @Bind(R.id.display_name_tv)
        TextView mDisplayNameTextView;
        @Bind(R.id.username_tv)
        TextView mUsernameTextView;
        @Bind(R.id.timestamp_tv)
        TextView mTimestampTextView;
        @Bind(R.id.message_tv)
        TextView mMessageTextView;
        @Bind(R.id.retweet_tv)
        TextView mRetweetTextView;
        @Bind(R.id.star_tv)
        TextView mStarTextView;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    // endregion
}
