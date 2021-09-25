package com.hackatonwhoandroid.data.localstorage;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import javax.inject.Inject;

public class LocalStorage {

    private static final String STORE_APP_SETTINGS = "STORE_APP_SETTINGS";
    private static final String UPDATE_INFO = "INFO_SERVER";

    @Inject
    SharedPreferences prefs;

    @Inject
    Gson gson;

//    BehaviorSubject<AppSettings> subject = BehaviorSubject.createDefault(AppSettings.initial());

    @Inject
    LocalStorage() {
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

}