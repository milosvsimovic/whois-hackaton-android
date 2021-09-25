package com.hackatonwhoandroid.presentation.chat;

import androidx.lifecycle.MutableLiveData;

import com.hackatonwhoandroid.utils.ErrorHandler;
import com.hackatonwhoandroid.utils.base.presentation.viewmodel.BaseViewModel;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatItemViewModel extends BaseViewModel<ChatItemViewModel.ActionCode> {

    private final MutableLiveData<MessageModel> message = new MutableLiveData<>();

    @Inject
    ErrorHandler errorHandler;

    @Inject
    public ChatItemViewModel() {
    }

    public void setValue(MessageModel item) {
        this.message.setValue(item);
    }

    public void onClick() {
        dispatchAction(ActionCode.CLICK, message.getValue());
    }

    public enum ActionCode {
        CLICK, ERROR
    }

}