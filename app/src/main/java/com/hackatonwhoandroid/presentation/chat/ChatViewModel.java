package com.hackatonwhoandroid.presentation.chat;

import static com.hackatonwhoandroid.presentation.chat.ChatViewModel.ActionCode.REFRESHED;

import androidx.lifecycle.MutableLiveData;

import com.hackatonwhoandroid.utils.ErrorHandler;
import com.hackatonwhoandroid.utils.base.presentation.viewmodel.BaseViewModel;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChatViewModel extends BaseViewModel<ChatViewModel.ActionCode> {

    private static final String TAG = ChatViewModel.class.getSimpleName();

    @Inject
    ErrorHandler errorHandler;

    private final MutableLiveData<List<MessageModel>> messages = new MutableLiveData<>();

    @Inject
    ChatViewModel() {
        MessageModel message = MessageModel.builder()
                .body("Zdravo")
                .timeStamp(DateTime.now())
                .isCreatedByUser(false)
                .build();

        MessageModel message2 = MessageModel.builder()
                .body("Kako ti mogu pomoći? \uD83D\uDE42")
                .timeStamp(DateTime.now())
                .isCreatedByUser(false)
                .build();

        List<MessageModel> list = new ArrayList<>();
        list.add(message);
        list.add(message2);
        messages.setValue(list);
    }

    public boolean onInputSubmit(String input) {
        // todo implement submit
        MessageModel message = MessageModel.builder()
                .body(input)
                .timeStamp(DateTime.now())
                .isCreatedByUser(true)
                .build();
        List<MessageModel> list = messages.getValue();
        list.add(message);
        messages.setValue(list);
        return true;
    }

    public void onRefresh() {
        dispatchAction(REFRESHED);
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
//    private void handleOnError(Throwable error) {
//        if (error instanceof NoNetworkException) {
//            noNetwork.setValue(true);
//            dispatchAction(ActionCode.ERROR_NETWORK);
//        } else {
//            dispatchAction(ActionCode.ERROR, errorHandler.parse(error));
//        }
//    }
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

    public enum ActionCode {
        ERROR, ERROR_NETWORK, ON_LIST, REFRESHED
    }

}