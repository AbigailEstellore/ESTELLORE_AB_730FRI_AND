package com.eldroid.news;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsContentFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_content, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView newsContentTextView = view.findViewById(R.id.newsContentTextView);
        TextView newsBodyTextView = view.findViewById(R.id.newsBodyTextView);
        ImageView newsImageView = view.findViewById(R.id.newsImageView);

        if (getArguments() != null) {
            String headlineTitle = getArguments().getString("headline_title");
            String newsContent = getArguments().getString("news_content");
            int newsImageResId = getArguments().getInt("news_image_res_id");

            newsContentTextView.setText(headlineTitle);
            newsBodyTextView.setText(newsContent);
            newsImageView.setImageResource(newsImageResId);
        }
    }
}

