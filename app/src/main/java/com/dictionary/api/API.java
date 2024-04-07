package com.dictionary.api;

import com.dictionary.model.Word;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class API {

    public static CompletableFuture<Word> getWord(String text) {
        CompletableFuture<Word> future = new CompletableFuture<>();

        // call api dictionary
        Call<List<WordResult>> callDictionary = RetrofitInstance.getDictionaryApi().getMeaning(text);
        callDictionary.enqueue(new Callback<List<WordResult>>() {
            @Override
            public void onResponse(Call<List<WordResult>> call, Response<List<WordResult>> response) {
                Word word = new Word();
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    WordResult wordResult = response.body().get(0);
                    word.setOriginal_text(text);
                    word.setMark(0);
                    if (!wordResult.getMeanings().get(0).getPartOfSpeech().isEmpty()) {
                        word.setType(wordResult.getMeanings().get(0).getPartOfSpeech());
                    }
                    if (!wordResult.getMeanings().get(0).getDefinitions().isEmpty()) {
                        word.setDefinition(wordResult.getMeanings().get(0).getDefinitions().get(0).getDefinition());
                    }
                    if (!wordResult.getMeanings().get(0).getSynonyms().isEmpty()) {
                        word.setSynonyms(wordResult.getMeanings().get(0).getSynonyms().get(0));
                    }
                    if (!wordResult.getMeanings().get(0).getAntonyms().isEmpty()) {
                        word.setAntonyms(wordResult.getMeanings().get(0).getAntonyms().get(0));
                    }
                    if (!wordResult.getMeanings().get(0).getDefinitions().isEmpty()) {
                        word.setExample(wordResult.getMeanings().get(0).getDefinitions().get(0).getExample());
                    }
                    if (wordResult.getPhonetics().get(0).getAudio() != null) {
                        word.setAudio(wordResult.getPhonetics().get(0).getAudio());
                    }

                    // call api yandex
                    getTranslate(text).thenAccept(translation -> {
                        word.setTranslated_text(translation);
                        future.complete(word);
                    }).exceptionally(throwable -> {
                        future.completeExceptionally(throwable);
                        return null;
                    });
                } else {
                    future.completeExceptionally(new RuntimeException("Failed to get word definition from dictionary"));
                }
            }

            @Override
            public void onFailure(Call<List<WordResult>> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });

        return future;
    }

    public static CompletableFuture<String> getTranslate(String text) {
        CompletableFuture<String> future = new CompletableFuture<>();

        Call<TranslationResponse> callYandex = RetrofitInstance.getYandexApi()
                .getTranslation(
                        "trnsl.1.1.20240308T155126Z.c8bbd140684c9d27.19f4d172357b6331fea1a277381359eef1cd2170",
                        text,
                        "en-vi");

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
