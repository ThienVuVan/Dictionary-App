package com.dictionary.api;

import com.dictionary.model.ApiResult;
import com.dictionary.model.Word;
import com.dictionary.model.WordDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class API {

    public static CompletableFuture<ApiResult> getWordEnglish(String text) {
        CompletableFuture<ApiResult> future = new CompletableFuture<>();

        Call<List<WordResult>> callDictionary = RetrofitInstance.getDictionaryApi().getMeaning(text);
        callDictionary.enqueue(new Callback<List<WordResult>>() {
            @Override
            public void onResponse(Call<List<WordResult>> call, Response<List<WordResult>> response) {
                Word word = new Word();
                List<WordDetail> wordDetailList = new ArrayList<>();
                ApiResult apiResult = new ApiResult();
                if (response.isSuccessful()) {
                    if (response.body() != null && !response.body().isEmpty()) {
                        WordResult wordResult = response.body().get(0);
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

                        for(Meaning meaning : wordResult.getMeanings()){
                            WordDetail wordDetail = new WordDetail();
                            wordDetail.setType(meaning.getPartOfSpeech());
                            wordDetail.setDefinition(meaning.getDefinitions().get(0).getDefinition());
                            wordDetail.setExample(meaning.getDefinitions().get(0).getExample());
                            String synonyms = "";
                            for(String synonym : meaning.getSynonyms()){
                                synonyms += synonym + ", ";
                            }
                            wordDetail.setSynonyms(synonyms);
                            String antonyms = "";
                            for(String antonym : meaning.getAntonyms()){
                                antonyms += antonym + ", ";
                            }
                            wordDetail.setAntonyms(antonyms);
                            wordDetailList.add(wordDetail);
                        }
                        // call api yandex
                        getTranslate(text, 1).thenAccept(translation -> {
                            word.setTranslated_text(translation);
                            apiResult.setWord(word);
                            apiResult.setWordDetailList(wordDetailList);
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
            public void onFailure(Call<List<WordResult>> call, Throwable t) {
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
