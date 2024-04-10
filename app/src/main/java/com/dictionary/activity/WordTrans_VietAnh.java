package com.dictionary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.dictionary.MainActivity;
import com.dictionary.R;
import com.dictionary.api.API;
import com.dictionary.api.Function;
import com.dictionary.model.Word;
import com.google.android.material.tabs.TabLayout;

public class WordTrans_VietAnh extends AppCompatActivity {
    private TextView txtTranslated;
    private TextView txtWord;
    private TextView txtDefination;
    private TextView txtSyn;
    private TextView txtAnt;
    private TextView txtExample;
    Toolbar toolbar;
    ImageButton searchButton, backButton;
    EditText searchEditText;
    private TabLayout tabLayout;
    ViewPager  viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_trans_viet_anh);

        backButton = (ImageButton) findViewById(R.id.backButtontext);
        searchEditText = (EditText) findViewById(R.id.txtSearch);

        searchButton = (ImageButton) findViewById(R.id.searchButton);
        txtTranslated  = findViewById(R.id.translated_textview);
        txtDefination = findViewById(R.id.definitions_textview);
        txtSyn = findViewById(R.id.synonyms_textview);
        txtAnt = findViewById(R.id.antonyms_textview);
        txtExample = findViewById(R.id.example_textview);
        txtWord = findViewById(R.id.word_textview);

//      Xử lí xuất hiện search bar
        searchEditText.setVisibility(View.GONE);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchEditText.getVisibility() == View.GONE) {
                    // Hiển thị EditText và ẩn các nút khác
                    searchEditText.setVisibility(View.VISIBLE);
//                    findViewById(R.id.toolbar_title).setVisibility(View.GONE);
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
//                    Intent i = new Intent(WordTrans_VietAnh.this, MainActivity.class);
//                    startActivity(i);
                    finish();
                }
                if (searchButton.getVisibility() == View.GONE) {
                    searchEditText.setVisibility(View.GONE);
//                    findViewById(R.id.toolbar_title).setVisibility(View.VISIBLE);
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
                    API.getTranslate("xin chào", 2)
                            .thenCompose(text -> {
                                String cleanedText = Function.removeOuterParentheses(text);
                                return API.getWordEnglish(cleanedText)
                                        .thenAccept(word -> {
                                            // hiệu sử lý ở đây như cái trước, đợi ngueeen put cái mới lên làm phần mark
                                            // word sau. cái original_text với transalte_text nó ngược với cái kia.
                                        })
                                        .exceptionally(throwable -> {
                                            throwable.printStackTrace();
                                            return null;
                                        });
                            })
                            .exceptionally(throwable -> {
                                throwable.printStackTrace();
                                return null;
                            });
                }
                return false;
            }
        });
    }
}