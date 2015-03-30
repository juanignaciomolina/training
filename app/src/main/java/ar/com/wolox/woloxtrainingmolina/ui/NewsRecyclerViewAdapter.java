package ar.com.wolox.woloxtrainingmolina.ui;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ar.com.wolox.woloxtrainingmolina.Config;
import ar.com.wolox.woloxtrainingmolina.R;
import ar.com.wolox.woloxtrainingmolina.entities.ItemNews;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRowViewHolder> {

    private ItemNews[] mItemsNews;

    private NewsRowViewHolder mNewsRowViewHolder;

    public NewsRecyclerViewAdapter(ItemNews[] itemsNews) {
        this.mItemsNews = itemsNews;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NewsRowViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, null);
        mNewsRowViewHolder = new NewsRowViewHolder(itemLayoutView, mViewHolderClickListener);
        return mNewsRowViewHolder;
    }

    NewsRowViewHolder.ViewHolderClicks mViewHolderClickListener = new NewsRowViewHolder.ViewHolderClicks() {
        @Override
        public void onClickRow(View caller) {
            Log.d(Config.LOG_DEBUG,"On Row Click");
        }

        @Override
        public void onClickLike(ImageView callerImage) {
            Log.d(Config.LOG_DEBUG, "On Like Click");
        }
    };

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(NewsRowViewHolder viewHolder, int position) {
        viewHolder.mTitle.setText(mItemsNews[position].getTitle());
        viewHolder.mContent.setText(mItemsNews[position].getContent());
        viewHolder.mImage.setImageResource(mItemsNews[position].getImageUrl());
        if (mItemsNews[position].getLike()) viewHolder.mLike.setImageResource(R.drawable.likeon);
        else viewHolder.mLike.setImageResource(R.drawable.likeoff);
        viewHolder.mDate.setText(mItemsNews[position].getDate());
    }

    // Return the size of your mItemsNews (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mItemsNews.length;
    }

}
