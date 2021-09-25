package com.hackatonwhoandroid.presentation.chat;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private static final int VERTICAL_ITEM_SPACE = 48;

    private final int verticalSpaceHeight;

    public VerticalSpaceItemDecoration() {
        verticalSpaceHeight = VERTICAL_ITEM_SPACE;
    }

    public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = verticalSpaceHeight;
    }
}