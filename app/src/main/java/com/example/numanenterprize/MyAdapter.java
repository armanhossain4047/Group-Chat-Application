package com.example.numanenterprize;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private final List<String> titles;
    private final List<String> subtitles;

    public MyAdapter(List<String> titles, List<String> subtitles) {
        this.titles = titles;
        this.subtitles = subtitles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTextView.setText(titles.get(position));
        holder.subtitleTextView.setText(subtitles.get(position));
    }

    @Override
    public int getItemCount() {
        return subtitles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView subtitleTextView;

        ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.itemTitle);
            subtitleTextView = itemView.findViewById(R.id.itemSubtitle);
        }
    }
}
