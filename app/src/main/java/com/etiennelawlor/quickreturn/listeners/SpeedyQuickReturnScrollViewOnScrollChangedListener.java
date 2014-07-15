package com.etiennelawlor.quickreturn.listeners;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.enums.QuickReturnType;
import com.etiennelawlor.quickreturn.views.NotifyingScrollView;

/**
 * Created by etiennelawlor on 7/14/14.
 */
public class SpeedyQuickReturnScrollViewOnScrollChangedListener implements NotifyingScrollView.OnScrollChangedListener {

    // region Member Variables
    private QuickReturnType mQuickReturnType;
    private View mHeader;
    private View mFooter;
    private Context mContext;
    private Animation mSlideHeaderUpAnimation;
    private Animation mSlideHeaderDownAnimation;
    private Animation mSlideFooterUpAnimation;
    private Animation mSlideFooterDownAnimation;

    // endregion

    // region Constructors
    public SpeedyQuickReturnScrollViewOnScrollChangedListener(Context context, QuickReturnType quickReturnType, View headerView, View footerView){
        mContext = context;
        mQuickReturnType = quickReturnType;

        mSlideHeaderUpAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_header_up);
        mSlideHeaderDownAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_header_down);
        mSlideFooterUpAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_footer_up);
        mSlideFooterDownAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_footer_down);

        switch (mQuickReturnType){
            case HEADER:
                mHeader =  headerView;
                break;
            case FOOTER:
                mFooter =  footerView;
            case BOTH:
                mHeader =  headerView;
                mFooter =  footerView;
            default:
                break;
        }
    }
    // endregion

    @Override
    public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
        if(t>=oldt){ // scrolling down
            switch (mQuickReturnType){
                case HEADER:
                    if(!(mHeader.getVisibility() == View.VISIBLE)){
                        mHeader.setVisibility(View.VISIBLE);
                        mHeader.startAnimation(mSlideHeaderDownAnimation);
                    }
                    break;
                case FOOTER:
                    if(mFooter.getVisibility() == View.VISIBLE){
                        mFooter.setVisibility(View.GONE);
                        mFooter.startAnimation(mSlideFooterDownAnimation);
                    }
                    break;
                case BOTH:
                    if(!(mHeader.getVisibility() == View.VISIBLE)){
                        mHeader.setVisibility(View.VISIBLE);
                        mHeader.startAnimation(mSlideHeaderDownAnimation);
                    }

                    if(mFooter.getVisibility() == View.VISIBLE){
                        mFooter.setVisibility(View.GONE);
                        mFooter.startAnimation(mSlideFooterDownAnimation);
                    }
                    break;
            }


        } else { // scrolling up
            switch (mQuickReturnType){
                case HEADER:
                    if(mHeader.getVisibility() == View.VISIBLE){
                        mHeader.setVisibility(View.GONE);
                        mHeader.startAnimation(mSlideHeaderUpAnimation);
                    }
                    break;
                case FOOTER:
                    if(!(mFooter.getVisibility() == View.VISIBLE)){
                        mFooter.setVisibility(View.VISIBLE);
                        mFooter.startAnimation(mSlideFooterUpAnimation);
                    }
                    break;
                case BOTH:
                    if(mHeader.getVisibility() == View.VISIBLE){
                        mHeader.setVisibility(View.GONE);
                        mHeader.startAnimation(mSlideHeaderUpAnimation);
                    }

                    if(!(mFooter.getVisibility() == View.VISIBLE)){
                        mFooter.setVisibility(View.VISIBLE);
                        mFooter.startAnimation(mSlideFooterUpAnimation);
                    }
                    break;
            }
        }
    }
}
