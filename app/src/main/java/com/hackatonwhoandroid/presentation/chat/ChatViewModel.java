package com.hackatonwhoandroid.presentation.chat;

import static com.hackatonwhoandroid.presentation.chat.ChatViewModel.ActionCode.ON_DOMAIN_RESPONSE;
import static com.hackatonwhoandroid.presentation.chat.ChatViewModel.ActionCode.ON_DOMAIN_SUBMIT;
import static com.hackatonwhoandroid.presentation.chat.ChatViewModel.ActionCode.ON_LIST_UPDATE;

import android.content.res.Resources;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.hackatonwhoandroid.domain.exceptions.NoNetworkException;
import com.hackatonwhoandroid.domain.model.Message;
import com.hackatonwhoandroid.domain.usecase.FavoriteMessageMarkUseCase;
import com.hackatonwhoandroid.domain.usecase.GetMessagesUseCase;
import com.hackatonwhoandroid.domain.usecase.SendMessageDomainUseCase;
import com.hackatonwhoandroid.utils.ErrorHandler;
import com.hackatonwhoandroid.utils.Toaster;
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
    GetMessagesUseCase getMessagesUseCase;

    @Inject
    FavoriteMessageMarkUseCase favoriteMessageMarkUseCase;

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
        addDisposable(getMessagesUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messages -> {
                            List<MessageModel> list = Mappers.getMapper(MessageModel.Mappers.class).mapAll(messages, resources);
                            this.messages.setValue(list);
                            dispatchAction(ON_LIST_UPDATE, list.size());
                        },
                        this::handleOnError
                ));
    }

    public boolean onInputSubmit(String domainName) {
        sendDomainMessage(domainName.toLowerCase());
        return true;
    }

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
                Message message =Mappers.getMapper(MessageModel.Mappers.class).mapToMessage(selectedDomainMessage);
                addDisposable(favoriteMessageMarkUseCase.execute(message)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    dispatchAction(ON_LIST_UPDATE);
                                },
                                this::handleOnError
                        ));
//                // toggle message favorite boolean
//                List<MessageModel> messages = this.messages.getValue();
//                if (messages != null) {
//                    boolean isFavorite = !selectedDomainMessage.isFavorite();
//                    for (MessageModel element : messages) {
//                        // all elements with the same body (domain name) should toggle favorites state
//                        if (Message.Type.isClickable(element.getType()) && element.getBody().equals(selectedDomainMessage.getBody())) {
//                            element.setFavorite(isFavorite);
//                        }
//                    }
//                    this.messages.setValue(messages);
//                }
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

    public void addToSearchHistory(String newValue) {
        searchHistory.removeIf(element -> element.equals(newValue));
        searchHistory.add(0, newValue);
    }

    public void showFavorites(boolean show) {
        List<MessageModel> messages = this.messages.getValue();

        List<String> favorites = new ArrayList<>();

        for (int i = messages.size() - 1; i > 0; i--) {
            MessageModel element = messages.get(i);
            if (!Message.Type.isClickable(element.getType())) {
                element.setVisible(show);
            } else {
                if (!favorites.contains(element.getBody())) {
                    favorites.add(element.getBody());
                } else {
                    element.setVisible(show);
                }
            }
        }
        this.messages.setValue(messages);
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
        ERROR, ERROR_NETWORK, ON_LIST_UPDATE, ON_DOMAIN_SUBMIT, ON_DOMAIN_RESPONSE, CHANGE_COLOR
    }

    public enum DomainMessageAction {
        FAVORITE, REFRESH, REMINDER
    }

}