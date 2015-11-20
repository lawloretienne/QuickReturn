package com.etiennelawlor.quickreturn.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.library.utils.QuickReturnUtils;
import com.etiennelawlor.quickreturn.models.GooglePlusPost;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by etiennelawlor on 7/17/14.
 */
public class GooglePlusAdapter extends RecyclerView.Adapter<GooglePlusAdapter.ViewHolder> {

    // region Member Variables
    private Context mContext;
    private ArrayList<GooglePlusPost> mGooglePlusPosts;
    private Transformation mTransformation;
    private Transformation mTransformation2;
    private int lastPosition = -1;
    // endregion

    // region Constructors
    public GooglePlusAdapter(Context context, ArrayList<GooglePlusPost> googlePlusPosts) {
        mContext = context;
        mGooglePlusPosts = googlePlusPosts;

        mTransformation = new RoundedTransformationBuilder()
//                .borderColor(getContext().getResources().getColor(R.color.white))
                .cornerRadius(QuickReturnUtils.dp2px(mContext, 50))
                .build();

        mTransformation2 = new RoundedTransformationBuilder()
//                .borderColor(getContext().getResources().getColor(R.color.white))
                .cornerRadius(QuickReturnUtils.dp2px(mContext, 2))
                .build();
    }
    // endregion

    @Override
    public GooglePlusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.google_plus_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GooglePlusPost post = mGooglePlusPosts.get(position);

