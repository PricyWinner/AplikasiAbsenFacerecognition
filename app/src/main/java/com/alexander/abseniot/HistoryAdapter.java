package com.alexander.abseniot;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private ArrayList<String> times;
//    private ArrayList<Transaction> currentUsertransactions;
//    private ArrayList<Product> products;
//    private Product product_found;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView time;

        public ViewHolder(View view) {
            super(view);

            time = (TextView) view.findViewById(R.id.tv_checkinTime);
        }

        public TextView getTextView() {
            return time;
        }
    }

    public HistoryAdapter(ArrayList<String> times) {

        this.times = times;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.time.setText(times.get(position));
//
    }
    @Override
    public int getItemCount() {
        return times.size();
    }
}
