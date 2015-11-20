package com.etiennelawlor.quickreturn.itemdecorations;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by etiennelawlor on 1/24/15.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    // region Member Variables
    private int mSpace;
    // endregion

    // region Constructors
    public SpacesItemDecoration(int space) {
        this.mSpace = space;
    }
    // endregion

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int childPosition = parent.getChildAdapterPosition(view);
        if (childPosition == 0) {
            outRect.left = mSpace;
            outRect.right = mSpace;
            outRect.top = mSpace;
            outRect.bottom = mSpace / 2;
        } else if (childPosition == parent.getLayoutManager().getItemCount() - 1) {
            outRect.left = mSpace;
            outRect.right = mSpace;
            outRect.top = mSpace / 2;
            outRect.bottom = mSpace;
        } else {
            outRect.left = mSpace;
            outRect.right = mSpace;
            outRect.top = mSpace / 2;
            outRect.bottom = mSpace / 2;
        }
    }
}