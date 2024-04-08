package com.dictionary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dictionary.activity.HistoryAdapter;
import com.dictionary.db.MyDB;
import com.dictionary.model.Word;

import java.io.IOException;
import java.util.ArrayList;

public class YourWordActivity extends AppCompatActivity {
    private TextView SearchText;
    private Button btnDel;
    private Button btnMarkAll;
    private ListView listView;
    private ArrayList<Word>  listWord;
    private HistoryAdapter adapter;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }


}