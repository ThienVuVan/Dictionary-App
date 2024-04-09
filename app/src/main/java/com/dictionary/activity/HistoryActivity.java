package com.dictionary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.dictionary.R;
import com.dictionary.db.MyDB;
import com.dictionary.model.Word;
import com.google.android.material.appbar.MaterialToolbar;


import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private Button btnDel;

    private  Button btnSelectAll;
    private EditText searchtext;
    private ListView listViewHistory;
    private ArrayList<Word>  listWordHitory;
    private HistoryAdapter adapter;
    private MyDB db;
    private MaterialToolbar btnBack;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        btnDel = findViewById(R.id.btnDel);
        btnSelectAll = findViewById(R.id.btnSelectAll);
        listViewHistory = findViewById(R.id.listHistory);
        searchtext = findViewById(R.id.searchText);
        btnBack = findViewById(R.id.backButton);
        btnBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        db = MyDB.getInstance(this);
        listWordHitory = db.getAllWords();
        searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HistoryActivity.this,"hehe",Toast.LENGTH_LONG).show();
                selectAll();
            }
        });


        adapter = new HistoryAdapter(this,listWordHitory);
        listViewHistory.setAdapter(adapter);
    }

    public void selectAll(){
        if(btnSelectAll.getText().toString().equals("Đánh dấu tất cả")){
            for(int i=0;i<listViewHistory.getChildCount(); i++){
                View v = listViewHistory.getChildAt(i);
                CheckBox cb = v.findViewById(R.id.checkItem);
                cb.setChecked(true);
                btnSelectAll.setText("Bỏ qua");
            }
        }else{
            for(int i=0;i<listViewHistory.getChildCount(); i++){
                View v = listViewHistory.getChildAt(i);
                CheckBox cb = v.findViewById(R.id.checkItem);
                cb.setChecked(false);
                btnSelectAll.setText("Đánh dấu tất cả");
            }
        }

    }
    //delete word on database
    public void DeleteWord(int id){
        for (int i = listViewHistory.getChildCount() - 1; i >= 0; i--) {
            View v = listViewHistory.getChildAt(i);
            CheckBox cb = v.findViewById(R.id.checkItem);

            // Check if the checkbox is checked
            if (cb.isChecked()) {
                // Get the corresponding Word object
                Word word = listWordHitory.get(i);
                // Delete the word from the database using its ID
                db.deleteWord(word.getId());
                // Remove the word from the list
                listWordHitory.remove(i);
            }
        }
        // Notify the adapter that the dataset has changed
        adapter.notifyDataSetChanged();
        // Inform the user about the deletion
        Toast.makeText(HistoryActivity.this, "Selected words deleted", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }




    //delete word on database

}