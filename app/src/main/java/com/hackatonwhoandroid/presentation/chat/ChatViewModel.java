package com.hackatonwhoandroid.presentation.chat;

import static com.hackatonwhoandroid.presentation.chat.ChatViewModel.ActionCode.ON_DOMAIN_RESPONSE;
import static com.hackatonwhoandroid.presentation.chat.ChatViewModel.ActionCode.ON_DOMAIN_SUBMIT;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.hackatonwhoandroid.domain.exceptions.NoNetworkException;
import com.hackatonwhoandroid.domain.model.Message;
import com.hackatonwhoandroid.domain.usecase.WhoisUseCase;
import com.hackatonwhoandroid.utils.ErrorHandler;
import com.hackatonwhoandroid.utils.Toaster;
import com.hackatonwhoandroid.utils.base.presentation.viewmodel.BaseViewModel;

import org.joda.time.DateTime;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    WhoisUseCase whoisUseCase;

    private final MutableLiveData<List<MessageModel>> messages = new MutableLiveData<>();
    private final MutableLiveData<MessageModel> selectedDomainMessage = new MutableLiveData<>();

    private final List<String> searchHistory = new ArrayList<>();

    @Inject
    ChatViewModel() {
//        MessageModel message = new MessageModel();
//        message.body

//                .body("Zdravo")
//                .timeStamp(DateTime.now())
//                .isCreatedByUser(false)
//                .build();
////
//        MessageModel message2 = MessageModel.builder()
//                .body("Kako ti mogu pomoći? \uD83D\uDE42")
//                .timeStamp(DateTime.now())
//                .isCreatedByUser(false)
//                .build();

        List<MessageModel> list = new ArrayList<>();
//        list.add(message);
//        list.add(message2);
        messages.setValue(list);
    }

    public boolean onInputSubmit(String domainName) {
        sendDomainMessage(domainName.toLowerCase());
        return true;
    }

    private void sendDomainMessage(String input) {
        MessageModel userMessage = new MessageModel();
        userMessage.setBody(input);
        userMessage.setTimeStamp(DateTime.now());
        userMessage.setCreatedByUser(true);
        userMessage.setFavorite(false);
        userMessage.setType(Message.Type.DOMAIN);
        addToMessages(userMessage);

        addDisposable(whoisUseCase.execute(input)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageResponse -> {
                            MessageModel model = Mappers.getMapper(MessageModel.Mappers.class).map(messageResponse);
                            addToMessages(model);
                            selectedDomainMessage.setValue(userMessage);
                            dispatchAction(ON_DOMAIN_RESPONSE);
                        },
                        this::handleOnError
                ));
        dispatchAction(ON_DOMAIN_SUBMIT);
    }


    private void addToMessages(MessageModel message) {
        List<MessageModel> list = messages.getValue();
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(message);
        messages.setValue(list);
    }

    public void onDomainMessageActionClick(DomainMessageAction action) {
        MessageModel selectedDomainMessage = this.selectedDomainMessage.getValue();
        if (selectedDomainMessage == null) {
            return;
        }

        switch (action) {
            case FAVORITE:
                // toggle message favorite boolean
                List<MessageModel> messages = this.messages.getValue();
                if (messages != null) {
                    for (MessageModel element : messages) {
                        if (element.getTimeStamp().isEqual(selectedDomainMessage.getTimeStamp())) {
                            element.setFavorite(!element.isFavorite());
                            break;
                        }
                    }
                    this.messages.setValue(messages);
                }
                break;
            case REFRESH:
                // resend message with domain name
                sendDomainMessage(selectedDomainMessage.getBody());
                this.selectedDomainMessage.setValue(null);
                break;
            case REMINDER:
                Toaster.showToast(action.toString());
                break;
        }

    }

    public void addToSearchHistory(String value) {
        searchHistory.removeIf(element -> element.equals(value));
        searchHistory.add(value);
        searchHistory.stream().distinct().collect(Collectors.toList());

    }

//    public LiveData<Boolean> showNoFavoritesView() {
//        return Transformations.map(list, input -> LineType.FAVORITE.equals(selectedLineGroup.getValue()) && list.getValue() != null && list.getValue().isEmpty());
//    }

    //    @SuppressWarnings("WeakerAccess")
//    public void syncData() {
//        analytics.syncSwiped();
//        addDisposable(syncDataUseCase.execute()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        syncResponse -> {
//                            noNetwork.setValue(false);
//                            checkForAppUpdate(syncResponse);
//                        },
//                        this::handleOnError
//                ));
//    }
//
//    private void checkForAppUpdate(SyncResponse syncResponse) {
//        addDisposable(checkForAppUpdateUseCase.execute()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        hasAppUpdate -> {
//                            Log.d(TAG, "Has App update");
//                            dispatchAction(ActionCode.UPDATE_AVAILABLE);
//                        },
//                        error -> dispatchAction(ActionCode.ERROR, errorHandler.parse(error)),
//                        () -> {
//                            Log.d(TAG, "Sync finished");
//                            dispatchAction(ActionCode.ON_UPDATE_FINISH, syncResponse);
//                        }
//                ));
//    }
//
//    @SuppressWarnings("WeakerAccess")
//    public void getLines() {
//        addDisposable(getLinesUseCase.execute()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        list -> {
//                            dispatchAction(ActionCode.ON_LIST);
//                            Log.d(TAG, "Lines: " + list.size() + " ");
//                            this.list.setValue(Mappers.getMapper(ChatItemModel.Mappers.class).mapAll(list));
//                        },
//                        this::handleOnError
//                ));
//    }
//
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

    public enum ActionCode {
        ERROR, ERROR_NETWORK, ON_LIST, ON_DOMAIN_SUBMIT, ON_DOMAIN_RESPONSE
    }

    public enum DomainMessageAction {
        FAVORITE, REFRESH, REMINDER
    }

}