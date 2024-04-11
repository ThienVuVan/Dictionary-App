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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.dictionary.MainActivity;
import com.dictionary.R;
import com.dictionary.api.API;
import com.dictionary.api.Function;
import com.dictionary.model.Word;
import com.dictionary.model.WordDetail;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class WordTrans_VietAnh extends AppCompatActivity {
    private TextView txtTranslated, txtPhonetic, txtWord;
    Toolbar toolbar;
    ImageButton searchButton, backButton;
    EditText searchEditText;
    RecyclerView recyclerView;
    RecyclerViewItem adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_trans_viet_anh);

        backButton = (ImageButton) findViewById(R.id.backButtontext);
        searchEditText = (EditText) findViewById(R.id.txtSearch);
        searchButton = (ImageButton) findViewById(R.id.searchButton);

        txtTranslated = findViewById(R.id.translated_textview);
        txtWord = findViewById(R.id.word_textview);
        txtPhonetic = findViewById(R.id.phonetic_textview);
//RecyclerView
        recyclerView = findViewById(R.id.recycler2);
        adapter = new RecyclerViewItem(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
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
                if (searchEditText.getVisibility() == View.GONE && searchButton.getVisibility() == View.VISIBLE) {
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
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    API.getTranslate(searchEditText.getText().toString(), 2)
                            .thenCompose(text -> {
                                String cleanedText = Function.removeOuterParentheses(text);
                                return API.getWordEnglish(cleanedText)
                                        .thenAccept(apiResult -> {
                                            Word newWord = apiResult.getWord();
                                            List<WordDetail> wordDetailList = apiResult.getWordDetailList();
                                            adapter.setData(wordDetailList);
                                            recyclerView.setAdapter(adapter);

                                            txtWord.setText(newWord.getOriginal_text());
                                            txtPhonetic.setText(newWord.getPhonetic());
                                            txtTranslated.setText(Function.removeOuterParentheses(newWord.getTranslated_text()));
                                            // gọi api xem word đã có trong table chưa, api trả về true/false
//                                // nếu có thì ko lưu
//                                // ko có thì lưu word vào db
//                                // ko có thì lưu word vào db
//                                MyDB db = MyDB.getInstance(getApplicationContext());
//                                if(!db.isWordExists(newWord.getOriginal_text())){
//                                    // lưu vào db
//                                    db.addWord(newWord);
//                                    Log.d("MyDB", "done");
//                                }else {
//                                    Log.d("MyDB", "Từ đã tồn tại trong cơ sở dữ liệu: " + newWord.getOriginal_text());
//                                }
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