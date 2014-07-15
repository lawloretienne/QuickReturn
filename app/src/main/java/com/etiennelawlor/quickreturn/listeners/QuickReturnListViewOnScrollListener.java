package com.etiennelawlor.quickreturn.listeners;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
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
    private TranslateAnimation mFooterAnim;
    private TranslateAnimation mHeaderAnim;
    private View mHeader;
    private View mFooter;
    private QuickReturnType mQuickReturnType;
    // endregion

    // region Constructors
    public QuickReturnListViewOnScrollListener(QuickReturnType quickReturnType, View headerView, int headerTranslation, View footerView, int footerTranslation){
        mQuickReturnType = quickReturnType;

        switch (mQuickReturnType){
            case HEADER:
                mHeader =  headerView;
                mMinHeaderTranslation = headerTranslation;
                break;
            case FOOTER:
                mFooter =  footerView;
                mMinFooterTranslation = footerTranslation;
            case BOTH:
                mHeader =  headerView;
                mMinHeaderTranslation = headerTranslation;
                mFooter =  footerView;
                mMinFooterTranslation = footerTranslation;
            default:
                break;
        }
    }
    // endregion

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE){
            Log.d(getClass().getSimpleName(), "onScrollStateChanged() : IDLE : mPrevScrollY - "+mPrevScrollY);

            int midHeader = -mMinHeaderTranslation/2;
            Log.d(getClass().getSimpleName(), "onScrollStateChanged() : IDLE : midHeader - "+midHeader);

            if(mPrevScrollY < midHeader){
                Log.d(getClass().getSimpleName(), "onScrollStateChanged() : slide header down");
            } else if(mPrevScrollY<-mMinHeaderTranslation && mPrevScrollY >= midHeader){
                Log.d(getClass().getSimpleName(), "onScrollStateChanged() : slide header up");
            }
            Log.d(getClass().getSimpleName(), "onScrollStateChanged() : IDLE : mMinHeaderTranslation - "+mMinHeaderTranslation);
            Log.d(getClass().getSimpleName(), "onScrollStateChanged() : IDLE : mMinFooterTranslation - "+mMinFooterTranslation);

        }

    }

    @Override
    public void onScroll(AbsListView listview, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int scrollY = QuickReturnUtils.getScrollY(listview);
        int diff = mPrevScrollY - scrollY;

        switch (mQuickReturnType){
            case HEADER:
                if(diff <=0){ // scrolling down
                    mHeaderDiffTotal = Math.max(mHeaderDiffTotal + diff, mMinHeaderTranslation);
                } else { // scrolling up
                    mHeaderDiffTotal = Math.min(Math.max(mHeaderDiffTotal + diff, mMinHeaderTranslation), 0);
                }

                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                    mHeaderAnim = new TranslateAnimation(0, 0, mHeaderDiffTotal,
                            mHeaderDiffTotal);
                    mHeaderAnim.setFillAfter(true);
                    mHeaderAnim.setDuration(0);
                    mHeader.startAnimation(mHeaderAnim);
                } else {
                    mHeader.setTranslationY(mHeaderDiffTotal);
                }
                break;
            case FOOTER:
                if(diff <=0){ // scrolling down
                    mFooterDiffTotal = Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation);
                } else { // scrolling up
                    mFooterDiffTotal = Math.min(Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation), 0);
                }

                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                    mFooterAnim = new TranslateAnimation(0, 0, -mFooterDiffTotal,
                            -mFooterDiffTotal);
                    mFooterAnim.setFillAfter(true);
                    mFooterAnim.setDuration(0);
                    mFooter.startAnimation(mFooterAnim);
                } else {
                    mFooter.setTranslationY(-mFooterDiffTotal);
                }
                break;
            case BOTH:
                if(diff <=0){ // scrolling down
                    mHeaderDiffTotal = Math.max(mHeaderDiffTotal + diff, mMinHeaderTranslation);
                    mFooterDiffTotal = Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation);
                } else { // scrolling up
                    mHeaderDiffTotal = Math.min(Math.max(mHeaderDiffTotal + diff, mMinHeaderTranslation), 0);
                    mFooterDiffTotal = Math.min(Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation), 0);
                }

                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                    mHeaderAnim = new TranslateAnimation(0, 0, mHeaderDiffTotal,
                            mHeaderDiffTotal);
                    mHeaderAnim.setFillAfter(true);
                    mHeaderAnim.setDuration(0);
                    mHeader.startAnimation(mHeaderAnim);

                    mFooterAnim = new TranslateAnimation(0, 0, -mFooterDiffTotal,
                            -mFooterDiffTotal);
                    mFooterAnim.setFillAfter(true);
                    mFooterAnim.setDuration(0);
                    mFooter.startAnimation(mFooterAnim);
                } else {
                    mHeader.setTranslationY(mHeaderDiffTotal);
                    mFooter.setTranslationY(-mFooterDiffTotal);
                }
            default:
                break;
        }

        mPrevScrollY = scrollY;
    }
}
