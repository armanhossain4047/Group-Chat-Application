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
    private final List<Boolean> isSentByMe;

    public MyAdapter(List<String> titles, List<String> subtitles, List<Boolean> isSentByMe) {
        this.titles = titles;
        this.subtitles = subtitles;
        this.isSentByMe = isSentByMe;
    }

    @Override
    public int getItemViewType(int position) {
        // Return 1 if sent by me, otherwise return 0
        return isSentByMe.get(position) ? 1 : 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            // Sent message layout
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
        } else {
            // Received message layout
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Safely set title and subtitle
        String title = titles.get(position);
        String subtitle = subtitles.get(position);

        if (title != null) {
            holder.titleTextView.setText(title);
        } else {
            holder.titleTextView.setText("No Title");
        }

        if (subtitle != null) {
            holder.subtitleTextView.setText(subtitle);
        } else {
            holder.subtitleTextView.setText("No Subtitle");
        }
    }

    @Override
    public int getItemCount() {
        return Math.min(titles.size(), subtitles.size());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView subtitleTextView;

        ViewHolder(View itemView) {
            super(itemView);

            // Initialize views safely
            titleTextView = itemView.findViewById(R.id.itemTitle);
            subtitleTextView = itemView.findViewById(R.id.itemSubtitle);

            // Add a check for null views to catch potential issues
            if (titleTextView == null || subtitleTextView == null) {
                throw new IllegalStateException("TextView not found in layout. Check your XML IDs.");
            }
        }
    }
}
