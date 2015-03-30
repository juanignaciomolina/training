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
                new ItemNews("Ali Connors 1",R.drawable.item_news_placeholder),
                new ItemNews("Ali Connors 2",R.drawable.item_news_placeholder),
                new ItemNews("Ali Connors 3",R.drawable.item_news_placeholder),
                new ItemNews("Ali Connors 4",R.drawable.item_news_placeholder),
                new ItemNews("Ali Connors 5",R.drawable.item_news_placeholder),
                new ItemNews("Ali Connors 6",R.drawable.item_news_placeholder),
                new ItemNews("Ali Connors 7",R.drawable.item_news_placeholder),
                new ItemNews("Ali Connors 8",R.drawable.item_news_placeholder),
                new ItemNews("Ali Connors 9",R.drawable.item_news_placeholder),
                new ItemNews("Ali Connors 10",R.drawable.item_news_placeholder),
                new ItemNews("Ali Connors 11",R.drawable.item_news_placeholder),
                new ItemNews("Ali Connors 12",R.drawable.item_news_placeholder)};

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        NewsRecyclerViewAdapter mAdapter = new NewsRecyclerViewAdapter(itemsData);
        recyclerView.setAdapter(mAdapter);
        // todo customize animations extending RecyclerView.ItemAnimator class
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        fab.attachToRecyclerView(recyclerView); //this is for the fab animation
    }
}
