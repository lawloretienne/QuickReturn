package com.etiennelawlor.quickreturn.library.listeners;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ScrollView;

import com.etiennelawlor.quickreturn.library.R;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.views.NotifyingScrollView;
import com.etiennelawlor.quickreturn.library.views.NotifyingWebView;


/**
 * Created by etiennelawlor on 7/14/14.
 */
public class SpeedyQuickReturnWebViewOnScrollChangedListener implements NotifyingWebView.OnScrollChangedListener {

    // region Member Variables
    private final QuickReturnViewType mQuickReturnViewType;
    private final View mHeader;
    private final View mFooter;
    private final Animation mSlideHeaderUpAnimation;
    private final Animation mSlideHeaderDownAnimation;
    private final Animation mSlideFooterUpAnimation;
    private final Animation mSlideFooterDownAnimation;
    // endregion

    // region Constructors
    private SpeedyQuickReturnWebViewOnScrollChangedListener(Builder builder) {
        mQuickReturnViewType = builder.mQuickReturnViewType;
        mHeader = builder.mHeader;
        mFooter = builder.mFooter;
        mSlideHeaderUpAnimation = builder.mSlideHeaderUpAnimation;
        mSlideHeaderDownAnimation = builder.mSlideHeaderDownAnimation;
        mSlideFooterUpAnimation = builder.mSlideFooterUpAnimation;
        mSlideFooterDownAnimation = builder.mSlideFooterDownAnimation;
    }
    // endregion

    @Override
    public void onScrollChanged(WebView who, int l, int t, int oldl, int oldt) {
        if(t<oldt){ // scrolling up
            switch (mQuickReturnViewType){
                case HEADER:
                    if(mHeader.getVisibility() == View.GONE){
                        mHeader.setVisibility(View.VISIBLE);
                        mHeader.startAnimation(mSlideHeaderDownAnimation);
                    }
                    break;
                case FOOTER:
                    if(mFooter.getVisibility() == View.GONE){
                        mFooter.setVisibility(View.VISIBLE);
                        mFooter.startAnimation(mSlideFooterUpAnimation);
                    }
                    break;
                case BOTH:
                    if(mHeader.getVisibility() == View.GONE){
                        mHeader.setVisibility(View.VISIBLE);
                        mHeader.startAnimation(mSlideHeaderDownAnimation);
                    }

                    if(mFooter.getVisibility() == View.GONE){
                        mFooter.setVisibility(View.VISIBLE);
                        mFooter.startAnimation(mSlideFooterUpAnimation);
                    }
                    break;
            }


        } else if (t>oldt){ // scrolling down
            switch (mQuickReturnViewType){
                case HEADER:
                    if(mHeader.getVisibility() == View.VISIBLE){
                        mHeader.setVisibility(View.GONE);
                        mHeader.startAnimation(mSlideHeaderUpAnimation);
                    }
                    break;
                case FOOTER:
                    if(mFooter.getVisibility() == View.VISIBLE){
                        mFooter.setVisibility(View.GONE);
                        mFooter.startAnimation(mSlideFooterDownAnimation);
                    }
                    break;
                case BOTH:
                    if(mHeader.getVisibility() == View.VISIBLE){
                        mHeader.setVisibility(View.GONE);
                        mHeader.startAnimation(mSlideHeaderUpAnimation);
                    }

                    if(mFooter.getVisibility() == View.VISIBLE){
                        mFooter.setVisibility(View.GONE);
                        mFooter.startAnimation(mSlideFooterDownAnimation);
                    }
                    break;
            }
        }
    }

    // region Inner Classes

    public static class Builder {
        // Required parameters
        private final QuickReturnViewType mQuickReturnViewType;

        // Optional parameters - initialized to default values
        private View mHeader = null;
        private View mFooter = null;
        private Animation mSlideHeaderUpAnimation = null;
        private Animation mSlideHeaderDownAnimation = null;
        private Animation mSlideFooterUpAnimation = null;
        private Animation mSlideFooterDownAnimation = null;

        public Builder(Context context, QuickReturnViewType quickReturnViewType) {
            mSlideHeaderUpAnimation = AnimationUtils.loadAnimation(context, R.anim.anticipate_slide_header_up);
            mSlideHeaderDownAnimation = AnimationUtils.loadAnimation(context, R.anim.overshoot_slide_header_down);
            mSlideFooterUpAnimation = AnimationUtils.loadAnimation(context, R.anim.overshoot_slide_footer_up);
            mSlideFooterDownAnimation= AnimationUtils.loadAnimation(context, R.anim.anticipate_slide_footer_down);

            mQuickReturnViewType = quickReturnViewType;
        }

        public Builder header(View header){
            mHeader = header;
            return this;
        }

        public Builder footer(View footer){
            mFooter = footer;
            return this;
        }

        public Builder slideHeaderUpAnimation(Animation slideHeaderUpAnimation){
            mSlideHeaderUpAnimation = slideHeaderUpAnimation;
            return this;
        }

        public Builder slideHeaderDownAnimation(Animation slideHeaderDownAnimation){
            mSlideHeaderDownAnimation = slideHeaderDownAnimation;
            return this;
        }

        public Builder slideFooterUpAnimation(Animation slideFooterUpAnimation){
            mSlideFooterUpAnimation = slideFooterUpAnimation;
            return this;
        }

        public Builder slideFooterDownAnimation(Animation slideFooterDownAnimation){
            mSlideFooterDownAnimation = slideFooterDownAnimation;
            return this;
        }

        public SpeedyQuickReturnWebViewOnScrollChangedListener build() {
            return new SpeedyQuickReturnWebViewOnScrollChangedListener(this);
        }
    }

    // endregion
}
