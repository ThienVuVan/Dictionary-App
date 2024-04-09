package com.dictionary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.dictionary.R;
import com.dictionary.activity.HistoryAdapter;
import com.dictionary.db.MyDB;
import com.dictionary.model.Word;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private Button btnDel;
    private  Button btnSelectAll;
    private EditText searchtext;
    private ListView listViewHistory;
    private ArrayList<Word>  listWordHitory;
    private HistoryAdapter adapter;
    private MyDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        btnDel = findViewById(R.id.btnDel);
        btnSelectAll = findViewById(R.id.btnSelectAll);
        listViewHistory = findViewById(R.id.listHistory);
        searchtext = findViewById(R.id.searchText);
        db = new MyDB(HistoryActivity.this);
        listWordHitory = db.getAllWords();
        btnSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HistoryActivity.this,"hehe",Toast.LENGTH_LONG).show();
                selectAll();
            }
        });
        listViewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("message","item click");
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
}