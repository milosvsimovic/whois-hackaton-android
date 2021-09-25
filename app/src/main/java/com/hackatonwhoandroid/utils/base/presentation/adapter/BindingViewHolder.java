package com.hackatonwhoandroid.utils.base.presentation.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.hackatonwhoandroid.utils.base.presentation.viewmodel.ViewModelIdProvider;

import java.util.Objects;

@SuppressWarnings({"WeakerAccess", "unused"})
public class BindingViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding viewDataBinding;

    public BindingViewHolder(ViewDataBinding viewDataBinding) {
        super(viewDataBinding.getRoot());
        this.viewDataBinding = viewDataBinding;
    }

    public ViewDataBinding getBinding() {
        return viewDataBinding;
    }

    @NonNull
    public Context getContext() {
        return Objects.requireNonNull(getBinding().getRoot().getContext());
    }

    @NonNull
    public LifecycleOwner getLifecycleOwner() {
        return Objects.requireNonNull(getBinding().getLifecycleOwner());
    }

    public void bindViewModel(ViewModel viewModel) {
        viewDataBinding.setVariable(ViewModelIdProvider.getViewModelId(), viewModel);
        viewDataBinding.executePendingBindings();
    }

}
