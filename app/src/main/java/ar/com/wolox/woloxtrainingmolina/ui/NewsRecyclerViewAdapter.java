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

    private ItemNews[] mItemsNews;

    public NewsRecyclerViewAdapter(ItemNews[] itemsNews) {
        this.mItemsNews = itemsNews;
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

        viewHolder.mTitle.setText(mItemsNews[position].getTitle());
        viewHolder.mContent.setText(mItemsNews[position].getContent());
        viewHolder.mImage.setImageResource(mItemsNews[position].getImageUrl());
        if (mItemsNews[position].getLike()) viewHolder.mLike.setImageResource(R.drawable.likeon);
        else viewHolder.mLike.setImageResource(R.drawable.likeoff);
        viewHolder.mDate.setText(mItemsNews[position].getDate());
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle;
        public TextView mContent;
        public ImageView mImage;
        public ImageView mLike;
        public TextView mDate;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
            mContent = (TextView) itemLayoutView.findViewById(R.id.item_content);
            mImage = (ImageView) itemLayoutView.findViewById(R.id.item_image);
            mLike = (ImageView) itemLayoutView.findViewById(R.id.item_like);
            mDate = (TextView) itemLayoutView.findViewById(R.id.item_date);
        }
    }

    // Return the size of your mItemsNews (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mItemsNews.length;
    }

}
