package com.dictionary;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.widget.Toast;
import com.dictionary.api.API;
import com.dictionary.api.TranslationResponse;
import com.dictionary.api.WordResult;
import com.dictionary.model.Word;
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
        setContentView(R.layout.word_trans);

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

        // call api for word
        API.getWord("hello").thenAccept(word -> {
            System.out.println(word);
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });

        // call api for text translate
        API.getTranslate("nguyen is a dog!").thenAccept(text -> {
            System.out.println(text);
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
    }
}
