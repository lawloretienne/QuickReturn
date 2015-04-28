package com.etiennelawlor.quickreturn.library.listeners;

import android.view.View;
import android.webkit.WebView;

import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.views.NotifyingWebView;

/**
 * Created by etiennelawlor on 7/11/14.
 */
public class QuickReturnWebViewOnScrollChangedListener implements NotifyingWebView.OnScrollChangedListener {

    // region Member Variables
    private final QuickReturnViewType mQuickReturnViewType;
    private final View mHeader;
    private final int mMinHeaderTranslation;
    private final View mFooter;
    private final int mMinFooterTranslation;

    private int mHeaderDiffTotal = 0;
    private int mFooterDiffTotal = 0;
    // endregion

    // region Constructors
    private QuickReturnWebViewOnScrollChangedListener(Builder builder) {
        mQuickReturnViewType = builder.mQuickReturnViewType;
        mHeader = builder.mHeader;
        mMinHeaderTranslation = builder.mMinHeaderTranslation;
        mFooter = builder.mFooter;
        mMinFooterTranslation = builder.mMinFooterTranslation;
    }
    // endregion

    @Override
    public void onScrollChanged(WebView who, int l, int t, int oldl, int oldt) {
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

    // region Inner Classes

    public static class Builder {
        // Required parameters
        private final QuickReturnViewType mQuickReturnViewType;

        // Optional parameters - initialized to default values
        private View mHeader = null;
        private int mMinHeaderTranslation = 0;
        private View mFooter = null;
        private int mMinFooterTranslation = 0;

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

        public QuickReturnWebViewOnScrollChangedListener build() {
            return new QuickReturnWebViewOnScrollChangedListener(this);
        }
    }

    // endregion
}
