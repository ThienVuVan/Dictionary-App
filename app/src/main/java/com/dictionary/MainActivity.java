package com.dictionary;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.widget.Toast;
import com.dictionary.api.TranslationResponse;
import com.dictionary.api.WordResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Arrays;
import java.util.List;
import com.dictionary.api.RetrofitInstance;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        // Loại bỏ tiểu đề mặc định
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//
//        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"ádđ",Toast.LENGTH_SHORT).show();
//            }
//        });

        getMeaning("hello");
        getTranslate("nguyen is a dog!");
    }

    private void getTranslate(String text){
        Call<TranslationResponse> call = RetrofitInstance.getYandexApi()
                .getTranslation(
                        "trnsl.1.1.20240308T155126Z.c8bbd140684c9d27.19f4d172357b6331fea1a277381359eef1cd2170",
                        text,
                        "en-vi");
        call.enqueue(new Callback<TranslationResponse>() {
            @Override
            public void onResponse(Call<TranslationResponse> call, Response<TranslationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TranslationResponse translationResponse = response.body();
                    System.out.println(Arrays.toString(translationResponse.getText()));
                } else {
                    Toast.makeText(getApplicationContext(), "No results found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<TranslationResponse> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMeaning(String word) {
        Call<List<WordResult>> call = RetrofitInstance.getDictionaryApi().getMeaning(word);
        call.enqueue(new Callback<List<WordResult>>() {
            @Override
            public void onResponse(Call<List<WordResult>> call, Response<List<WordResult>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    WordResult wordResult = response.body().get(0);
                    Toast.makeText(getApplicationContext(), "Meaning: " + wordResult.getWord(), Toast.LENGTH_SHORT).show();
                    System.out.println(wordResult.getWord());
                    System.out.println(wordResult.getMeanings().get(0).getDefinitions().get(0).getDefinition());
                } else {
                    Toast.makeText(getApplicationContext(), "No results found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<WordResult>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
