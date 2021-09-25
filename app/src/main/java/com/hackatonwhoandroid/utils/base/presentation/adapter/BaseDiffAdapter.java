package com.hackatonwhoandroid.utils.base.presentation.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.hackatonwhoandroid.utils.base.di.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

public abstract class BaseDiffAdapter<ItemT> extends ListAdapter<ItemT, BindingViewHolder> {

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    protected BaseDiffAdapter(@NonNull DiffUtil.ItemCallback<ItemT> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public final BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false));
    }

    @Override
    public final void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bindViewModel(provideViewModel(holder, position));
    }

    @Override
    public final int getItemViewType(int position) {
        return provideLayoutId(position);
    }

    /**
     * position
     * This method should return the layout id of an item depending on the position.
     * Different layout id for different view type is returned.
     *
     * @param position of item.
     * @return layout id.
     */
    @LayoutRes
    public abstract int provideLayoutId(int position);

    /**
     * This method provides viewmodel instance depending on the position.
     *
     * @param position of item.
     * @return viewmodel to be bound.
     */
    @NonNull
    public abstract ViewModel provideViewModel(@NonNull BindingViewHolder viewHolder, int position);

    protected <T extends ViewModel> T createViewModel(@NonNull Class<T> viewModelClass) {
        return viewModelProviderFactory.create(viewModelClass);
    }

}
