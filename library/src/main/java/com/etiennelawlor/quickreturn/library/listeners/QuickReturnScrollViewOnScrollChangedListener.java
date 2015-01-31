package com.etiennelawlor.quickreturn.library.listeners;

import android.view.View;
import android.widget.ScrollView;

import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.views.NotifyingScrollView;

/**
 * Created by etiennelawlor on 7/11/14.
 */
public class QuickReturnScrollViewOnScrollChangedListener implements NotifyingScrollView.OnScrollChangedListener {

    // region Member Variables
    private int mMinFooterTranslation;
    private int mMinHeaderTranslation;
    private int mHeaderDiffTotal = 0;
    private int mFooterDiffTotal = 0;
    private View mHeader;
    private View mFooter;
    private QuickReturnViewType mQuickReturnViewType;
    // endregion

    // region Constructors
    public QuickReturnScrollViewOnScrollChangedListener(QuickReturnViewType quickReturnViewType, View headerView, int headerTranslation, View footerView, int footerTranslation){
        mQuickReturnViewType = quickReturnViewType;
        mHeader =  headerView;
        mMinHeaderTranslation = headerTranslation;
        mFooter =  footerView;
        mMinFooterTranslation = footerTranslation;
    }
    // endregion


    @Override
    public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
        int diff = oldt - t;

        switch (mQuickReturnViewType){
            case HEADER:
                if(diff <=0){ // scrolling down
                    mHeaderDiffTotal = Math.max(mHeaderDiffTotal+diff, mMinHeaderTranslation);
                } else { // scrolling up
                    mHeaderDiffTotal = Math.min(Math.max(mHeaderDiffTotal+diff, mMinHeaderTranslation), 0);
                }

                mHeader.setTranslationY(mHeaderDiffTotal);
                break;
            case FOOTER:
                if(diff <=0){ // scrolling down
                    mFooterDiffTotal = Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation);
                } else { // scrolling up
                    mFooterDiffTotal = Math.min(Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation), 0);
                }

                mFooter.setTranslationY(-mFooterDiffTotal);
                break;
            case BOTH:
                if(diff <=0){ // scrolling down
                    mHeaderDiffTotal = Math.max(mHeaderDiffTotal+diff, mMinHeaderTranslation);
                    mFooterDiffTotal = Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation);
                } else { // scrolling up
                    mHeaderDiffTotal = Math.min(Math.max(mHeaderDiffTotal+diff, mMinHeaderTranslation), 0);
                    mFooterDiffTotal = Math.min(Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation), 0);
                }

                mHeader.setTranslationY(mHeaderDiffTotal);
                mFooter.setTranslationY(-mFooterDiffTotal);
                break;
        }
    }
}
