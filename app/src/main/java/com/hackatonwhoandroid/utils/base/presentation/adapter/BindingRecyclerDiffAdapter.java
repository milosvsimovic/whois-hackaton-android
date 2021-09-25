package com.hackatonwhoandroid.utils.base.presentation.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.hackatonwhoandroid.utils.base.di.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

public abstract class BindingRecyclerDiffAdapter<ItemT> extends ListAdapter<ItemT, BindingViewHolder> {

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    protected BindingRecyclerDiffAdapter(@NonNull DiffUtil.ItemCallback<ItemT> diffCallback) {
        super(diffCallback);
    }

    protected BindingRecyclerDiffAdapter(@NonNull AsyncDifferConfig<ItemT> config) {
        super(config);
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        if (parent.getContext() instanceof LifecycleOwner) {
            dataBinding.setLifecycleOwner((LifecycleOwner) parent.getContext());
        }
        return new BindingViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bindViewModel(provideItemViewModelForPosition(holder, position));
    }

    @Override
    public final int getItemViewType(int position) {
        return provideItemLayoutIdForPosition(position);
    }

    @LayoutRes
    public abstract int provideItemLayoutIdForPosition(int position);

    @NonNull
    public abstract ViewModel provideItemViewModelForPosition(@NonNull BindingViewHolder viewHolder, int position);

    protected <T extends ViewModel> T createViewModel(@NonNull Class<T> viewModelClass) {
        return viewModelProviderFactory.create(viewModelClass);
    }

}
