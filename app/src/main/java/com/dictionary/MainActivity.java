package com.dictionary;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.dictionary.activity.HistoryActivity;
import com.dictionary.activity.Setting;
import com.dictionary.activity.TextTrans;
import com.dictionary.activity.WordTrans;
import com.dictionary.activity.YourWordActivity;
import com.dictionary.api.API;

public class MainActivity extends AppCompatActivity {
    private Button btnVietAnh,btnTextTrans,btnYourword,btnHistory,btnSetting;
    private ImageButton btnSearch;
    private EditText txtSearch;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = findViewById(R.id.backButtontext);
        btnVietAnh = findViewById(R.id.btnVietAnh);
        btnTextTrans = findViewById(R.id.btnTextTrans);
        btnYourword = findViewById(R.id.btnYourword);
        btnHistory = findViewById(R.id.btnHistory);
        btnSetting = findViewById(R.id.btnSetting);
        txtSearch = findViewById(R.id.txtSearch);

        toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
//        // Loại bỏ tiểu đề mặc định
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//
//        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"ádđ",Toast.LENGTH_SHORT).show();
//            }
//        });

        // call api for word
        API.getWord("hello").thenAccept(word -> {
            System.out.println(word);
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });

        // call api for text translate
        API.getTranslate("nguyen is a dog!").thenAccept(text -> {
            System.out.println(text);
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });

        //Intent điều hướng
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, WordTrans.class);
                Bundle b = new Bundle();
                b.putString("word",txtSearch.getText().toString());
                i.putExtras(b);
                MainActivity.this.startActivity(i);
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Setting.class);
                startActivity(i);
            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(i);
            }
        });
        btnYourword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, YourWordActivity.class);
                startActivity(i);
            }
        });
        btnTextTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TextTrans.class);
                startActivity(i);
            }
        });
        btnVietAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, WordTrans.class);
                startActivity(i);
            }
        });
    }
}
