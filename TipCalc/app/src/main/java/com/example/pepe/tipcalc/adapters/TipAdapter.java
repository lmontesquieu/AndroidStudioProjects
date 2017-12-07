package com.example.pepe.tipcalc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pepe.tipcalc.R;
import com.example.pepe.tipcalc.model.TipRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pepe on 10/3/2016.
 */

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.ViewHolder> {

    private List<TipRecord> dataset;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public TipAdapter(Context context, List<TipRecord> dataset, OnItemClickListener onItemClickListener) {
        this.dataset = dataset;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public TipAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.dataset = new ArrayList<TipRecord>();
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TipRecord element = dataset.get(position);
        String strTip = String.format(
                        context.getString(R.string.global_message_tip),
                        element.getTip());
        holder.txtListTip.setText(strTip);
        holder.txtListDate.setText(element.getDateFormatted());
        holder.setOnItemClickListener(element,onItemClickListener);
    }

    public void add(TipRecord record){
        dataset.add(0,record);
        notifyDataSetChanged();
    }

    public void clear(){
        dataset.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtListTip;
        TextView txtListDate;

        public ViewHolder(View itemView) {
            super(itemView);
            txtListTip = (TextView) itemView.findViewById(R.id.txtListTip);
            txtListDate = (TextView) itemView.findViewById(R.id.txtListDate);
        }

        public void setOnItemClickListener(final TipRecord element, final OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(element);
                }
            });
        }
    }
}
