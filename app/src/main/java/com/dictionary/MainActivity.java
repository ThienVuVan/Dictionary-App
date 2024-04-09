package com.dictionary;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.dictionary.api.API;
import com.dictionary.model.Word;

import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_trans);
    }
}
