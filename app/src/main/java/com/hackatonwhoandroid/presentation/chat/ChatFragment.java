package com.hackatonwhoandroid.presentation.chat;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hackatonwhoandroid.R;
import com.hackatonwhoandroid.databinding.FragmentChatBinding;
import com.hackatonwhoandroid.domain.model.Message;
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
        getViewModel().getActions().observe(getViewLifecycleOwner(), actionCodeAction -> {
            switch (actionCodeAction.getCode()) {
                case ON_DOMAIN_SUBMIT:
                    getViewModel().selectDomainMessage(null);
                    getViewDataBinding().layoutDomainActions.setVisibility(View.GONE);
                    break;
                case ERROR:
                    break;
            }
        });
    }

    private void initRecycler() {
        adapter.setListener(action -> {
            switch (action.getCode()) {
                case ON_ITEM_ADDED:
                    getViewDataBinding().recycler.getLayoutManager().scrollToPosition((int) action.getData());
                    break;
                case ON_ITEM_CLICK:
                    MessageModel messageModel = (MessageModel) action.getData();
                    if (messageModel.getType() == Message.Type.DOMAIN) {
                        getViewModel().selectDomainMessage(messageModel);
                        getViewDataBinding().layoutDomainActions.setVisibility(View.VISIBLE);
                    } else {
                        getViewModel().selectDomainMessage(null);
                        getViewDataBinding().layoutDomainActions.setVisibility(View.GONE);
                    }
                    break;
            }
        });
        getViewDataBinding().recycler.setAdapter(adapter);
        int dividerHeight = (int) getViewDataBinding().recycler.getResources().getDimension(R.dimen.message_divider_height);
        getViewDataBinding().recycler.addItemDecoration(new VerticalSpaceItemDecoration(dividerHeight));
    }


//    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context ctx, Intent intent) {
//            if (Objects.equals(Intent.ACTION_TIME_TICK, intent.getAction())) {
//                getViewModel().getLines();
//            }
//        }
//    };

    //
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        initToolbar();
//        initRecycler(view.getContext());
//        getViewModel().syncData();
//        observeAction();
//        getViewModel().getLines();
//    }

//    private void initRecycler(Context context) {
//        adapter.setListener(this::onItemAction);
//        getViewDataBinding().recycler.setAdapter(adapter);
//        getViewDataBinding().recycler.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
//        SimpleItemAnimator itemAnimator = (SimpleItemAnimator) getViewDataBinding().recycler.getItemAnimator();
//        if (itemAnimator != null) {
//            itemAnimator.setSupportsChangeAnimations(false);
//        }
//    }
//
//    private void observeAction() {
//        getViewModel().getActions().observe(getViewLifecycleOwner(), action -> {
//            switch (action.getCode()) {
//
//                case ERROR:
//                    Toaster.showToast((String) action.getData());
//                    stopRefreshing();
//                    break;
//
//                case ERROR_NETWORK:
//                    stopRefreshing();
//                    break;
//
//                case ON_UPDATE_FINISH:
//                    SyncResponse syncResponse = (SyncResponse) action.getData();
//                    if (isSwipeToRefreshAction()) {
//                        new Handler().postDelayed(() -> {
//                            stopRefreshing();
//                            handleSyncResponse(syncResponse, true);
//                        }, 1000);
//                    } else {
//                        handleSyncResponse(syncResponse, false);
//                    }
//                    break;
//
//                case UPDATE_AVAILABLE:
//                    if (isAdded()) {
//                        updateAvailableDialog.show(getActivity());
//                    }
//                    stopRefreshing();
//                    break;
//
//                case SHARE_APP:
//                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                    shareIntent.setType(Constants.SHARE_CONTENT_TYPE);
//                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_title));
//                    String shareMessage = getString(R.string.share_message);
//                    shareMessage = shareMessage + Constants.PLAY_STORE_LINK + "\n";
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
//                    startActivity(Intent.createChooser(shareIntent, Constants.SHARE_CHOOSER_TYPE));
//                    analytics.shareClicked();
//                    break;
//            }
//        });
//    }
//
//    private boolean isSwipeToRefreshAction() {
//        return getViewDataBinding().swipeRefresh.isRefreshing();
//    }
//
//    private void handleSyncResponse(SyncResponse syncResponse, boolean showToast) {
//        // show toast with update message
//        if (syncResponse.isHasUpdate()) {
//            customToasts.showDisplayLatestChangesToast(requireActivity(), syncResponse.getUpdateComment());
//        } else if (showToast) {
//            customToasts.showNoChangesToast(requireActivity());
//        }
//
//        // update activities footer
//        if (listener != null) {
//            listener.onAction(actionProvider.provide(ActionCode.SYNC_FINISHED, syncResponse.getUpdateComment()));
//        }
//    }
//
//    private void stopRefreshing() {
//        if (getContext() != null && getViewDataBinding().swipeRefresh.isRefreshing()) {
//            getViewDataBinding().swipeRefresh.setRefreshing(false);
//        }
//    }
//
//    @SuppressWarnings("ConstantConditions")
//    private void initToolbar() {
//        Spinner spinner = getViewDataBinding().spinnerType;
//        List<String> list = Arrays.asList(LineType.FAVORITE, LineType.URBAN, LineType.SUBURBAN);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner_selected, list);
//        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown_list);
//        spinner.setAdapter(adapter);
//        getViewModel().onLineGroupSelected(LineType.FAVORITE);
//    }
//
//    private void onItemAction(Action<ChatAdapter.ActionCode> action) {
//        if (action.getCode() == ChatAdapter.ActionCode.ITEM_CLICK) {
//            if (listener != null) {
//                listener.onAction(actionProvider.provide(ActionCode.ON_LINE_CLICK, action.getData()));
//            }
//        }
//    }
//
//    @Override
//    public void onResume() {
//        if (getContext() != null) {
//            getContext().registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
//
//        } else {
//            // this is a potential bugfix if receiver is not register for any reason
//            getViewModel().getLines();
//        }
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (getContext() != null && broadcastReceiver != null) {
//            getContext().unregisterReceiver(broadcastReceiver);
//        }
//    }

    public enum ActionCode {
        ON_LINE_CLICK, SYNC_FINISHED
    }

}