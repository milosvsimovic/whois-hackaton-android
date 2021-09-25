package com.hackatonwhoandroid.utils.base.presentation.viewmodel;

public class ViewModelIdProvider {

    private static Integer viewModelId;

    public static int getViewModelId() {
        if (viewModelId == null) {
            throw new RuntimeException("ViewModelIdProvider not initialized");
        }
        return viewModelId;
    }

    public static void setViewModelId(int viewModelId) {
        ViewModelIdProvider.viewModelId = viewModelId;
    }

}