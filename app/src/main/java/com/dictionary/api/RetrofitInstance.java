package com.dictionary.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static final String BASE_URL = "https://api.dictionaryapi.dev/api/v2/entries/";
    private static final String BASE_URL_YANDEX = "https://translate.yandex.net/api/v1.5/tr.json/";
    private static Retrofit getInstanceDic() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static Retrofit getInstanceYandex() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_YANDEX)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static final DictionaryApi dictionaryApi = getInstanceDic().create(DictionaryApi.class);
    public static final DictionaryApi YandexApi = getInstanceYandex().create(DictionaryApi.class);

    public static DictionaryApi getDictionaryApi() {
        return dictionaryApi;
    }

    public static DictionaryApi getYandexApi() {
        return YandexApi;
    }



}