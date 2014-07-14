package com.etiennelawlor.quickreturn.listeners;

import android.os.Build;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.etiennelawlor.quickreturn.enums.QuickReturnType;
import com.etiennelawlor.quickreturn.views.NotifyingScrollView;

/**
 * Created by etiennelawlor on 7/11/14.
 */
public class QuickReturnScrollViewOnScrollChangedListener implements  NotifyingScrollView.OnScrollChangedListener {

    // region Member Variables
    private int mMinFooterTranslation;
    private int mMinHeaderTranslation;
    private int mHeaderDiffTotal = 0;
    private int mFooterDiffTotal = 0;
    private TranslateAnimation mFooterAnim;
    private TranslateAnimation mHeaderAnim;
    private View mHeader;
    private View mFooter;
    private QuickReturnType mQuickReturnType;
    // endregion

    // region Constructors
    public QuickReturnScrollViewOnScrollChangedListener(QuickReturnType quickReturnType, View headerView, int headerTranslation, View footerView, int footerTranslation){
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
    public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
        int diff = oldt - t;

        switch (mQuickReturnType){
            case HEADER:
                if(diff <=0){ // scrolling down
                    mHeaderDiffTotal = Math.max(mHeaderDiffTotal+diff, mMinHeaderTranslation);
                } else { // scrolling up
                    mHeaderDiffTotal = Math.min(Math.max(mHeaderDiffTotal+diff, mMinHeaderTranslation), 0);
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
                    mHeaderDiffTotal = Math.max(mHeaderDiffTotal+diff, mMinHeaderTranslation);
                    mFooterDiffTotal = Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation);
                } else { // scrolling up
                    mHeaderDiffTotal = Math.min(Math.max(mHeaderDiffTotal+diff, mMinHeaderTranslation), 0);
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
                break;
        }
    }
}
