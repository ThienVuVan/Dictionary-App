package com.dictionary.api;

import com.dictionary.model.ApiResult;
import com.dictionary.model.Word;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class API {

    public static CompletableFuture<ApiResult> getWordEnglish(String text) {
        CompletableFuture<ApiResult> future = new CompletableFuture<>();

        // call api dictionary
        Call<List<WordDetail>> callDictionary = RetrofitInstance.getDictionaryApi().getMeaning(text);
        callDictionary.enqueue(new Callback<List<WordDetail>>() {
            @Override
            public void onResponse(Call<List<WordDetail>> call, Response<List<WordDetail>> response) {
                Word word = new Word();
                ApiResult apiResult = new ApiResult();
                if (response.isSuccessful()) {
                    if (response.body() != null && !response.body().isEmpty()) {
                        WordDetail wordResult = response.body().get(0);
                        word.setOriginal_text(text);
                        word.setIsMark(0);
                        for (Phonetic phoneticItem : wordResult.getPhonetics()) {
                            if (phoneticItem.getAudio() != null) {
                                word.setAudio(phoneticItem.getAudio());
                                break;
                            }
                        }
                        for (Phonetic phoneticItem : wordResult.getPhonetics()) {
                            if (phoneticItem.getText() != null) {
                                word.setPhonetic(phoneticItem.getText());
                                break;
                            }
                        }
                        // call api yandex
                        getTranslate(text, 1).thenAccept(translation -> {
                            word.setTranslated_text(translation);
                            apiResult.setWord(word);
                            apiResult.setWord_detail(wordResult);
                            future.complete(apiResult);
                        }).exceptionally(throwable -> {
                            future.completeExceptionally(throwable);
                            return null;
                        });
                    } else {
                        future.complete(null); // Trả về null khi không có dữ liệu từ API
                    }
                } else {
                    future.complete(null); // Trả về null khi API không thành công
                }
            }

            @Override
            public void onFailure(Call<List<WordDetail>> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });

        return future;
    }


    public static CompletableFuture<String> getTranslate(String text, Integer mode) {
        CompletableFuture<String> future = new CompletableFuture<>();
        String lang;

        if (mode == 1) {
            lang = "en-vi";
        } else {
            lang = "vi-en";
        }

        Call<TranslationResponse> callYandex = RetrofitInstance.getYandexApi()
                .getTranslation(
                        "trnsl.1.1.20240308T155126Z.c8bbd140684c9d27.19f4d172357b6331fea1a277381359eef1cd2170",
                        text,
                        lang);
        callYandex.enqueue(new Callback<TranslationResponse>() {
            @Override
            public void onResponse(Call<TranslationResponse> call, Response<TranslationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TranslationResponse translationResponse = response.body();
                    String translation = Arrays.toString(translationResponse.getText());
                    future.complete(translation);
                } else {
                    future.completeExceptionally(new RuntimeException("Failed to get translation from yandex"));
                }
            }

            @Override
            public void onFailure(Call<TranslationResponse> call, Throwable throwable) {
                future.completeExceptionally(throwable);
            }
        });

        return future;
    }
}
