package com.hackatonwhoandroid.utils.base.presentation.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.hackatonwhoandroid.utils.base.di.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

abstract class BindingRecyclerAdapter extends RecyclerView.Adapter<BindingViewHolder> {

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @NonNull
    @Override
    public final BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        if (parent.getContext() instanceof LifecycleOwner) {
            dataBinding.setLifecycleOwner((LifecycleOwner) parent.getContext());
        }
        return new BindingViewHolder(dataBinding);
    }

    @Override
    public final void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
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
