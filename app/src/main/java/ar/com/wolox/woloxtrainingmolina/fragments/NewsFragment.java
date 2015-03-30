package ar.com.wolox.woloxtrainingmolina.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;

import ar.com.wolox.woloxtrainingmolina.R;
import ar.com.wolox.woloxtrainingmolina.entities.ItemNews;
import ar.com.wolox.woloxtrainingmolina.ui.NewsRecyclerViewAdapter;

public class NewsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FloatingActionButton mFab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_news);
        mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh_layout);

        // todo dummy data for testing purposes
        ItemNews itemsData[] = {
                new ItemNews("Nicola Dille", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, true, "15m"),
                new ItemNews("Carmelina Teston", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, false, "18m"),
                new ItemNews("Sanford Hamrick", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, true, "32m"),
                new ItemNews("Jina Hersom", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, false, "42m"),
                new ItemNews("Brendan Nemeth", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, true, "9m"),
                new ItemNews("Stanton Riggenbach", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, false, "17m"),
                new ItemNews("Shaunna Drozd", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, true, "14m"),
                new ItemNews("Thresa Lashley", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, false, "29m"),
                new ItemNews("Shante Evensen", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, true, "35m"),
                new ItemNews("Jesus Sera", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, false, "52m"),
                new ItemNews("Kathryn Seawright", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, true, "7m"),
                new ItemNews("Jacquline Rochelle", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, false, "22m")};

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        NewsRecyclerViewAdapter mAdapter = new NewsRecyclerViewAdapter(itemsData);
        mRecyclerView.setAdapter(mAdapter);
        // todo customize animations extending RecyclerView.ItemAnimator class
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mFab.attachToRecyclerView(mRecyclerView); //this is for the mFab animation

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
    }

    void refreshItems() {
        // Load items
        // ...

        // Load complete
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
