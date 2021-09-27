package com.hackatonwhoandroid.presentation.chat;

import static com.hackatonwhoandroid.presentation.chat.ChatViewModel.ActionCode.ON_DOMAIN_RESPONSE;
import static com.hackatonwhoandroid.presentation.chat.ChatViewModel.ActionCode.ON_DOMAIN_SUBMIT;
import static com.hackatonwhoandroid.presentation.chat.ChatViewModel.ActionCode.ON_LIST_UPDATE;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.hackatonwhoandroid.domain.exceptions.NoNetworkException;
import com.hackatonwhoandroid.domain.model.Message;
import com.hackatonwhoandroid.domain.usecase.FavoriteMessageMarkUseCase;
import com.hackatonwhoandroid.domain.usecase.RefreshMessagesUseCase;
import com.hackatonwhoandroid.domain.usecase.SendMessageDomainUseCase;
import com.hackatonwhoandroid.domain.usecase.ShowFavoriteMessagesUseCase;
import com.hackatonwhoandroid.domain.usecase.SubscribeForMessagesUseCase;
import com.hackatonwhoandroid.utils.ErrorHandler;
import com.hackatonwhoandroid.utils.base.presentation.viewmodel.BaseViewModel;

import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChatViewModel extends BaseViewModel<ChatViewModel.ActionCode> {

    private static final String TAG = ChatViewModel.class.getSimpleName();

    @Inject
    ErrorHandler errorHandler;

    @Inject
    SendMessageDomainUseCase sendMessageDomainUseCase;

    @Inject
    SubscribeForMessagesUseCase subscribeForMessagesUseCase;

    @Inject
    FavoriteMessageMarkUseCase favoriteMessageMarkUseCase;

    @Inject
    ShowFavoriteMessagesUseCase showFavoriteMessagesUseCase;

    @Inject
    RefreshMessagesUseCase refreshMessagesUseCase;

    @Inject
    Resources resources;

    private final MutableLiveData<List<MessageModel>> messages = new MutableLiveData<>();
    private final MutableLiveData<MessageModel> selectedDomainMessage = new MutableLiveData<>();

    // distinct element list
    private final List<String> searchHistory = new ArrayList<>();

    @Inject
    ChatViewModel() {
        List<MessageModel> list = new ArrayList<>();
        messages.setValue(list);
    }

    public void initMessages() {
        addDisposable(subscribeForMessagesUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messages -> {
                            List<MessageModel> list = Mappers.getMapper(MessageModel.Mappers.class).mapAll(messages, resources);
                            this.messages.setValue(list);
                        },
                        this::handleOnError
                ));
        addDisposable(refreshMessagesUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                        },
                        this::handleOnError
                ));
    }

    public boolean onInputSubmit(String domainName) {
        sendDomainMessage(domainName);
        return true;
    }

//    MessageModel userMessage = createUserMessage(input, name, extension, resources.getString(R.string.chat_status_message_checking_domain));
//    addToMessages(userMessage);
//    addToSearchHistory(input);
//    int messageNumber = getLastMessageNumber();
//    dispatchAction(ON_DOMAIN_SUBMIT);

    private void sendDomainMessage(String input) {
        dispatchAction(ON_DOMAIN_SUBMIT);
        addDisposable(sendMessageDomainUseCase.execute(input)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            dispatchAction(ON_DOMAIN_RESPONSE);
                        },
                        this::handleOnError
                ));
    }

    public void onDomainMessageActionClick(DomainMessageAction action) {
        MessageModel selectedDomainMessage = this.selectedDomainMessage.getValue();
        if (selectedDomainMessage == null) {
            return;
        }

        switch (action) {
            case FAVORITE:
                Message message = Mappers.getMapper(MessageModel.Mappers.class).mapToMessage(selectedDomainMessage);
                addDisposable(favoriteMessageMarkUseCase.execute(message)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    dispatchAction(ON_LIST_UPDATE);
                                },
                                this::handleOnError
                        ));
                break;
            case REFRESH:
                // resend message with domain name
                sendDomainMessage(selectedDomainMessage.getBody());
                this.selectedDomainMessage.setValue(null);
                break;
            case REMINDER:
                sendDomainMessage("_more_info_");
                break;
        }

    }

    @NonNull
    private MessageModel createReminderMessage(String domain, String name, String domainExtension, String statusMessage) {
        MessageModel userMessage = new MessageModel();
        userMessage.setBody(domain);
        userMessage.setDomainName(name);
        userMessage.setTimestamp(System.currentTimeMillis());
        userMessage.setCreatedByUser(true);
        userMessage.setFavorite(false);
        userMessage.setType(Message.Type.REMINDER);
        userMessage.setStatusMessage(statusMessage);
        return userMessage;
    }

    public void showFavorites(boolean show) {
        addDisposable(showFavoriteMessagesUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            dispatchAction(ON_LIST_UPDATE);
                        },
                        this::handleOnError
                ));
    }

    private void handleOnError(Throwable error) {
        if (error instanceof NoNetworkException) {
//            noNetwork.setValue(true);
            dispatchAction(ActionCode.ERROR_NETWORK);
        } else {
            dispatchAction(ActionCode.ERROR, errorHandler.parse(error));
        }
    }
//
//    public void onRefresh() {
//        syncData();
//        getLines();
//    }
//
//    public void onShareClick() {
//        dispatchAction(ActionCode.SHARE_APP);
//    }
//
//    public void onDirectionChangeClick() {
//        addDisposable(changeDirectionUseCase.execute()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(() -> {
//                        },
//                        this::handleOnError
//                ));
//    }
//
//    public void onLineGroupSelected(@LineType String lineGroup) {
//        Log.d(TAG, lineGroup + " ------------------------ ");
//        analytics.menuSelected(lineGroup);
//        selectedLineGroup.setValue(lineGroup);
//        updateSettings(AppSettings.builder()
//                .selectedLineType(lineGroup)
//                .build());
//    }
//
//    private void updateSettings(AppSettings settings) {
//        addDisposable(updateAppSettingsUseCase.execute(settings)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(() -> {
//                        },
//                        this::handleOnError
//                ));
//    }
//
//    public void onCityLinesClick() {
//        onLineGroupSelected(LineType.URBAN);
//    }
//
//    public void onInterCityLinesClick() {
//        onLineGroupSelected(LineType.SUBURBAN);
//    }


    @Override
    protected void dispatchAction(ActionCode code) {
        super.dispatchAction(code);
    }

    public void selectDomainMessage(@Nullable MessageModel domainMessage) {
        selectedDomainMessage.setValue(domainMessage);
    }

    public void sendInitialMessage() {
        sendDomainMessage("_init");
    }


    public enum ActionCode {
        ERROR, ERROR_NETWORK, ON_LIST_UPDATE, ON_DOMAIN_SUBMIT, ON_DOMAIN_RESPONSE, CHANGE_COLOR
    }

    public enum DomainMessageAction {
        FAVORITE, REFRESH, REMINDER
    }

}