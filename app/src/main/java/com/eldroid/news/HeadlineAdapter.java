package com.eldroid.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HeadlineAdapter extends RecyclerView.Adapter<HeadlineAdapter.HeadlineViewHolder> {

    private List<Headline> headlines;
    private OnHeadlineClickListener listener;

    public HeadlineAdapter(List<Headline> headlines, OnHeadlineClickListener listener) {
        this.headlines = headlines;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HeadlineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_headline, parent, false);
        return new HeadlineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadlineViewHolder holder, int position) {
        Headline headline = headlines.get(position);
        holder.headlineTitle.setText(headline.getTitle());
        holder.itemView.setOnClickListener(v -> listener.onHeadlineClick(headline));
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }

    public class HeadlineViewHolder extends RecyclerView.ViewHolder {

        TextView headlineTitle;

        public HeadlineViewHolder(@NonNull View itemView) {
            super(itemView);
            headlineTitle = itemView.findViewById(R.id.headlineTitle);
        }
    }

    public interface OnHeadlineClickListener {
        void onHeadlineClick(Headline headline);
    }
}

