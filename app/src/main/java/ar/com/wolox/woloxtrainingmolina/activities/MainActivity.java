package ar.com.wolox.woloxtrainingmolina.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ar.com.wolox.woloxtrainingmolina.Config;
import ar.com.wolox.woloxtrainingmolina.R;
import ar.com.wolox.woloxtrainingmolina.ui.ViewPagerAdapter;
import ar.com.wolox.woloxtrainingmolina.ui.widget.SlidingTabLayout;
import ar.com.wolox.woloxtrainingmolina.utils.UiHelper;

public class MainActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private ViewPager mPager;
    private ViewPagerAdapter mAdapter;
    private SlidingTabLayout mTabs;
    private int mNumbOfTabs = 2;
    private CharSequence mTitles[] = new CharSequence[mNumbOfTabs];
    private int mImageResources[] = new int[mNumbOfTabs];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVars();
        initTabs();
        initUi();

    }

    private void initVars() {
        mTitles[0] = getString(R.string.fragment_news_name);
        mTitles[1] = getString(R.string.fragment_profile_name);
        mImageResources[0] = R.drawable.tab_news_img_selector;
        mImageResources[1] = R.drawable.tab_profile_img_selector;
    }

    private void initUi() {
        mToolbar = UiHelper.setToolbar(
                this,
                R.id.toolbar,
                R.id.toolbar_title,
                getString(R.string.general_company_name),
                R.id.toolbar_logo,
                R.drawable.topbarlogo);

        //Si la version del OS es LOLLIPOP en adelante se usa elevation, sino se
        //usa una elevation fake con una imagen con degrade. Hay que hacer esto
        //porque los SlidingTabLayout no son compatibles con elevation pre LOLLIPOP
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(R.id.support_elevation).setVisibility(View.GONE);
            mToolbar.setElevation(Config.UI_ELEVATION);
            mTabs.setElevation(Config.UI_ELEVATION);
        } else {
            getSupportActionBar().setElevation(0);
            findViewById(R.id.support_elevation).setVisibility(View.VISIBLE);
        }

    }

    private void initTabs() {

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

}
