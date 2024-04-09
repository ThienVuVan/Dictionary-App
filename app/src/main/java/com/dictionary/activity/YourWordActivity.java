package com.dictionary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private ListView listYourWordView;
    private YourWordAdapter yourWordAdapter;
    private  ArrayList<Word> listYourWord;
    private Button btnDel;
    private Button btnSellectAll;
    private EditText searchText;
    private MaterialToolbar btnBack;
    private MyDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yourword);
        btnDel = findViewById(R.id.ybtnDel);
        btnSellectAll = findViewById(R.id.ybtnSellectAll);
        listYourWordView = findViewById(R.id.yourWordList);
        searchText  = findViewById(R.id.ysearchText);
        db = MyDB.getInstance(this);
        listYourWord = db.getAllMarkedWords();
        yourWordAdapter  = new YourWordAdapter(this,listYourWord);
        listYourWordView.setAdapter(yourWordAdapter);
        btnBack = findViewById(R.id.backButton);

        btnBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}