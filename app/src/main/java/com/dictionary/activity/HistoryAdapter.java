package com.dictionary.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;


import com.dictionary.R;
import com.dictionary.db.MyDB;
import com.dictionary.model.Word;

import java.io.IOException;
import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter implements Filterable {
    private Activity activity;
    private ArrayList<Word> data;
    private LayoutInflater inflater;
    private ArrayList<Word> dataBackup;

    public HistoryAdapter(Activity activity, ArrayList<Word> data) {
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
        txtDefine.setText(data.get(position).getDefinition());
        ImageButton audioBtn = v.findViewById(R.id.btnAudio);
        ImageButton addToYourWord = v.findViewById(R.id.btnAddToYourWord);
        if (data.get(position).getMark() == 1) {
            addToYourWord.setBackgroundResource(R.drawable.star_fill);
        } else {
            addToYourWord.setBackgroundResource(R.drawable.ic_favor);
        }

        audioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio(data.get(position).getAudio());
                Toast.makeText(activity,"test",Toast.LENGTH_SHORT).show();
            }
        });
        addToYourWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDB myDB = MyDB.getInstance(activity);
                if(data.get(position).getMark() == 1){
                    myDB.updateMark(data.get(position).getId(),0);
                    data.get(position).setMark(0);
                    updateData(data);

                }else{
                    myDB.updateMark(data.get(position).getId(),1);
                    data.get(position).setMark(1);
                    updateData(data);

                }
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,"test",Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }
    public void playAudio(String audioUrl){
        MediaPlayer mediaPlayer = new MediaPlayer();

        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build());

        try {
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Failed to play audio", Toast.LENGTH_SHORT).show();
        }

    }
    public void updateData(ArrayList<Word> newData){
        this.data = newData;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        Filter f =  new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults fr = new FilterResults();
                //backup du lieu: luu tam data vao databackup
                if(dataBackup == null){
                    dataBackup = new ArrayList<>(data);

                }
                //neu chuoi de filter la rong thi khoi phuc du lieu
                if(constraint == null || constraint.length() == 0){
                    fr.count = dataBackup.size();
                    fr.values = dataBackup;
                }
                //neu khong thi thuc hien filter
                else{
                    ArrayList<Word> newdata = new ArrayList<>();
                    for(Word c : dataBackup){
                        if(c.getOriginal_text().toLowerCase().contains(constraint.toString().toLowerCase())){
                            newdata.add(c);
                        }
                    }
                    fr.count = newdata.size();
                    fr.values=newdata;
                }
                return fr;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data = new ArrayList<Word>();
                ArrayList<Word> tmp = (ArrayList<Word>) results.values;
                data.addAll(tmp);
                notifyDataSetChanged();
            }
        };
        return f;
    }
}
