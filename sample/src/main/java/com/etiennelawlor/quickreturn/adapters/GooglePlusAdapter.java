package com.etiennelawlor.quickreturn.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
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

//import java.util.logging.Handler;

/**
 * Created by etiennelawlor on 7/17/14.
 */
public class GooglePlusAdapter extends RecyclerView.Adapter<GooglePlusAdapter.ViewHolder> {

    // region Constants
//    private static final int INTERVAL = 5000;
    // endregion

    // region Member Variables
    private Context mContext;
    private ArrayList<GooglePlusPost> mGooglePlusPosts;
    private final LayoutInflater mInflater;
    private Transformation mTransformation;
    private Transformation mTransformation2;
    private int lastPosition = -1;

//    private int mIndicatorPosition = 0;
//    private final Handler mHandler = new Handler();
    // endregion

    // region Constructors
    public GooglePlusAdapter(Context context, ArrayList<GooglePlusPost> googlePlusPosts){
        mContext = context;
        mGooglePlusPosts = googlePlusPosts;

        mInflater = LayoutInflater.from(mContext);

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

        if(post != null){
            holder.mDisplayNameTextView.setText(post.getDisplayName());
            holder.mTimestampTextView.setText(post.getTimestamp());
            holder.mAddCommentTextView.setText(String.valueOf(post.getCommentCount()));
            holder.mPlusOneTextView.setText(mContext.getString(R.string.plus_one, post.getPlusOneCount()));
            holder.mMessageTextView.setText(post.getMessage());
//        holder.mCommentTextView.setText(post.getCommenterOneDisplayName());

            Spanned styledText = Html.fromHtml("<b>"+post.getCommenterOneDisplayName()+"</b> "+post.getComment());
            holder.mCommentTextView.setText(styledText);
//

            Picasso.with(holder.mUserImageView.getContext())
                    .load(post.getAvatarUrl())
                    .transform(mTransformation)
                    .centerCrop()
                    .resize(QuickReturnUtils.dp2px(mContext, 50),
                            QuickReturnUtils.dp2px(mContext, 50))
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

            Picasso.with(holder.mCommenterOneImageView.getContext())
                    .load(post.getCommenterOneAvatarUrl())
                    .transform(mTransformation2)
                    .centerCrop()
                    .resize(QuickReturnUtils.dp2px(mContext, 34),
                            QuickReturnUtils.dp2px(mContext, 34))
//                    .placeholder(R.drawable.ic_facebook)
                    .error(android.R.drawable.stat_notify_error)
                    .into(holder.mCommenterOneImageView);

            Picasso.with(holder.mCommenterTwoImageView.getContext())
                    .load(post.getCommenterTwoAvatarUrl())
                    .transform(mTransformation2)
                    .centerCrop()
                    .resize(QuickReturnUtils.dp2px(mContext, 34),
                            QuickReturnUtils.dp2px(mContext, 34))
//                    .placeholder(R.drawable.ic_facebook)
                    .error(android.R.drawable.stat_notify_error)
                    .into(holder.mCommenterTwoImageView);

            Picasso.with(holder.mCommenterThreeImageView.getContext())
                    .load(post.getCommenterThreeAvatarUrl())
                    .transform(mTransformation2)
                    .centerCrop()
                    .resize(QuickReturnUtils.dp2px(mContext, 34),
                            QuickReturnUtils.dp2px(mContext, 34))
//                    .placeholder(R.drawable.ic_facebook)
                    .error(android.R.drawable.stat_notify_error)
                    .into(holder.mCommenterThreeImageView);

//        holder.mRunnable = new Runnable() {
//            @Override
//            public void run() {
//
//                TranslateAnimation ta;
//
//                switch (mIndicatorPosition){
//                    case 0:
//                        ta = new TranslateAnimation(holder.mIndicatorView.getX(),
//                                holder.mIndicatorView.getX() + (QuickReturnUtils.dp2px(getContext(),37)) , 0, 0);
//                        ta.setDuration(500);
//                        ta.setFillAfter(true);
//                        holder.mIndicatorView.startAnimation(ta);
//                        mIndicatorPosition = 1;
//                        break;
//                    case 1:
//                        ta = new TranslateAnimation(holder.mIndicatorView.getX() + (QuickReturnUtils.dp2px(getContext(),37)),
//                                holder.mIndicatorView.getX() + (QuickReturnUtils.dp2px(getContext(),75)) , 0, 0);
//                        ta.setDuration(500);
//                        ta.setFillAfter(true);
//                        holder.mIndicatorView.startAnimation(ta);
//                        mIndicatorPosition = 2;
//                        break;
//                    case 2:
//                        ta = new TranslateAnimation(holder.mIndicatorView.getX() + (QuickReturnUtils.dp2px(getContext(),75)),
//                                holder.mIndicatorView.getX() , 0, 0);
//                        ta.setDuration(500);
//                        ta.setFillAfter(true);
//                        holder.mIndicatorView.startAnimation(ta);
//                        mIndicatorPosition = 0;
//                        break;
//                }
//
//                mHandler.postDelayed(holder.mRunnable, INTERVAL);
//            }
//        };
//
//        mHandler.removeCallbacks(holder.mRunnable);
//        mHandler.post(holder.mRunnable);


//        Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        convertView.startAnimation(animation);

            if(position > lastPosition){
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

    // region Inner Classes

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.user_iv) ImageView mUserImageView;
        @Bind(R.id.display_name_tv) TextView mDisplayNameTextView;
        @Bind(R.id.comment_tv) TextView mCommentTextView;
        @Bind(R.id.plus_one_tv) TextView mPlusOneTextView;
        @Bind(R.id.add_comment_tv) TextView mAddCommentTextView;
        @Bind(R.id.timestamp_tv) TextView mTimestampTextView;
        @Bind(R.id.message_tv) TextView mMessageTextView;
        @Bind(R.id.post_iv) ImageView mPostImageView;
        @Bind(R.id.commenter_one_iv) ImageView mCommenterOneImageView;
        @Bind(R.id.commenter_two_iv) ImageView mCommenterTwoImageView;
        @Bind(R.id.commenter_three_iv) ImageView mCommenterThreeImageView;
        @Bind(R.id.indicator_v) View mIndicatorView;

//        Runnable mRunnable;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    // endregion

}
