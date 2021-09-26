package com.hackatonwhoandroid.presentation.main;

import static com.hackatonwhoandroid.presentation.main.MainViewModel.ActionCode.ACTION_FAVORITES;
import static com.hackatonwhoandroid.presentation.main.MainViewModel.ActionCode.TRANSLATE;

import androidx.lifecycle.MutableLiveData;

import com.hackatonwhoandroid.domain.usecase.SendMessageDomainUseCase;
import com.hackatonwhoandroid.utils.ErrorHandler;
import com.hackatonwhoandroid.utils.base.presentation.viewmodel.BaseViewModel;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainViewModel extends BaseViewModel<MainViewModel.ActionCode> {

    private static final String TAG = MainViewModel.class.getSimpleName();

    @Inject
    SendMessageDomainUseCase sendMessageDomainUseCase;

    @Inject
    ErrorHandler errorHandler;

    private final MutableLiveData<MainModel> model = new MutableLiveData<>();
    private MutableLiveData<Boolean> showFavorites = new MutableLiveData<>();

    @Inject
    MainViewModel() {
        showFavorites.setValue(false);
    }

    void callExampleMethod() {
        // todo main thread observe on
//        addDisposable(exampleUseCase.execute()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        value -> {
//                            Log.d(TAG, value);
//                            setModelData(MainModel.builder().text(value).build());
//                        },
//                        error -> dispatchAction(ActionCode.ERROR, errorHandler.parse(error))
//                ));
    }

    public void onActionButtonFavoritesClick() {
        showFavorites.setValue(showFavorites.getValue());
        dispatchAction(ACTION_FAVORITES, showFavorites.getValue());
    }

    public void onActionButtonTranslateClick(){
        dispatchAction(TRANSLATE);
    }

    public void setModelData(MainModel data) {
        model.setValue(data);
    }

    public enum ActionCode {
        ERROR, ACTION_FAVORITES, TRANSLATE
    }

}