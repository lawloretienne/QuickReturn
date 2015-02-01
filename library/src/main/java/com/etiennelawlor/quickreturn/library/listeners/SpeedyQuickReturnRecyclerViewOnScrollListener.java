package com.etiennelawlor.quickreturn.library.listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.etiennelawlor.quickreturn.library.R;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.utils.QuickReturnUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by etiennelawlor on 7/14/14.
 */
public class SpeedyQuickReturnRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

    // region Member Variables
    private final QuickReturnViewType mQuickReturnViewType;
    private final View mHeader;
    private final View mFooter;
    private final ArrayList<View> mHeaderViews;
    private final ArrayList<View> mFooterViews;
    private final Animation mSlideHeaderUpAnimation;
    private final Animation mSlideHeaderDownAnimation;
    private final Animation mSlideFooterUpAnimation;
    private final Animation mSlideFooterDownAnimation;
    private final int mColumnCount;

    private int mPrevScrollY = 0;
    private List<RecyclerView.OnScrollListener> mExtraOnScrollListeners = new ArrayList<>();
    // endregion

    // region Constructors
    private SpeedyQuickReturnRecyclerViewOnScrollListener(Builder builder) {
        mQuickReturnViewType = builder.mQuickReturnViewType;
        mHeader = builder.mHeader;
        mFooter = builder.mFooter;
        mHeaderViews = builder.mHeaderViews;
        mFooterViews = builder.mFooterViews;
        mSlideHeaderUpAnimation = builder.mSlideHeaderUpAnimation;
        mSlideHeaderDownAnimation = builder.mSlideHeaderDownAnimation;
        mSlideFooterUpAnimation = builder.mSlideFooterUpAnimation;
        mSlideFooterDownAnimation = builder.mSlideFooterDownAnimation;
        mColumnCount = builder.mColumnCount;
    }
    // endregion

    @Override
    public void onScrollStateChanged(RecyclerView view, int newState) {
        // apply extra listener first
        for (RecyclerView.OnScrollListener listener : mExtraOnScrollListeners) {
          listener.onScrollStateChanged(view, newState);
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        // apply extra on scroll listener
        for (RecyclerView.OnScrollListener listener : mExtraOnScrollListeners) {
            listener.onScrolled(recyclerView, dx, dy);
        }

        int scrollY = QuickReturnUtils.getScrollY(recyclerView, mColumnCount);
        int diff = mPrevScrollY - scrollY;

        if(diff>0){ // scrolling up
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
                case GOOGLE_PLUS:
                    if(mHeaderViews!=null){
                        for(View view : mHeaderViews){
                            if(view.getVisibility() == View.GONE){
                                view.setVisibility(View.VISIBLE);
                                view.startAnimation(mSlideHeaderDownAnimation);
                            }
                        }
                    }

                    if(mFooterViews!=null){
                        for(View view : mFooterViews){
                            int scrollThreshold = (Integer) view.getTag(R.id.scroll_threshold_key);
                            if(diff > scrollThreshold){
                                if(view.getVisibility() == View.GONE){
                                    view.setVisibility(View.VISIBLE);
                                    view.startAnimation(mSlideFooterUpAnimation);
                                }
                            }
                        }
                    }
                    break;
            }
        } else if(diff<0){ // scrolling down
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
                case GOOGLE_PLUS:
                    if(mHeaderViews!=null){
                        for(View view : mHeaderViews){
                            if(view.getVisibility() == View.VISIBLE){
                                view.setVisibility(View.GONE);
                                view.startAnimation(mSlideHeaderUpAnimation);
                            }
                        }
                    }

                    if(mFooterViews!=null){
                        for(View view : mFooterViews){
                            int scrollThreshold = (Integer) view.getTag(R.id.scroll_threshold_key);
                            if(diff < -scrollThreshold){
                                if(view.getVisibility() == View.VISIBLE){
                                    view.setVisibility(View.GONE);
                                    view.startAnimation(mSlideFooterDownAnimation);
                                }
                            }
                        }
                    }
                    break;
            }
        }

        mPrevScrollY = scrollY;
    }

    // region Helper Methods
    public void registerExtraOnScrollListener(RecyclerView.OnScrollListener listener) {
        mExtraOnScrollListeners.add(listener);
    }
    // endregion

    // region Inner Classes

    public static class Builder {
        // Required parameters
        private final QuickReturnViewType mQuickReturnViewType;

        // Optional parameters - initialized to default values
        private View mHeader = null;
        private View mFooter = null;
        private ArrayList<View> mHeaderViews = null;
        private ArrayList<View> mFooterViews = null;
        private Animation mSlideHeaderUpAnimation = null;
        private Animation mSlideHeaderDownAnimation = null;
        private Animation mSlideFooterUpAnimation = null;
        private Animation mSlideFooterDownAnimation = null;
        private int mColumnCount = 1;

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

        public Builder headerViews(ArrayList<View> headerViews){
            mHeaderViews = headerViews;
            return this;
        }

        public Builder footerViews(ArrayList<View> footerViews){
            mFooterViews = footerViews;
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

        public Builder columnCount(int columnCount){
            mColumnCount = columnCount;
            return this;
        }

        public SpeedyQuickReturnRecyclerViewOnScrollListener build() {
            return new SpeedyQuickReturnRecyclerViewOnScrollListener(this);
        }
    }

    // endregion
}
