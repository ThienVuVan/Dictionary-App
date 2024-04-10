package com.dictionary.api;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface DictionaryApi {
    @GET("en/{word}")
    retrofit2.Call<List<WordDetail>> getMeaning(@Path("word") String word);

    @POST("translate")
    @FormUrlEncoded
    Call<TranslationResponse> getTranslation(
            @Field("key") String apiKey,
            @Field("text") String text,
            @Field("lang") String lang
    );
}
