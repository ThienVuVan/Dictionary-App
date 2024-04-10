package com.dictionary.activity;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;


import com.dictionary.R;
import com.dictionary.model.Word;

import java.io.IOException;
import java.util.ArrayList;

public class YourWordAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Word> data;
    private LayoutInflater inflater;

    public YourWordAdapter(Activity activity, ArrayList<Word> data) {
        this.activity = activity;
        this.data = data;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v ==null){
            v = inflater.inflate(R.layout.item_dic,null);
        }
        TextView txtWord =v.findViewById(R.id.txtWord);
        txtWord.setText(data.get(position).getOriginal_text());
        TextView txtDefine =v.findViewById(R.id.txtDefine);
//        txtDefine.setText(data.get(position).getDefinition());

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,"test",Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }


}
