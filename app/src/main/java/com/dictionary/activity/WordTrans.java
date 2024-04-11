package com.dictionary.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dictionary.R;
import com.dictionary.api.API;
import com.dictionary.api.Function;
import com.dictionary.api.Meaning;
import com.dictionary.api.WordResult;
import com.dictionary.db.MyDB;
import com.dictionary.model.Word;
import com.dictionary.model.WordDetail;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class WordTrans extends AppCompatActivity {

    private TextView txtTranslated,txtPhonetic,txtWord;
    Toolbar toolbar;
    ImageButton searchButton, backButton;
    EditText searchEditText;
    RecyclerView recyclerView;
    RecyclerViewItem adapter;
    private Word currentWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_trans);

        backButton = (ImageButton) findViewById(R.id.backButtontext);
        searchEditText = (EditText) findViewById(R.id.txtSearch);

        searchButton = (ImageButton) findViewById(R.id.searchButton);

        txtTranslated  = findViewById(R.id.translated_textview);
        txtWord = findViewById(R.id.word_textview);
        txtPhonetic = findViewById(R.id.phonetic_textview);

        recyclerView = findViewById(R.id.recycler);
        adapter = new RecyclerViewItem(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        searchEditText.setVisibility(View.GONE);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchEditText.getVisibility() == View.GONE) {
                    searchEditText.setVisibility(View.VISIBLE);
                    findViewById(R.id.favorButton).setVisibility(View.GONE);
                    findViewById(R.id.noteButton).setVisibility(View.GONE);
                    searchButton.setVisibility(View.GONE);
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchEditText.getVisibility() == View.GONE && searchButton.getVisibility() == View.VISIBLE){
                    finish();
                }
                if (searchButton.getVisibility() == View.GONE) {
                    searchEditText.setVisibility(View.GONE);
                    findViewById(R.id.favorButton).setVisibility(View.VISIBLE);
                    findViewById(R.id.noteButton).setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.VISIBLE);
                }
            }
        });
        searchEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
                    API.getWordEnglish(searchEditText.getText().toString())
                            .thenAccept(apiResult -> {
                                if(apiResult == null){
                                    // thông báo từ không tồn tại
                                    return;
                                }
                                Word newWord = apiResult.getWord();
                                currentWord = newWord;
                                List<WordDetail> wordDetailList = apiResult.getWordDetailList();
                                adapter.setData(wordDetailList);
                                recyclerView.setAdapter(adapter);

                                txtWord.setText(newWord.getOriginal_text());
                                txtPhonetic.setText(newWord.getPhonetic());
                                txtTranslated.setText(Function.removeOuterParentheses(newWord.getTranslated_text()));

                                MyDB db = MyDB.getInstance(getApplicationContext());
                                if(!db.isWordExists(newWord.getOriginal_text())) db.addWord(newWord);

                            }).exceptionally(throwable -> {
                                throwable.printStackTrace();
                                return null;
                            });
                }
                return false;
            }
        });

        // nếu ấn mark word
        // gọi api lấy ra word để ấy id theo current_word bên trênda lưu
        // sau đó gọi api đánh dấu mark word = 1
        // khi đánh dấu thành công thì thay đổi màu ngôi sao

    }
}