package ar.com.wolox.woloxtrainingmolina.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import ar.com.wolox.woloxtrainingmolina.Config;
import ar.com.wolox.woloxtrainingmolina.R;
import ar.com.wolox.woloxtrainingmolina.ui.ViewPagerAdapter;
import ar.com.wolox.woloxtrainingmolina.ui.widget.SlidingTabLayout;

public class MainActivity extends ActionBarActivity {

    // Declaring Your View and Variables

    Toolbar mToolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"NEWS","PROFILE"};
    int mImageResources[]={R.drawable.newsoff, R.drawable.profileoff};
    int Numboftabs =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs, mImageResources);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setCustomTabView(R.layout.tab, R.id.tab_tv, R.id.tab_img);

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

    }

    private void setToolbar() {
        // Set a toolbar to replace the action bar.
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setElevation(2);
        getSupportActionBar().setTitle(null); //El title lo ponemos en toolbar_title sino queda a la izquierda del logo
        ImageView logo = (ImageView) findViewById(R.id.toolbar_logo);
        logo.setImageResource(R.drawable.topbarlogo);
        TextView activity_name = (TextView) findViewById(R.id.toolbar_title);
        activity_name.setText(R.string.general_company_name);
    }

}
