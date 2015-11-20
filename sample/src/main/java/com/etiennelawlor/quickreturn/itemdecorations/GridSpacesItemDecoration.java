package com.etiennelawlor.quickreturn.itemdecorations;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by etiennelawlor on 1/31/15.
 */
public class GridSpacesItemDecoration extends RecyclerView.ItemDecoration {

    // region Member Variables
    private int mSpace;
    // endregion

    // region Constructors
    public GridSpacesItemDecoration(int space) {
        this.mSpace = space;
    }
    // endregion

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int childPosition = parent.getChildAdapterPosition(view);
        if (childPosition == 0 || childPosition == 1) {
            outRect.top = mSpace;
        }

        if (childPosition % 2 == 0) {
            outRect.left = mSpace;
            outRect.right = mSpace / 2;
        } else {
            outRect.left = mSpace / 2;
            outRect.right = mSpace;
        }

        outRect.bottom = mSpace;
    }
}
