package com.hackatonwhoandroid.utils.base.presentation.adapter;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class BaseRecyclerAdapter<ItemT> extends BindingRecyclerAdapter {

    private List<ItemT> items;

    public BaseRecyclerAdapter() {
        this.items = new ArrayList<>();
    }

    public BaseRecyclerAdapter(List<ItemT> items) {
        if (items == null) {
            this.items = new ArrayList<>();
        } else {
            this.items = items;
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    private boolean isPositionValid(int position) {
        return position >= 0 && position < getItemCount();
    }

    public void setItems(List<ItemT> items) {
        this.items = items == null ? new ArrayList<>() : new ArrayList<>(items);
        notifyDataSetChanged();
    }

    public List<ItemT> getAll() {
        return items;
    }

    public ItemT get(int position) {
        return isPositionValid(position) ? items.get(position) : null;
    }

    public void add(ItemT item) {
        if (item != null) {
            items.add(item);
            notifyItemInserted(getItemCount() - 1);
        }
    }

    public void addAll(Collection<ItemT> items) {
        if (items != null && items.size() > 0) {
            this.items.addAll(items);
            notifyItemRangeInserted(getItemCount() - items.size(), items.size());
        }
    }

    public void insert(int position, ItemT item) {
        if (position >= 0 && position <= getItemCount() && item != null) {
            items.add(position, item);
            notifyItemInserted(position);
        }
    }

    public void update(int position, ItemT item) {
        if (isPositionValid(position) && item != null) {
            items.set(position, item);
            notifyItemChanged(position);
        }
    }

    public ItemT remove(int position) {
        ItemT removed = null;
        if (isPositionValid(position)) {
            removed = items.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
        return removed;
    }

    public void clear() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public void updateDiffList(ListsDiffUtilCallback<ItemT> diffUtilCallback) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback);
        getAll().clear();
        getAll().addAll(diffUtilCallback.getNewItems());
        diffResult.dispatchUpdatesTo(this);
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("list")
    public static void setList(RecyclerView recyclerView, List<?> items) {
        if (recyclerView != null && recyclerView.getAdapter() instanceof BaseRecyclerAdapter && items != null) {
            ((BaseRecyclerAdapter) recyclerView.getAdapter()).setItems(items);
        }
    }
}