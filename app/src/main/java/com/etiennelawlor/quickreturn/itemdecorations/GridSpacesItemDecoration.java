package com.etiennelawlor.quickreturn.itemdecorations;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by etiennelawlor on 1/31/15.
 */
public class GridSpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public GridSpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildPosition(view) == 0 || parent.getChildPosition(view) == 1){
            outRect.top = space;
        }
        
        if(parent.getChildPosition(view) % 2 == 0){
            outRect.left = space;
            outRect.right = space/2;
        } else {
            outRect.left = space/2;
            outRect.right = space;
        }

        outRect.bottom = space;
    }
}
