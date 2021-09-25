package com.hackatonwhoandroid.utils.base.presentation.adapter;

import androidx.recyclerview.widget.DiffUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;

@SuppressWarnings({"WeakerAccess", "unused"})
@AllArgsConstructor
public class ListsDiffUtilCallback<ItemT> extends DiffUtil.Callback {

    protected final List<ItemT> oldList;
    protected final List<ItemT> newList;

    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    public List<ItemT> getOldList() {
        return oldList != null ? oldList : new ArrayList<>();
    }

    public List<ItemT> getNewItems() {
        return newList != null ? newList : new ArrayList<>();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.deepEquals(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }
}
