package com.dictionary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dictionary.MainActivity;
import com.dictionary.R;
import com.dictionary.api.API;
import com.dictionary.api.Meaning;
import com.dictionary.api.WordDetail;
import com.dictionary.db.MyDB;
import com.dictionary.model.Word;
import com.google.android.material.tabs.TabLayout;

public class WordTrans extends AppCompatActivity {
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
        setContentView(R.layout.word_trans);

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
//                    Intent i = new Intent(WordTrans.this, MainActivity.class);
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
                    API.getWordEnglish(searchEditText.getText().toString())
                            .thenAccept(apiResult -> {
                                Word newWord = apiResult.getWord();
                                WordDetail wordDetail = apiResult.getWord_detail();

                                // in ra màn hình trên đầu
                                String original_text = newWord.getOriginal_text();
                                String translate_text = newWord.getTranslated_text();
                                String phonetic = newWord.getPhonetic();
                                String audio = newWord.getAudio();

                                // sau đây là lặp qua các loại từ và in ra màn hình
                                // lặp qua bao nhiêu in bấy nhiêu.
                                for(Meaning meaning : wordDetail.getMeanings()){
                                    String type = meaning.getPartOfSpeech();
                                    String definition = meaning.getDefinitions().get(0).getDefinition();
                                    String example = meaning.getDefinitions().get(0).getExample();
                                    String synonyms = "";
                                    for(String synonym : meaning.getSynonyms()){
                                        synonyms += synonym + ", ";
                                    }
                                    String antonyms = "";
                                    for(String antonym : meaning.getAntonyms()){
                                        antonyms += antonym + ", ";
                                    }
                                }


//                                Word newWord = word;
//                                txtWord.setText(newWord.getOriginal_text());
//                                txtTranslated.setText(newWord.getTranslated_text());
////                                txtDefination.setText(newWord.getDefinition());
////                                txtSyn.setText(newWord.getSynonyms());
////                                txtAnt.setText(newWord.getAntonyms());
////                                txtExample.setText(newWord.getExample());
//                                // gọi api xem word đã có trong table chưa, api trả về true/false
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
                }
                return false;
            }
        });

        // nếu ấn mark word
        // gọi api lấy ra word để ấy id của word
        // sau đó gọi api đdánh dấu mark word
        // khi đánh dấu thành công thì thay đổi màu ngôi sao
        // note: cách gọi ra db như sau
    }
}