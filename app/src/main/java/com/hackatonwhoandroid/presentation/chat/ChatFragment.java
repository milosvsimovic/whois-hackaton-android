package com.hackatonwhoandroid.presentation.chat;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.hackatonwhoandroid.R;
import com.hackatonwhoandroid.databinding.FragmentChatBinding;
import com.hackatonwhoandroid.utils.SpeedyLinearLayoutManager;
import com.hackatonwhoandroid.utils.base.presentation.BaseFragment;
import com.hackatonwhoandroid.utils.base.presentation.IActionListener;
import com.hackatonwhoandroid.utils.base.presentation.viewmodel.ActionProvider;

import javax.inject.Inject;

import lombok.Setter;

@Setter
public class ChatFragment extends BaseFragment<FragmentChatBinding, ChatViewModel> {

    public static ChatFragment newInstance(IActionListener<ActionCode> listener) {
        ChatFragment fragment = new ChatFragment();
        fragment.setListener(listener);
        return fragment;
    }

    @Inject
    ChatAdapter adapter;

    @Inject
    ActionProvider actionProvider;

    private IActionListener<ActionCode> listener;

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycler();
        getViewModel().getActions().observe(getViewLifecycleOwner(), action -> {
            switch (action.getCode()) {
                case ON_DOMAIN_SUBMIT:
                    getViewModel().selectDomainMessage(null);
                    getViewDataBinding().layoutDomainActions.setVisibility(View.GONE);
                    break;
                case ON_DOMAIN_RESPONSE:
                    getViewDataBinding().layoutDomainActions.setVisibility(View.VISIBLE);
                    break;
                case ERROR:
                    break;
            }
        });
        getViewModel().initMessages();
        getViewModel().sendInitialMessage();
    }

    public void handleFavoritesButtonToggle(boolean show) {
        getViewModel().showFavorites(show);
    }

    private void initRecycler() {
        adapter.setListener(action -> {
            switch (action.getCode()) {
                case ON_LIST_ADDED:
                case ON_ITEM_ADDED:
                    RecyclerView.LayoutManager layoutManager = getViewDataBinding().recyclerMessages.getLayoutManager();
                    if (layoutManager != null) {
                        layoutManager.scrollToPosition((int) action.getData());
                    }
                    break;
                case ON_LIST_UPDATED:
                    getViewModel().selectDomainMessage(null);
                    getViewDataBinding().layoutDomainActions.setVisibility(View.GONE);
                    break;
                case ON_FULL_LIST:
                    getViewModel().selectDomainMessage(null);
                    getViewDataBinding().layoutDomainActions.setVisibility(View.GONE);
                    RecyclerView.LayoutManager lm = getViewDataBinding().recyclerMessages.getLayoutManager();
                    if (lm != null) {
                        lm.scrollToPosition((int) action.getData());
                    }
                    break;
                case ON_ITEM_CLICK:
                    MessageModel messageModel = (MessageModel) action.getData();
                    if (messageModel.getType().isClickable()) {
                        getViewModel().selectDomainMessage(messageModel);
                        getViewDataBinding().layoutDomainActions.setVisibility(View.VISIBLE);
                    } else {
                        getViewModel().selectDomainMessage(null);
                        getViewDataBinding().layoutDomainActions.setVisibility(View.GONE);
                    }
                    break;
            }
        });
        getViewDataBinding().recyclerMessages.setAdapter(adapter);
        getViewDataBinding().recyclerMessages.setLayoutManager(new SpeedyLinearLayoutManager(getViewDataBinding().recyclerMessages.getContext(), SpeedyLinearLayoutManager.VERTICAL, false));
        int dividerHeight = (int) getViewDataBinding().recyclerMessages.getResources().getDimension(R.dimen.message_divider_height);
        getViewDataBinding().recyclerMessages.addItemDecoration(new VerticalSpaceItemDecoration(dividerHeight));
    }

    public enum ActionCode {
        ON_LINE_CLICK
    }

}