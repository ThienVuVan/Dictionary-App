package com.dictionary.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.dictionary.R;
import com.dictionary.api.API;
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
                    // dũng sử lý back về home ở đây
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
                    API.getWordEnglish(searchEditText.getText().toString())
                            .thenAccept(word -> {
                                Word newWord = word;
                                txtWord.setText(newWord.getOriginal_text());
                                txtTranslated.setText(newWord.getTranslated_text());
                                txtDefination.setText(newWord.getDefinition());
                                txtSyn.setText(newWord.getSynonyms());
                                txtAnt.setText(newWord.getAntonyms());
                                txtExample.setText(newWord.getExample());
                                // lấy thông tin của word in lên màn hình, đồng thời lưu vào bảng word.
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