        if (post != null) {
            setUpUserImage(holder.mUserImageView, post);
            setUpDisplayName(holder.mDisplayNameTextView, post);
            setUpComment(holder.mCommentTextView, post);
            setUpPlusOne(holder.mPlusOneTextView, post);
            setUpAddComment(holder.mAddCommentTextView, post);
            setUpTimestamp(holder.mTimestampTextView, post);
            setUpMessage(holder.mMessageTextView, post);
            setUpPostImage(holder.mPostImageView, post);
            setUpCommenterOneImage(holder.mCommenterOneImageView, post);
            setUpCommenterTwoImage(holder.mCommenterTwoImageView, post);
            setUpCommenterThreeImage(holder.mCommenterThreeImageView, post);

//        Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        convertView.startAnimation(animation);

            if (position > lastPosition) {
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.up_from_bottom);
                holder.itemView.startAnimation(animation);
            }

//        if(position > lastPosition){
//            YoYo.with(Techniques.SlideInUp)
//                    .duration(700)
//                    .playOn(convertView);
//        }

            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mGooglePlusPosts.size();
    }

    // region Helper Methods
    private void setUpUserImage(ImageView iv, GooglePlusPost post) {
        String avatarUrl = post.getAvatarUrl();
        if (!TextUtils.isEmpty(avatarUrl)) {
            Picasso.with(iv.getContext())
                    .load(avatarUrl)
                    .transform(mTransformation)
                    .centerCrop()
                    .resize(QuickReturnUtils.dp2px(mContext, 50),
                            QuickReturnUtils.dp2px(mContext, 50))
//                    .placeholder(R.drawable.ic_facebook)
                    .error(android.R.drawable.stat_notify_error)
                    .into(iv);
        }
    }

    private void setUpDisplayName(TextView tv, GooglePlusPost post) {
        String displayName = post.getDisplayName();
        if (!TextUtils.isEmpty(displayName)) {
            tv.setText(displayName);
        }
    }

    private void setUpComment(TextView tv, GooglePlusPost post) {
        String commenterOneDisplayName = post.getCommenterOneDisplayName();
        String comment = post.getComment();

        Spanned styledText = Html.fromHtml("<b>" + commenterOneDisplayName + "</b> " + comment);
        if (!TextUtils.isEmpty(styledText)) {
            tv.setText(styledText);
        }
    }

    private void setUpPlusOne(TextView tv, GooglePlusPost post) {
        int plusOneCount = post.getPlusOneCount();
        tv.setText(tv.getContext().getString(R.string.plus_one, plusOneCount));
    }

    private void setUpAddComment(TextView tv, GooglePlusPost post) {
        int commentCount = post.getCommentCount();
        tv.setText(String.valueOf(commentCount));
    }

    private void setUpTimestamp(TextView tv, GooglePlusPost post) {
        String timestamp = post.getTimestamp();
        if (!TextUtils.isEmpty(timestamp)) {
            tv.setText(timestamp);
        }
    }

    private void setUpMessage(TextView tv, GooglePlusPost post) {
        String message = post.getMessage();
        if (!TextUtils.isEmpty(message)) {
            tv.setText(message);
        }
    }

    private void setUpPostImage(ImageView iv, GooglePlusPost post) {
        String postImageUrl = post.getPostImageUrl();
        if (!TextUtils.isEmpty(postImageUrl)) {
            Picasso.with(iv.getContext())
                    .load(postImageUrl)
//                    .placeholder(R.drawable.ic_facebook)
                    .centerCrop()
                    .resize(QuickReturnUtils.dp2px(mContext, 346),
                            QuickReturnUtils.dp2px(mContext, 320))
                    .error(android.R.drawable.stat_notify_error)
                    .into(iv);
        }
    }

    private void setUpCommenterOneImage(ImageView iv, GooglePlusPost post) {
        String commenterOneAvatarUrl = post.getCommenterOneAvatarUrl();
        if (!TextUtils.isEmpty(commenterOneAvatarUrl)) {
            Picasso.with(iv.getContext())
                    .load(commenterOneAvatarUrl)
                    .transform(mTransformation2)
                    .centerCrop()
                    .resize(QuickReturnUtils.dp2px(mContext, 34),
                            QuickReturnUtils.dp2px(mContext, 34))
//                    .placeholder(R.drawable.ic_facebook)
                    .error(android.R.drawable.stat_notify_error)
                    .into(iv);
        }
    }

    private void setUpCommenterTwoImage(ImageView iv, GooglePlusPost post) {
        String commenterTwoAvatarUrl = post.getCommenterTwoAvatarUrl();
        if (!TextUtils.isEmpty(commenterTwoAvatarUrl)) {
            Picasso.with(iv.getContext())
                    .load(commenterTwoAvatarUrl)
                    .transform(mTransformation2)
                    .centerCrop()
                    .resize(QuickReturnUtils.dp2px(mContext, 34),
                            QuickReturnUtils.dp2px(mContext, 34))
//                    .placeholder(R.drawable.ic_facebook)
                    .error(android.R.drawable.stat_notify_error)
                    .into(iv);
        }
    }

    private void setUpCommenterThreeImage(ImageView iv, GooglePlusPost post) {
        String commenterThreeAvatarUrl = post.getCommenterThreeAvatarUrl();
        if (!TextUtils.isEmpty(commenterThreeAvatarUrl)) {
            Picasso.with(iv.getContext())
                    .load(commenterThreeAvatarUrl)
                    .transform(mTransformation2)
                    .centerCrop()
                    .resize(QuickReturnUtils.dp2px(mContext, 34),
                            QuickReturnUtils.dp2px(mContext, 34))
//                    .placeholder(R.drawable.ic_facebook)
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
        @Bind(R.id.comment_tv)
        TextView mCommentTextView;
        @Bind(R.id.plus_one_tv)
        TextView mPlusOneTextView;
        @Bind(R.id.add_comment_tv)
        TextView mAddCommentTextView;
        @Bind(R.id.timestamp_tv)
        TextView mTimestampTextView;
        @Bind(R.id.message_tv)
        TextView mMessageTextView;
        @Bind(R.id.post_iv)
        ImageView mPostImageView;
        @Bind(R.id.commenter_one_iv)
        ImageView mCommenterOneImageView;
        @Bind(R.id.commenter_two_iv)
        ImageView mCommenterTwoImageView;
        @Bind(R.id.commenter_three_iv)
        ImageView mCommenterThreeImageView;
        @Bind(R.id.indicator_v)
        View mIndicatorView;

//        Runnable mRunnable;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    // endregion

}
