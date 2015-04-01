package ar.com.wolox.woloxtrainingmolina.ui;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import ar.com.wolox.woloxtrainingmolina.Config;
import ar.com.wolox.woloxtrainingmolina.R;
import ar.com.wolox.woloxtrainingmolina.entities.News;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRowViewHolder> {

    private ArrayList<News> mItemsNews = new ArrayList<News>();

    private NewsRowViewHolder mNewsRowViewHolder;

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
        viewHolder.mTitle.setText(mItemsNews.get(position).getTitle());
        viewHolder.mContent.setText(mItemsNews.get(position).getText());
        viewHolder.mImage.setImageResource(R.drawable.item_news_placeholder); //todo desharcodear esto
        if (true) viewHolder.mLike.setImageResource(R.drawable.likeon); //todo desharcodear esto
        else viewHolder.mLike.setImageResource(R.drawable.likeoff);
        viewHolder.mDate.setText("15m");//todo desharcodear esto
    }

    // Return the size of your mItemsNews (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mItemsNews.size();
    }

    //Dataset manipulators
    public void addNews(int position, News newsToInsert) {
        mItemsNews.add(position, newsToInsert);
        notifyItemInserted(position);
    }

    public void addNewsArray(News[] newsArray) {
        for(News item : newsArray) {
            addNews(getItemCount(), item);
        }
    }

    public void removeNewsByPos(int position) {
        mItemsNews.remove(position);
        notifyItemRemoved(position);
    }

    public int getNewsPosition(News newsToLocate) {
        return mItemsNews.indexOf(newsToLocate);
    }

    public void removeNews(News newsToRemove) {
        removeNewsByPos(getNewsPosition(newsToRemove));
    }

    private void moveNews(int fromPos, int toPos) {
        News temp = mItemsNews.remove(fromPos);
        mItemsNews.add(toPos, temp);
    }

}
