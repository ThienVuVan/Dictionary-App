package com.dictionary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import com.dictionary.MainActivity;
import com.dictionary.R;

public class Setting extends AppCompatActivity {
    private ImageButton btnBack;
    private Switch swtTheme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btnBack = findViewById(R.id.backButton);
        swtTheme = findViewById(R.id.swtTheme);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(Setting.this, MainActivity.class);
//                startActivity(i);
                finish();
            }
        });
        swtTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(swtTheme.isChecked()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    swtTheme.setText("Chế độ ban đêm đang bật");
                }
                else {
                }
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    swtTheme.setText("Chế độ ban đêm đang tắt");
            }
        });
    }
}