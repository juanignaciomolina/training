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

import java.util.ArrayList;
import java.util.List;

import ar.com.wolox.woloxtrainingmolina.R;
import ar.com.wolox.woloxtrainingmolina.TrainingApp;
import ar.com.wolox.woloxtrainingmolina.activities.MainActivity;
import ar.com.wolox.woloxtrainingmolina.api.NewsRequestAdapter;
import ar.com.wolox.woloxtrainingmolina.api.NewsService;
import ar.com.wolox.woloxtrainingmolina.entities.News;
import ar.com.wolox.woloxtrainingmolina.entities.RowNews;
import ar.com.wolox.woloxtrainingmolina.entities.User;
import ar.com.wolox.woloxtrainingmolina.ui.NewsRecyclerViewAdapter;
import ar.com.wolox.woloxtrainingmolina.utils.UiHelper;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewsFragment extends Fragment {

    private Activity mActivity;
    private NewsService mNewsService;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FloatingActionButton mFab;
    private LinearLayout mNoNewsHolder;
    private ProgressBar mProgressBar;

    private NewsRecyclerViewAdapter mNewsRecyclerViewAdapter;

    private User mUser;

    private RowNews mItemNews[] = {};
    //private News mNews[] = {};
    private List<News> mNews = new ArrayList<News>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = getActivity();

        initUi();
        initApiConnection();

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

    private void initUi() {
        mRecyclerView = (RecyclerView) mActivity.findViewById(R.id.recycler_view_news);
        mFab = (FloatingActionButton) mActivity.findViewById(R.id.fab);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mActivity.findViewById(R.id.swipe_refresh_layout);
        mNoNewsHolder = (LinearLayout) mActivity.findViewById(R.id.no_news_holder);
        mProgressBar = (ProgressBar) mActivity.findViewById(R.id.loading_indicator);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mNewsRecyclerViewAdapter= new NewsRecyclerViewAdapter(mNews);
        mRecyclerView.setAdapter(mNewsRecyclerViewAdapter);
        // todo customize animations extending RecyclerView.ItemAnimator class
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mFab.attachToRecyclerView(mRecyclerView); //this is for the mFab animation
        mFab.setOnClickListener(mFabClickListener);

        mSwipeRefreshLayout.setOnRefreshListener(mSwipeRefreshListener);
    }

    private void initApiConnection() {
        mNewsService = TrainingApp.getRestAdapter().create(NewsService.class);
    }

    private void populateUi() {
        mProgressBar.setVisibility(View.GONE);
        mFab.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        if (mNews != null && mNews.size() > 0) displayNoNews(false);
        else displayNoNews(true);
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

    private void doGetNews() {
        mNewsService.getNews(0, 5, mGetNewsCallback);
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

    //
    private void addNewsIterator(News[] from, List to) {
        for( News news : from) {
            to.add(news);
        }
    }

    // ** EVENT BUS **

    public void onEvent(MainActivity.LogInEvent event){
        this.mUser = event.mUser;
        populateUi();
        doGetNews();
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

    Callback<NewsRequestAdapter> mGetNewsCallback = new Callback<NewsRequestAdapter>() {

        @Override
        public void success(NewsRequestAdapter newsRequestAdapter, Response response) {
            mNews.clear();
            addNewsIterator(newsRequestAdapter.getResults(), mNews);
            //mNews = newsRequestAdapter.getResults();
            populateUi();
            mNewsRecyclerViewAdapter.notifyDataSetChanged();
        }

        @Override
        public void failure(RetrofitError error) {
            UiHelper.showToast(mActivity, error.getMessage());
        }
    };

    // ** End of ANONYMOUS CLASSES **
}
