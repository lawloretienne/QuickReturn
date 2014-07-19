package com.etiennelawlor.quickreturn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.models.Tweet;
import com.etiennelawlor.quickreturn.utils.QuickReturnUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by etiennelawlor on 7/17/14.
 */
public class TwitterAdapter extends ArrayAdapter<Tweet> {

    // region Member Variables
    private Context mContext;
    private ArrayList<Tweet> mTweets;
    private final LayoutInflater mInflater;
    // endregion

    // region Constructors
    public TwitterAdapter(Context context, ArrayList<Tweet> tweets){
        super(context, R.layout.twitter_row, tweets);
        mContext = context;
        mTweets = tweets;

        mInflater = LayoutInflater.from(mContext);
    }
    // endregion

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.twitter_row, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Tweet tweet = getItem(position);

        holder.mDisplayNameTextView.setText(tweet.getDisplayName());
        holder.mUsernameTextView.setText(tweet.getUsername());
        holder.mTimestampTextView.setText(tweet.getTimestamp());
        holder.mRetweetTextView.setText(String.valueOf(tweet.getRetweetCount()));
        holder.mStarTextView.setText(String.valueOf(tweet.getStarCount()));
        holder.mMessageTextView.setText(tweet.getMessage());

        Picasso.with(holder.mUserImageView.getContext())
                .load(tweet.getAvatarUrl())
                .centerCrop()
                .resize(QuickReturnUtils.dp2px(getContext(), 50),
                        QuickReturnUtils.dp2px(getContext(), 50))
//                    .placeholder(R.drawable.ic_facebook)
                .error(R.drawable.ic_action_edit)
                .into(holder.mUserImageView);

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.user_iv) ImageView mUserImageView;
        @InjectView(R.id.display_name_tv) TextView mDisplayNameTextView;
        @InjectView(R.id.username_tv) TextView mUsernameTextView;
        @InjectView(R.id.timestamp_tv) TextView mTimestampTextView;
        @InjectView(R.id.message_tv) TextView mMessageTextView;
        @InjectView(R.id.retweet_tv) TextView mRetweetTextView;
        @InjectView(R.id.star_tv) TextView mStarTextView;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
