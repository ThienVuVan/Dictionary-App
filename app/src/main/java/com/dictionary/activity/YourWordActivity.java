package com.dictionary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dictionary.R;
import com.dictionary.activity.HistoryAdapter;
import com.dictionary.db.MyDB;
import com.dictionary.model.Word;
import com.google.android.material.appbar.MaterialToolbar;

import java.io.IOException;
import java.util.ArrayList;

public class YourWordActivity extends AppCompatActivity {
    private MaterialToolbar btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yourword);

        btnBack = findViewById(R.id.backButton);

        btnBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}