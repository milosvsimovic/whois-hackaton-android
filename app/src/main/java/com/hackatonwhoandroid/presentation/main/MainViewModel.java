package com.hackatonwhoandroid.presentation.main;

import static com.hackatonwhoandroid.presentation.main.MainViewModel.ActionCode.ACTION_FAVORITES;

import androidx.lifecycle.MutableLiveData;

import com.hackatonwhoandroid.domain.usecase.WhoisUseCase;
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
    WhoisUseCase whoisUseCase;

    @Inject
    ErrorHandler errorHandler;

    private final MutableLiveData<MainModel> model = new MutableLiveData<>();

    @Inject
    MainViewModel() {
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

    public void onActionButtonFavoritesClick(){
        dispatchAction(ACTION_FAVORITES);
    }

    public void setModelData(MainModel data) {
        model.setValue(data);
    }

    public enum ActionCode {
        ERROR, ACTION_FAVORITES
    }

}