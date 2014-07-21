package com.etiennelawlor.quickreturn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.models.GooglePlusPost;
import com.etiennelawlor.quickreturn.models.Tweet;
import com.etiennelawlor.quickreturn.utils.QuickReturnUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by etiennelawlor on 7/17/14.
 */
public class GooglePlusAdapter extends ArrayAdapter<GooglePlusPost> {

    // region Member Variables
    private Context mContext;
    private ArrayList<GooglePlusPost> mGooglePlusPosts;
    private final LayoutInflater mInflater;
    // endregion

    // region Constructors
    public GooglePlusAdapter(Context context, ArrayList<GooglePlusPost> googlePlusPosts){
        super(context, R.layout.twitter_row, googlePlusPosts);
        mContext = context;
        mGooglePlusPosts = googlePlusPosts;

        mInflater = LayoutInflater.from(mContext);
    }
    // endregion

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.google_plus_row, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GooglePlusPost post = getItem(position);

        holder.mDisplayNameTextView.setText(post.getDisplayName());
//        holder.mUsernameTextView.setText(tweet.getUsername());
        holder.mTimestampTextView.setText(post.getTimestamp());
//        holder.mRetweetTextView.setText(String.valueOf(tweet.getRetweetCount()));
//        holder.mStarTextView.setText(String.valueOf(tweet.getStarCount()));

        holder.mMessageTextView.setText(post.getMessage());
//
        Picasso.with(holder.mUserImageView.getContext())
                .load(post.getAvatarUrl())
                .centerCrop()
                .resize(QuickReturnUtils.dp2px(getContext(), 50),
                        QuickReturnUtils.dp2px(getContext(), 50))
//                    .placeholder(R.drawable.ic_facebook)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.mUserImageView);

        Picasso.with(holder.mPostImageView.getContext())
                .load(post.getPostImageUrl())
//                    .placeholder(R.drawable.ic_facebook)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.mPostImageView);

        Picasso.with(holder.mCommenterOneImageView.getContext())
                .load(post.getCommenterOneAvatarUrl())
                .centerCrop()
                .resize(QuickReturnUtils.dp2px(getContext(), 34),
                        QuickReturnUtils.dp2px(getContext(), 34))
//                    .placeholder(R.drawable.ic_facebook)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.mCommenterOneImageView);

        Picasso.with(holder.mCommenterTwoImageView.getContext())
                .load(post.getCommenterTwoAvatarUrl())
                .centerCrop()
                .resize(QuickReturnUtils.dp2px(getContext(), 34),
                        QuickReturnUtils.dp2px(getContext(), 34))
//                    .placeholder(R.drawable.ic_facebook)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.mCommenterTwoImageView);

        Picasso.with(holder.mCommenterThreeImageView.getContext())
                .load(post.getCommenterThreeAvatarUrl())
                .centerCrop()
                .resize(QuickReturnUtils.dp2px(getContext(), 34),
                        QuickReturnUtils.dp2px(getContext(), 34))
//                    .placeholder(R.drawable.ic_facebook)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.mCommenterThreeImageView);




        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.user_iv) ImageView mUserImageView;
        @InjectView(R.id.display_name_tv) TextView mDisplayNameTextView;
//        @InjectView(R.id.username_tv) TextView mUsernameTextView;
        @InjectView(R.id.timestamp_tv) TextView mTimestampTextView;
        @InjectView(R.id.message_tv) TextView mMessageTextView;
        @InjectView(R.id.post_iv) ImageView mPostImageView;
        @InjectView(R.id.commenter_one_iv) ImageView mCommenterOneImageView;
        @InjectView(R.id.commenter_two_iv) ImageView mCommenterTwoImageView;
        @InjectView(R.id.commenter_three_iv) ImageView mCommenterThreeImageView;


//        @InjectView(R.id.retweet_tv) TextView mRetweetTextView;
//        @InjectView(R.id.star_tv) TextView mStarTextView;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
