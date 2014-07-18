package com.etiennelawlor.quickreturn.adapters;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.models.Tweet;
import com.etiennelawlor.quickreturn.utils.QuickReturnUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by etiennelawlor on 7/17/14.
 */
public class TwitterAdapter extends ArrayAdapter<Tweet> {

    // region Member Variables
    private Context mContext;
    private Tweet[] mTweets;
    private final LayoutInflater mInflater;
    // endregion

    // region Constructors
    public TwitterAdapter(Context context, Tweet[] tweets){
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
//        holder.word.setText("Word: " + tweet.getWord());
//        holder.length.setText("Length: " + tweet.getWord().length());
//        holder.position.setText("Position: " + position);

        String profileImageUrl = "http://thunderbird37.com/wp-content/uploads/2014/03/megan-fox-pictures.jpg";
//        String profileImageUrl = "http://cdn02.cdn.justjared.com/wp-content/uploads/2008/09/fox-gq/megan-fox-gq-october-2008-10.jpg";
//        String profileImageUrl = "http://www.officialpsds.com/images/thumbs/Wave-psd28864.png";


        if (!TextUtils.isEmpty(profileImageUrl)) {
            Picasso.with(holder.mUserImageView.getContext())
                    .load(profileImageUrl)
                    .centerCrop()
                    .resize(QuickReturnUtils.dp2px(getContext(), 50),
                            QuickReturnUtils.dp2px(getContext(), 50))
//                    .placeholder(R.drawable.ic_facebook)
                    .error(R.drawable.ic_action_edit)
                    .into(holder.mUserImageView);


        } else {
//            holder.mUserImageView.setImageResource(R.drawable.ic_facebook);
        }

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.user_iv) ImageView mUserImageView;
        @InjectView(R.id.display_name_tv) TextView mDisplayNameTextView;
        @InjectView(R.id.username_tv) TextView mUsernameTextView;
        @InjectView(R.id.timestamp_tv) TextView mTimestampTextView;
        @InjectView(R.id.message_tv) TextView mMessageTextView;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
