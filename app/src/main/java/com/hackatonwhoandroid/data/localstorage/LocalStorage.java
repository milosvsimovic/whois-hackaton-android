package com.hackatonwhoandroid.data.localstorage;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hackatonwhoandroid.data.localstorage.model.MessageData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class LocalStorage {

    private static final String STORE_APP_SETTINGS = "STORE_APP_SETTINGS";
    private static final String MESSAGE_HISTORY = "MESSAGE_HISTORY";
    @Inject
    SharedPreferences prefs;
    @Inject
    Gson gson;

    private List<MessageData> initialList = new ArrayList<>();
    private boolean showOnlyFavorites;

    private final BehaviorSubject<List<MessageData>> subject;

    @Inject
    LocalStorage() {
        this.subject = BehaviorSubject.createDefault(initialList);
    }

//    public Completable updateAppSettings(AppSettings settings) {
//        prefs.edit()
//                .putString(STORE_APP_SETTINGS, gson.toJson(settings))
//                .apply();
//        subject.onNext(settings);
//        return Completable.complete();
//    }
//
//    public Observable<AppSettings> getAppSettings() { return subject.map(settings -> {
//            String json = prefs.getString(STORE_APP_SETTINGS, gson.toJson(AppSettings.initial()));
//            return gson.fromJson(json, AppSettings.class);
//        });
//    }
//
//    public void updateInfo(UpdateInfo updateInfoItems) {
//        prefs.edit()
//                .putString(UPDATE_INFO, gson.toJson(updateInfoItems))
//                .apply();
//    }
//
//    public Single<UpdateInfo> getInfo() {
//        String json = prefs.getString(UPDATE_INFO, gson.toJson(UpdateInfo.initial()));
//        return Single.just(gson.fromJson(json, UpdateInfo.class));
//    }

    public void updateMessageList(List<MessageData> messages) {
        prefs.edit()
                .putString(MESSAGE_HISTORY, gson.toJson(messages))
                .apply();
        if (showOnlyFavorites) {
            subject.onNext(new ArrayList<>());
            subject.onNext(filterOutFavorites(messages));
        } else {
            subject.onNext(messages);
        }
    }

    private List<MessageData> filterOutFavorites(List<MessageData> messages) {
        return messages.stream()
                .filter(MessageData::isFavorite)
                .distinct()
                .collect(Collectors.toList());
    }

    public void showFavoritesOnly() {
        showOnlyFavorites = !showOnlyFavorites;
        refreshMessages();
    }

    public List<MessageData> getList() {
        String json = prefs.getString(MESSAGE_HISTORY, gson.toJson(new ArrayList<>()));
        Type listType = new TypeToken<ArrayList<MessageData>>() {
        }.getType();
        return gson.fromJson(json, listType);
    }

    public Observable<List<MessageData>> getMessages() {
        return subject;
    }

    public void addMessage(MessageData message) {
        List<MessageData> list = getList();
        list.add(message);
        updateMessageList(list);
    }

    public void updateMessage(MessageData message) {
        List<MessageData> list = getList();
        list = list.stream().map(data -> data.getTimestamp() == (message.getTimestamp()) ? message : data)
                .collect(Collectors.toList());
        updateMessageList(list);
    }

    public void refreshMessages() {
        if (showOnlyFavorites) {
            subject.onNext(new ArrayList<>());
            subject.onNext(filterOutFavorites(getList()));
        } else {
            subject.onNext(getList());
        }
    }
}