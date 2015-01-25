package com.etiennelawlor.quickreturn.itemdecorations;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by etiennelawlor on 1/24/15.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(parent.getChildPosition(view) == 0){
            outRect.left = space;
            outRect.right = space;
            outRect.top = space;
            outRect.bottom = space/2;
        } else if (parent.getChildPosition(view) == parent.getChildCount()-1){
            outRect.left = space;
            outRect.right = space;
            outRect.top = space/2;
            outRect.bottom = space;
        } else {
            outRect.left = space;
            outRect.right = space;
            outRect.top = space/2;
            outRect.bottom = space/2;
        }
    }
}