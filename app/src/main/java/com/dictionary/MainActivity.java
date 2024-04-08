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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_trans);

    }
}
