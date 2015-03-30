package ar.com.wolox.woloxtrainingmolina.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ar.com.wolox.woloxtrainingmolina.R;
import ar.com.wolox.woloxtrainingmolina.entities.ItemNews;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

    private ItemNews[] itemsNews;

    public NewsRecyclerViewAdapter(ItemNews[] itemsNews) {
        this.itemsNews = itemsNews;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NewsRecyclerViewAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.txtViewTitle.setText(itemsNews[position].getTitle());
        viewHolder.imgViewIcon.setImageResource(itemsNews[position].getImageUrl());
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;
        public ImageView imgViewIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.item_icon);
        }
    }

    // Return the size of your itemsNews (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsNews.length;
    }

}
