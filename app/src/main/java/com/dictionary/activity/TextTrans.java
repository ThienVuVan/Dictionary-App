package com.dictionary.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.dictionary.MainActivity;
import com.dictionary.R;
import com.dictionary.api.API;
import com.dictionary.api.Function;

public class TextTrans extends AppCompatActivity {
    private Button btnTran;
    private Button btnEngtoVi;
    private EditText textorigin;
    private TextView txtTranslate;
    Toolbar toolbar;
    private ImageButton btnBack, btnClose, btnCopy;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_trans);

        toolbar = findViewById(R.id.toolbar);
        btnBack = findViewById(R.id.backButtontext);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TextTrans.this,"ádđ",Toast.LENGTH_SHORT).show();
            }
        });
      
        textorigin = findViewById(R.id.plain_text_input);
        txtTranslate = findViewById(R.id.txtTranslate);
        btnClose = findViewById(R.id.closeButton);
        btnCopy = findViewById(R.id.copyButton);

        btnTran = findViewById(R.id.btnAnhViet);
        btnEngtoVi = findViewById(R.id.btnVietAnh);
        btnTran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API.getTranslate(textorigin.getText().toString(),1).thenAccept(text -> {
                    String cleanText = Function.removeOuterParentheses(text);
                    txtTranslate.setText(cleanText);
                }).exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;
                });
            }
        });
        btnEngtoVi.setOnClickListener( v ->{
            API.getTranslate(textorigin.getText().toString(),2).thenAccept(text -> {
                String cleanText = Function.removeOuterParentheses(text);
                txtTranslate.setText(cleanText);
            }).exceptionally(throwable -> {
                throwable.printStackTrace();
                return null;
            });
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textorigin.setText("");
            }
        });
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToCopy = txtTranslate.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", textToCopy);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Đã sao chép vào bộ nhớ tạm", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
