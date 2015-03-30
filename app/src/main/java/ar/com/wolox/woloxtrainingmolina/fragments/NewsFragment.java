package ar.com.wolox.woloxtrainingmolina.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_news);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);

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

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        NewsRecyclerViewAdapter mAdapter = new NewsRecyclerViewAdapter(itemsData);
        recyclerView.setAdapter(mAdapter);
        // todo customize animations extending RecyclerView.ItemAnimator class
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        fab.attachToRecyclerView(recyclerView); //this is for the fab animation
    }
}
