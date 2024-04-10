package com.dictionary.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dictionary.R;
import com.dictionary.model.WordDetail;

import java.util.List;

public class RecyclerViewItem extends RecyclerView.Adapter<RecyclerViewItem.ItemViewHolder>{
    private Context mContext;
    private List<WordDetail> list;

    public RecyclerViewItem(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<WordDetail> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        WordDetail wordDetail = list.get(position);
        if(wordDetail == null){
            return;
        }
        holder.txtType.setText(wordDetail.getType());
        holder.txtDefination.setText(wordDetail.getDefinition());
        holder.txtSyn.setText(wordDetail.getSynonyms());
        holder.txtAnt.setText(wordDetail.getAntonyms());
        holder.txtExample.setText(wordDetail.getExample());

    }

    @Override
    public int getItemCount() {
        if(list!= null){
            return list.size();
        }
        return 0;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView txtType;
        private TextView txtDefination;
        private TextView txtSyn;
        private TextView txtAnt;
        private TextView txtExample;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtType = itemView.findViewById(R.id.part_of_speech_textview);
            txtDefination = itemView.findViewById(R.id.definitions_textview);
            txtSyn = itemView.findViewById(R.id.synonyms_textview);
            txtAnt = itemView.findViewById(R.id.antonyms_textview);
            txtExample = itemView.findViewById(R.id.example_textview);

        }
    }

}
