package com.dictionary.activity;

import android.annotation.SuppressLint;
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
import com.dictionary.R;
import com.dictionary.api.API;

public class TextTrans extends AppCompatActivity {
    private Button btnTran;
    private Button btnEngtoVi;
    private EditText textorigin;
    private TextView txtTranslate;
    Toolbar toolbar;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_trans);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Loại bỏ tiểu đề mặc định
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
        btnTran = findViewById(R.id.btnAnhViet);
        btnEngtoVi = findViewById(R.id.btnVietAnh);
        btnTran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API.getTranslate(textorigin.getText().toString(),1).thenAccept(text -> {

                    txtTranslate.setText(text);
                    // lấy text ở đây hiện lên màn hình,code ở trong đây, ko code ở ngoài.
                }).exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;
                });
            }
        });
        btnEngtoVi.setOnClickListener( v ->{
            API.getTranslate(textorigin.getText().toString(),2).thenAccept(text -> {

                txtTranslate.setText(text);
                // lấy text ở đây hiện lên màn hình,code ở trong đây, ko code ở ngoài.
            }).exceptionally(throwable -> {
                throwable.printStackTrace();
                return null;
            });
        });

        // call api for text translate
        // lấy text đưa vào đây và sử lý trong thenAccept
//        API.getTranslate("nguyen is a dog!").thenAccept(text -> {
//            System.out.println(text);
//            // lấy text ở đây hiện lên màn hình,code ở trong đây, ko code ở ngoài.
//        }).exceptionally(throwable -> {
//            throwable.printStackTrace();
//            return null;
//        });
    }
}
