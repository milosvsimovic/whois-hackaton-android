package com.hackatonwhoandroid.presentation.chat;

import static com.hackatonwhoandroid.presentation.chat.ChatAdapter.ActionCode.ON_ITEM_CLICK;
import static com.hackatonwhoandroid.presentation.chat.ChatAdapter.ActionCode.ON_ITEM_ADDED;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.hackatonwhoandroid.R;
import com.hackatonwhoandroid.utils.base.presentation.IActionListener;
import com.hackatonwhoandroid.utils.base.presentation.adapter.BaseRecyclerAdapter;
import com.hackatonwhoandroid.utils.base.presentation.adapter.BindingViewHolder;
import com.hackatonwhoandroid.utils.base.presentation.viewmodel.ActionProvider;

import java.util.List;

import javax.inject.Inject;

public class ChatAdapter extends BaseRecyclerAdapter<MessageModel> {

    private IActionListener<ActionCode> listener;

    @Inject
    ActionProvider actionProvider;

    @Inject
    ChatAdapter() {
    }

    public void setListener(IActionListener<ActionCode> listener) {
        this.listener = listener;
    }

    @Override
    public int provideItemLayoutIdForPosition(int position) {
        if (get(position).isCreatedByUser()) {
            return R.layout.item_outgoing_message_text;
        }
        return R.layout.item_incoming_message_text;
    }

    @NonNull
    @Override
    public ViewModel provideItemViewModelForPosition(@NonNull BindingViewHolder viewHolder, int position) {
        ChatItemViewModel viewModel = createViewModel(ChatItemViewModel.class);

        if (getItemCount() > 0) {
            viewModel.setValue(get(position));
            viewModel.getActions().observe(viewHolder.getLifecycleOwner(), action -> {

                switch (action.getCode()) {
                    case CLICK:
                        MessageModel messageModel = (MessageModel) action.getData();
                        listener.onAction(actionProvider.provide(ON_ITEM_CLICK, messageModel));
                        break;
                }
            });
        }
        return viewModel;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void setItems(List<MessageModel> items) {
        if (getItemCount() == 0) {
            super.setItems(items);
        } else {
            // state update for one or more elements
            if(items.size() == getItemCount()){
                notifyDataSetChanged();
            }else{
                // add or remove element
                MessageModel lastMessage = get(getItemCount() - 1);

                for (MessageModel element : items) {
                    if (lastMessage.getTimeStamp().isBefore(element.getTimeStamp())) {
                        add(element);
                        listener.onAction(actionProvider.provide(ON_ITEM_ADDED, getItemCount() - 1));
                    }
                }
            }
        }
    }

    public enum ActionCode {
        ON_ITEM_ADDED, ON_ITEM_CLICK
    }

}
