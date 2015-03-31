package ar.com.wolox.woloxtrainingmolina.fragments;

import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.melnykov.fab.FloatingActionButton;

import ar.com.wolox.woloxtrainingmolina.R;
import ar.com.wolox.woloxtrainingmolina.activities.MainActivity;
import ar.com.wolox.woloxtrainingmolina.entities.RowNews;
import ar.com.wolox.woloxtrainingmolina.entities.User;
import ar.com.wolox.woloxtrainingmolina.ui.NewsRecyclerViewAdapter;
import ar.com.wolox.woloxtrainingmolina.utils.UiHelper;
import de.greenrobot.event.EventBus;

public class NewsFragment extends Fragment {

    private Activity mActivity;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FloatingActionButton mFab;
    private LinearLayout mNoNewsHolder;
    private ProgressBar mProgressBar;

    private User mUser;

    private RowNews mItemNews[] = {};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = getActivity();

        initVars();
        initUi();

        setLoadingUi();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void initVars() {
        //todo dummy data for testing
        RowNews itemsNews[] = {
                new RowNews("Nicola Dille", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, true, "15m"),
                new RowNews("Carmelina Teston", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, false, "18m"),
                new RowNews("Sanford Hamrick", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, true, "32m"),
                new RowNews("Jina Hersom", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, false, "42m"),
                new RowNews("Brendan Nemeth", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, true, "9m"),
                new RowNews("Stanton Riggenbach", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, false, "17m"),
                new RowNews("Shaunna Drozd", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, true, "14m"),
                new RowNews("Thresa Lashley", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, false, "29m"),
                new RowNews("Shante Evensen", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, true, "35m"),
                new RowNews("Jesus Sera", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, false, "52m"),
                new RowNews("Kathryn Seawright", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, true, "7m"),
                new RowNews("Jacquline Rochelle", "I'll be in your neighborhood doing errands...", R.drawable.item_news_placeholder, false, "22m")};

        mItemNews = itemsNews;
    }

    private void initUi() {
        mRecyclerView = (RecyclerView) mActivity.findViewById(R.id.recycler_view_news);
        mFab = (FloatingActionButton) mActivity.findViewById(R.id.fab);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mActivity.findViewById(R.id.swipe_refresh_layout);
        mNoNewsHolder = (LinearLayout) mActivity.findViewById(R.id.no_news_holder);
        mProgressBar = (ProgressBar) mActivity.findViewById(R.id.loading_indicator);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        NewsRecyclerViewAdapter mAdapter = new NewsRecyclerViewAdapter(mItemNews);
        mRecyclerView.setAdapter(mAdapter);
        // todo customize animations extending RecyclerView.ItemAnimator class
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mFab.attachToRecyclerView(mRecyclerView); //this is for the mFab animation
        mFab.setOnClickListener(mFabClickListener);

        mSwipeRefreshLayout.setOnRefreshListener(mSwipeRefreshListener);
    }

    private void populateUi() {
        mProgressBar.setVisibility(View.GONE);
        mFab.setVisibility(View.VISIBLE);
        if (mItemNews != null && mItemNews.length > 0) {
            displayNoNews(false);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        } else
            displayNoNews(true);
    }

    private void displayNoNews(boolean state) {
        if (state) mNoNewsHolder.setVisibility(View.VISIBLE);
        else mNoNewsHolder.setVisibility(View.GONE);
    }

    private void setLoadingUi() {
        mSwipeRefreshLayout.setVisibility(View.GONE);
        mFab.setVisibility(View.GONE);
        displayNoNews(false);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void refreshItems() {
        // Load items
        // ...

        // Load complete
        onItemsLoadComplete();
    }

    private void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }

    // ** EVENT BUS **

    public void onEvent(MainActivity.LogInEvent event){
        this.mUser = event.mUser;
        populateUi();
    }

    // ** End of EVENT BUS **

    // ** ANONYMOUS CLASSES **

    View.OnClickListener mFabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            UiHelper.showToast(mActivity, "FAB Click");
        }
    };

    SwipeRefreshLayout.OnRefreshListener mSwipeRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            // Refresh items
            refreshItems();
        }
    };

    // ** End of ANONYMOUS CLASSES **
}
