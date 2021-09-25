package com.hackatonwhoandroid.utils.base.presentation.adapter;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseDiffRecyclerAdapter<ItemT> extends BindingRecyclerDiffAdapter<ItemT> {

    protected BaseDiffRecyclerAdapter(@NonNull DiffUtil.ItemCallback<ItemT> diffCallback) {
        super(diffCallback);
    }

    protected BaseDiffRecyclerAdapter(@NonNull AsyncDifferConfig<ItemT> config) {
        super(config);
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("submitList")
    public static void setSubmitList(RecyclerView recyclerView, List<?> items) {
        if (recyclerView != null && recyclerView.getAdapter() instanceof BaseDiffRecyclerAdapter) {
            ((BaseDiffRecyclerAdapter) recyclerView.getAdapter()).submitList(items);
        }
    }

}
