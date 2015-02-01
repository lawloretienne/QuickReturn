package com.etiennelawlor.quickreturn.library.listeners;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.utils.QuickReturnUtils;

import java.util.ArrayList;
import java.util.List;

/**
* Created by etiennelawlor on 7/10/14.
*/
public class QuickReturnRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

    // region Member Variables
    private final QuickReturnViewType mQuickReturnViewType;
    private final View mHeader;
    private final int mMinHeaderTranslation;
    private final View mFooter;
    private final int mMinFooterTranslation;
    private final int mColumnCount;
    private final boolean mIsSnappable; // Can Quick Return view snap into place?

    private int mPrevScrollY = 0;
    private int mHeaderDiffTotal = 0;
    private int mFooterDiffTotal = 0;
    private List<RecyclerView.OnScrollListener> mExtraOnScrollListenerList = new ArrayList<>();
    // endregion

    // region Constructors
    private QuickReturnRecyclerViewOnScrollListener(Builder builder) {
        mQuickReturnViewType = builder.mQuickReturnViewType;
        mHeader = builder.mHeader;
        mMinHeaderTranslation = builder.mMinHeaderTranslation;
        mFooter = builder.mFooter;
        mMinFooterTranslation = builder.mMinFooterTranslation;
        mColumnCount = builder.mColumnCount;
        mIsSnappable = builder.mIsSnappable;
    }
    // endregion


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        // apply another list' s on scroll listener
        for (RecyclerView.OnScrollListener listener : mExtraOnScrollListenerList) {
          listener.onScrollStateChanged(recyclerView, newState);
        }
        
        if (newState == RecyclerView.SCROLL_STATE_IDLE && mIsSnappable) {

            int midHeader = -mMinHeaderTranslation/2;
            int midFooter = mMinFooterTranslation/2;

            switch (mQuickReturnViewType) {
                case HEADER:
                    if (-mHeaderDiffTotal > 0 && -mHeaderDiffTotal < midHeader) {
                        ObjectAnimator anim = ObjectAnimator.ofFloat(mHeader, "translationY", mHeader.getTranslationY(), 0);
                        anim.setDuration(100);
                        anim.start();
                        mHeaderDiffTotal = 0;
                    } else if (-mHeaderDiffTotal < -mMinHeaderTranslation && -mHeaderDiffTotal >= midHeader) {
                        ObjectAnimator anim = ObjectAnimator.ofFloat(mHeader, "translationY", mHeader.getTranslationY(), mMinHeaderTranslation);
                        anim.setDuration(100);
                        anim.start();
                        mHeaderDiffTotal = mMinHeaderTranslation;
                    }
                    break;
                case FOOTER:
                    if (-mFooterDiffTotal > 0 && -mFooterDiffTotal < midFooter) { // slide up
                        ObjectAnimator anim = ObjectAnimator.ofFloat(mFooter, "translationY", mFooter.getTranslationY(), 0);
                        anim.setDuration(100);
                        anim.start();
                        mFooterDiffTotal = 0;
                    } else if (-mFooterDiffTotal < mMinFooterTranslation && -mFooterDiffTotal >= midFooter) { // slide down
                        ObjectAnimator anim = ObjectAnimator.ofFloat(mFooter, "translationY", mFooter.getTranslationY(), mMinFooterTranslation);
                        anim.setDuration(100);
                        anim.start();
                        mFooterDiffTotal = -mMinFooterTranslation;
                    }
                    break;
                case BOTH:
                    if (-mHeaderDiffTotal > 0 && -mHeaderDiffTotal < midHeader) {
                        ObjectAnimator anim = ObjectAnimator.ofFloat(mHeader, "translationY", mHeader.getTranslationY(), 0);
                        anim.setDuration(100);
                        anim.start();
                        mHeaderDiffTotal = 0;
                    } else if (-mHeaderDiffTotal < -mMinHeaderTranslation && -mHeaderDiffTotal >= midHeader) {
                        ObjectAnimator anim = ObjectAnimator.ofFloat(mHeader, "translationY", mHeader.getTranslationY(), mMinHeaderTranslation);
                        anim.setDuration(100);
                        anim.start();
                        mHeaderDiffTotal = mMinHeaderTranslation;
                    }

                    if (-mFooterDiffTotal > 0 && -mFooterDiffTotal < midFooter) { // slide up
                        ObjectAnimator anim = ObjectAnimator.ofFloat(mFooter, "translationY", mFooter.getTranslationY(), 0);
                        anim.setDuration(100);
                        anim.start();
                        mFooterDiffTotal = 0;
                    } else if (-mFooterDiffTotal < mMinFooterTranslation && -mFooterDiffTotal >= midFooter) { // slide down
                        ObjectAnimator anim = ObjectAnimator.ofFloat(mFooter, "translationY", mFooter.getTranslationY(), mMinFooterTranslation);
                        anim.setDuration(100);
                        anim.start();
                        mFooterDiffTotal = -mMinFooterTranslation;
                    }
                    break;
                case TWITTER:
                    if (-mHeaderDiffTotal > 0 && -mHeaderDiffTotal < midHeader) {
                        ObjectAnimator anim = ObjectAnimator.ofFloat(mHeader, "translationY", mHeader.getTranslationY(), 0);
                        anim.setDuration(100);
                        anim.start();
                        mHeaderDiffTotal = 0;
                    } else if (-mHeaderDiffTotal < -mMinHeaderTranslation && -mHeaderDiffTotal >= midHeader) {
                        ObjectAnimator anim = ObjectAnimator.ofFloat(mHeader, "translationY", mHeader.getTranslationY(), mMinHeaderTranslation);
                        anim.setDuration(100);
                        anim.start();
                        mHeaderDiffTotal = mMinHeaderTranslation;
                    }

                    if (-mFooterDiffTotal > 0 && -mFooterDiffTotal < midFooter) { // slide up
                        ObjectAnimator anim = ObjectAnimator.ofFloat(mFooter, "translationY", mFooter.getTranslationY(), 0);
                        anim.setDuration(100);
                        anim.start();
                        mFooterDiffTotal = 0;
                    } else if (-mFooterDiffTotal < mMinFooterTranslation && -mFooterDiffTotal >= midFooter) { // slide down
                        ObjectAnimator anim = ObjectAnimator.ofFloat(mFooter, "translationY", mFooter.getTranslationY(), mMinFooterTranslation);
                        anim.setDuration(100);
                        anim.start();
                        mFooterDiffTotal = -mMinFooterTranslation;
                    }
                    break;
            }

        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        // apply extra on scroll listener
        for (RecyclerView.OnScrollListener listener : mExtraOnScrollListenerList) {
          listener.onScrolled(recyclerView, dx, dy);
        }

        int scrollY = QuickReturnUtils.getScrollY(recyclerView, mColumnCount);
//        Log.d("", "onScrolled() : scrollY - "+scrollY);
        int diff = mPrevScrollY - scrollY;
//        Log.d("", "onScrolled() : diff - "+diff);

        if(diff != 0){
            switch (mQuickReturnViewType){
                case HEADER:
                    if(diff < 0){ // scrolling down
                        mHeaderDiffTotal = Math.max(mHeaderDiffTotal + diff, mMinHeaderTranslation);
                    } else { // scrolling up
                        mHeaderDiffTotal = Math.min(Math.max(mHeaderDiffTotal + diff, mMinHeaderTranslation), 0);
                    }

                    mHeader.setTranslationY(mHeaderDiffTotal);
                    break;
                case FOOTER:
                    if(diff < 0){ // scrolling down
                        mFooterDiffTotal = Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation);
                    } else { // scrolling up
                        mFooterDiffTotal = Math.min(Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation), 0);
                    }

                    mFooter.setTranslationY(-mFooterDiffTotal);
                    break;
                case BOTH:
                    if(diff < 0){ // scrolling down
                        mHeaderDiffTotal = Math.max(mHeaderDiffTotal + diff, mMinHeaderTranslation);
                        mFooterDiffTotal = Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation);
                    } else { // scrolling up
                        mHeaderDiffTotal = Math.min(Math.max(mHeaderDiffTotal + diff, mMinHeaderTranslation), 0);
                        mFooterDiffTotal = Math.min(Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation), 0);
                    }

                    mHeader.setTranslationY(mHeaderDiffTotal);
                    mFooter.setTranslationY(-mFooterDiffTotal);
                    break;
                case TWITTER:
                    if(diff < 0){ // scrolling down
                        if(scrollY > -mMinHeaderTranslation)
                            mHeaderDiffTotal = Math.max(mHeaderDiffTotal + diff, mMinHeaderTranslation);

                        if(scrollY > mMinFooterTranslation)
                            mFooterDiffTotal = Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation);
                    } else { // scrolling up
                        mHeaderDiffTotal = Math.min(Math.max(mHeaderDiffTotal + diff, mMinHeaderTranslation), 0);
                        mFooterDiffTotal = Math.min(Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation), 0);
                    }

                    mHeader.setTranslationY(mHeaderDiffTotal);
                    mFooter.setTranslationY(-mFooterDiffTotal);
                default:
                    break;
            }
        }

        mPrevScrollY = scrollY;
    }

    // region Helper Methods
    public void registerExtraOnScrollListener(RecyclerView.OnScrollListener listener) {
        mExtraOnScrollListenerList.add(listener);
    }
    // endregion
    
    // region Inner Classes
    
    public static class Builder {
        // Required parameters
        private final QuickReturnViewType mQuickReturnViewType;

        // Optional parameters - initialized to default values
        private View mHeader = null;
        private int mMinHeaderTranslation = 0;
        private View mFooter = null;
        private int mMinFooterTranslation = 0;
        private int mColumnCount = 1;
        private boolean mIsSnappable = false;

        public Builder(QuickReturnViewType quickReturnViewType) {
            mQuickReturnViewType = quickReturnViewType;
        }

        public Builder header(View header){
            mHeader = header;
            return this;
        }

        public Builder minHeaderTranslation(int minHeaderTranslation){
            mMinHeaderTranslation = minHeaderTranslation;
            return this;
        }

        public Builder footer(View footer){
            mFooter = footer;
            return this;
        }

        public Builder minFooterTranslation(int minFooterTranslation){
            mMinFooterTranslation = minFooterTranslation;
            return this;
        }

        public Builder columnCount(int columnCount){
            mColumnCount = columnCount;
            return this;
        }

        public Builder isSnappable(boolean isSnappable){
            mIsSnappable = isSnappable;
            return this;
        }

        public QuickReturnRecyclerViewOnScrollListener build() {
            return new QuickReturnRecyclerViewOnScrollListener(this);
        }
    }

    // endregion
}
