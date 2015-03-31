package ar.com.wolox.woloxtrainingmolina.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import ar.com.wolox.woloxtrainingmolina.Config;
import ar.com.wolox.woloxtrainingmolina.R;
import ar.com.wolox.woloxtrainingmolina.TrainingApp;
import ar.com.wolox.woloxtrainingmolina.api.LogInSessionService;
import ar.com.wolox.woloxtrainingmolina.entities.User;
import ar.com.wolox.woloxtrainingmolina.ui.ConnectingDialog;
import ar.com.wolox.woloxtrainingmolina.ui.ViewPagerAdapter;
import ar.com.wolox.woloxtrainingmolina.ui.widget.SlidingTabLayout;
import ar.com.wolox.woloxtrainingmolina.utils.UiHelper;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ActionBarActivity {

    private Context mContext;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mPreferencesEditor;
    private LogInSessionService mLogInSessionService;

    private Toolbar mToolbar;
    private ViewPager mPager;
    private ViewPagerAdapter mAdapter;
    private SlidingTabLayout mTabs;
    private int mNumbOfTabs = 2;
    private CharSequence mTitles[] = new CharSequence[mNumbOfTabs];
    private int mImageResources[] = new int[mNumbOfTabs];

    private User mUser;
    private String mEmail;
    private String mPassword;
    private String mSessionToken;

    private FragmentManager mFragmentManager;
    private ConnectingDialog mConnectingDialogInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        initPreferences();
        initFragments();
        initVars();
        initTabs();
        initUi();

        checkLogInMethod();
    }

    private void initFragments() {
        mFragmentManager = getSupportFragmentManager();
        mConnectingDialogInstance = new ConnectingDialog();
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

        //If the OS version is LOLLIPOP or higher we use the elevation attribute,
        //otherwise we use a fake elevation with a degrade image. We have to do this
        //because the SlidingTabLayouts aren't compatible with elevation pre LOLLIPOP
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
        //We instance a ViewPagerAdapater and provide it with a fragmentManager,
        // tittles and images for the tabs and the total amount of tabs
        mAdapter =  new ViewPagerAdapter(mFragmentManager,mTitles, mNumbOfTabs, mImageResources);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        //Provide a custom view for the tabs (tab.xml)
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        mTabs.setCustomTabView(R.layout.tab, R.id.tab_tv, R.id.tab_img);

        //Custom color for the tabs scroll
        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        mTabs.setViewPager(mPager);
    }

    private void initPreferences() {
        mPreferences = mContext.getSharedPreferences(Config.LOGIN_PREFERENCES_KEY, Context.MODE_PRIVATE);
        mPreferencesEditor = mPreferences.edit();
    }

    private void lockUi() {
        mConnectingDialogInstance.show(mFragmentManager, "Spinner_fragment_tag");
    }

    private void unlockUi() {
        mConnectingDialogInstance.dismiss();
    }

    private void checkLogInMethod() {
        //Get the stored values of the email, password and session (in case they exist)
        mEmail = mPreferences.getString(Config.LOGIN_EMAIL_KEY, null);
        mPassword = mPreferences.getString(Config.LOGIN_PASSWORD_KEY, null);
        mSessionToken = mPreferences.getString(Config.LOGIN_SESSION_KEY, null);
        if (mEmail == null || mPassword == null || mSessionToken == null)
            UiHelper.startActivityClearStack(mContext, LogInActivity.class);
        else
            doLogIn();
    }

    private void initSessionApiConnection() {
        //Get a connection to the Parse API (with a user session token)
        //by requesting it to the app level class
        TrainingApp.getParseApiHelper().setSessionToken(mSessionToken);
        TrainingApp.setRestAdapter(null); //This forces the TrainingApp class to get a new Adapter
        mLogInSessionService = TrainingApp.getRestAdapter().create(LogInSessionService.class);
    }

    private void doLogIn() {
        initSessionApiConnection();
        lockUi();
        mLogInSessionService.sessionLogIn(mLogInSessionCallback);
    }

    // ** ANONYMOUS CLASSES **

    Callback<User> mLogInSessionCallback = new Callback<User>() {
        @Override
        public void success(User user, Response response) {
            unlockUi();
            if (response.getStatus() == 200) { //Status 200: Log in OK
                UiHelper.showToast(mContext, getString(R.string.login_welcome)); //TODO keep user logged in
            }
            //There should be no situation where in spite of the response type being success the user has not logged in.
            //If this happens for some strange reason, we let the user know that something went wrong.
            else {
                UiHelper.showToast(mContext, getString(R.string.error_connection_unknown));
                Log.e(Config.LOG_ERROR, "Unknown connection response: " + response.getStatus());
                UiHelper.startActivityClearStack(mContext, LogInActivity.class);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e(Config.LOG_ERROR, error.getMessage());
            mUser = (User) error.getBody();
            if (mUser == null) {
                UiHelper.showToast(
                        mContext,
                        getString(R.string.login_unable_to_connect));
                return;
            }
            //Error 209: Invalid session token
            if (mUser.getCode().contains("209")) {
                UiHelper.showToast(
                        mContext,
                        getString(R.string.main_activity_session_expired));
                UiHelper.startActivityClearStack(mContext, LogInActivity.class);
            }
        }
    };

    // ** End of ANONYMOUS CLASSES **

}
