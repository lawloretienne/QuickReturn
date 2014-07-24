package com.etiennelawlor.quickreturn.listeners;

import android.util.Log;
import android.view.View;
import android.widget.AbsListView;

import com.etiennelawlor.quickreturn.enums.QuickReturnType;
import com.etiennelawlor.quickreturn.utils.QuickReturnUtils;

/**
 * Created by etiennelawlor on 7/10/14.
 */
public class QuickReturnListViewOnScrollListener implements AbsListView.OnScrollListener {

    // region Member Variables
    private int mMinFooterTranslation;
    private int mMinHeaderTranslation;
    private int mPrevScrollY = 0;
    private int mHeaderDiffTotal = 0;
    private int mFooterDiffTotal = 0;
    private View mHeader;
    private View mFooter;
    private QuickReturnType mQuickReturnType;
    private boolean mCanSlideInIdleScrollState = false;
    // endregion

    // region Constructors
    public QuickReturnListViewOnScrollListener(QuickReturnType quickReturnType, View headerView, int headerTranslation, View footerView, int footerTranslation){
        mQuickReturnType = quickReturnType;
        mHeader =  headerView;
        mMinHeaderTranslation = headerTranslation;
        mFooter =  footerView;
        mMinFooterTranslation = footerTranslation;
    }
    // endregion

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        Log.d(getClass().getSimpleName(), "onScrollStateChanged() : scrollState - "+scrollState);

        if(scrollState == SCROLL_STATE_IDLE && mCanSlideInIdleScrollState){

            int midHeader = -mMinHeaderTranslation/2;
            int midFooter = mMinFooterTranslation/2;

            switch (mQuickReturnType) {
                case HEADER:
                    if (-mHeaderDiffTotal > 0 && -mHeaderDiffTotal < midHeader) {
                        mHeader.setTranslationY(0);
                        mHeaderDiffTotal = 0;
                    } else if (-mHeaderDiffTotal < -mMinHeaderTranslation && -mHeaderDiffTotal >= midHeader) {
                        mHeader.setTranslationY(mMinHeaderTranslation);
                        mHeaderDiffTotal = mMinHeaderTranslation;
                    }
                    break;
                case FOOTER:
                    if (-mFooterDiffTotal > 0 && -mFooterDiffTotal < midFooter) { // slide up
                        mFooter.setTranslationY(0);
                        mFooterDiffTotal = 0;
                    } else if (-mFooterDiffTotal < mMinFooterTranslation && -mFooterDiffTotal >= midFooter) { // slide down
                        mFooter.setTranslationY(mMinFooterTranslation);
                        mFooterDiffTotal = -mMinFooterTranslation;
                    }
                    break;
                case BOTH:
                    if (-mHeaderDiffTotal > 0 && -mHeaderDiffTotal < midHeader) {
                        mHeader.setTranslationY(0);
                        mHeaderDiffTotal = 0;
                    } else if (-mHeaderDiffTotal < -mMinHeaderTranslation && -mHeaderDiffTotal >= midHeader) {
                        mHeader.setTranslationY(mMinHeaderTranslation);
                        mHeaderDiffTotal = mMinHeaderTranslation;
                    }

                    if (-mFooterDiffTotal > 0 && -mFooterDiffTotal < midFooter) { // slide up
                        mFooter.setTranslationY(0);
                        mFooterDiffTotal = 0;
                    } else if (-mFooterDiffTotal < mMinFooterTranslation && -mFooterDiffTotal >= midFooter) { // slide down
                        mFooter.setTranslationY(mMinFooterTranslation);
                        mFooterDiffTotal = -mMinFooterTranslation;
                    }
                    break;
            }

        }
    }

    @Override
    public void onScroll(AbsListView listview, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int scrollY = QuickReturnUtils.getScrollY(listview);
        int diff = mPrevScrollY - scrollY;

//        Log.d(getClass().getSimpleName(), "onScroll() : scrollY - "+scrollY);
//        Log.d(getClass().getSimpleName(), "onScroll() : diff - "+diff);


        if(diff != 0){
            switch (mQuickReturnType){
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
                default:
                    break;
            }
        }

        mPrevScrollY = scrollY;
    }

    public void setCanSlideInIdleScrollState(boolean canSlideInIdleScrollState){
        mCanSlideInIdleScrollState = canSlideInIdleScrollState;
    }
}
