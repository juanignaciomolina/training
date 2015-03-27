package ar.com.wolox.woloxtrainingmolina.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import ar.com.wolox.woloxtrainingmolina.R;
import ar.com.wolox.woloxtrainingmolina.ui.ViewPagerAdapter;
import ar.com.wolox.woloxtrainingmolina.ui.widget.SlidingTabLayout;

public class MainActivity extends ActionBarActivity {

    Toolbar mToolbar;
    ViewPager mPager;
    ViewPagerAdapter mAdapter;
    SlidingTabLayout mTabs;
    int mNumbOfTabs = 2;
    CharSequence mTitles[] = new CharSequence[mNumbOfTabs];
    int mImageResources[] = new int[mNumbOfTabs];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        initVars();
        initTabs();

    }

    private void initVars(){
        mTitles[0] = getString(R.string.fragment_news_name);
        mTitles[1] = getString(R.string.fragment_profile_name);
        mImageResources[0] = R.drawable.tab_news_img_selector;
        mImageResources[1] = R.drawable.tab_profile_img_selector;
    }

    private void initTabs(){

        //Se crea el ViewPagerAdapter y se le pasa el fragmentManager, los titulos, imagenes de las tabs y la cantidad de tabs
        mAdapter =  new ViewPagerAdapter(getSupportFragmentManager(),mTitles, mNumbOfTabs, mImageResources);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        //Tabs con una custom view (tab.xml)
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        mTabs.setCustomTabView(R.layout.tab, R.id.tab_tv, R.id.tab_img);

        //Color custom para el scroll de tabs
        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Se le asigna el ViewPager al SlidingTabLayout
        mTabs.setViewPager(mPager);
    }

    private void setToolbar() {
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
