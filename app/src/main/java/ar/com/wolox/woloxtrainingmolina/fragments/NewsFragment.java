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

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_news);

        // this is data fro recycler view
        ItemNews itemsData[] = {
                new ItemNews("Item: 1",R.drawable.topbarlogo),
                new ItemNews("Item: 2",R.drawable.topbarlogo),
                new ItemNews("Item: 3",R.drawable.topbarlogo),
                new ItemNews("Item: 4",R.drawable.topbarlogo),
                new ItemNews("Item: 5",R.drawable.topbarlogo),
                new ItemNews("Item: 6",R.drawable.topbarlogo),
                new ItemNews("Item: 7",R.drawable.topbarlogo),
                new ItemNews("Item: 8",R.drawable.topbarlogo),
                new ItemNews("Item: 9",R.drawable.topbarlogo),
                new ItemNews("Item: 10",R.drawable.topbarlogo),
                new ItemNews("Item: 11",R.drawable.topbarlogo),
                new ItemNews("Item: 12",R.drawable.topbarlogo)};

        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        // 3. create an adapter
        NewsRecyclerViewAdapter mAdapter = new NewsRecyclerViewAdapter(itemsData);
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.attachToRecyclerView(recyclerView);

    }
}
