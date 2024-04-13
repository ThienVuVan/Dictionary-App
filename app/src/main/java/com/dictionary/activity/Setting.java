package com.dictionary.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import com.dictionary.MainActivity;
import com.dictionary.R;
import com.dictionary.db.MyDB;

public class Setting extends AppCompatActivity {
    private ImageButton btnBack;
    private Button btnTheme,btnDelData,btnEmail;
    private MyDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btnBack = findViewById(R.id.backButton);
        btnTheme = findViewById(R.id.btnTheme);
        btnDelData = findViewById(R.id.btnDelData);
        btnEmail = findViewById(R.id.btnEmail);

        db = MyDB.getInstance(this);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            btnTheme.setText("Tắt chế độ ban đêm");
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            btnTheme.setText("Bật chế độ ban đêm");
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDarkModeOn){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("isDarkModeOn",false);
                    editor.apply();
                    btnTheme.setText("Bật chế độ ban đêm");
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("isDarkModeOn", true);
                    editor.apply();
                    btnTheme.setText("Tắt chế độ ban đêm");
                }
            }
        });
        btnDelData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);
                builder.setMessage("Ban Co muon soa khong");

                builder.setPositiveButton("co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteAllWords();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }

        });
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto: "+"dictionaryapp@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Báo lỗi và góp ý");
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(intent);
//                }
                startActivity(intent);
            }
        });
    }
}