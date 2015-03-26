package ar.com.wolox.woloxtrainingmolina.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ar.com.wolox.woloxtrainingmolina.Config;
import ar.com.wolox.woloxtrainingmolina.R;
import ar.com.wolox.woloxtrainingmolina.api.LogInService;
import ar.com.wolox.woloxtrainingmolina.api.ParseAPIHelper;
import ar.com.wolox.woloxtrainingmolina.entities.User;
import ar.com.wolox.woloxtrainingmolina.ui.ConnectingDialog;
import ar.com.wolox.woloxtrainingmolina.utils.InputCheckHelper;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LogInActivity extends FragmentActivity implements Callback<User> {

    private Context mContext;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mPreferencesEditor;

    private EditText mMail;
    private EditText mPassword;
    private Button mLogIn;
    private Button mSignUp;
    private TextView mToS;

    private ParseAPIHelper mAPIHelper;
    private LogInService mLogInService;
    private RestAdapter mRestAdapter;

    private User mUser;

    private boolean mActivityIsVisible;

    private FragmentManager mFragmentManager;
    private ConnectingDialog mConnectingDialogInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mContext = this;

        initPreferences(); //Preparar lo necesario para usar las SharedPreferences
        setUi(); //findViewsById
        setListeners(); //Bindear listeners a los botones
        initUi(); //Cargar valores default de la UI
        initApiConnection();
        initFragments(); //Preparar fragmentManager y fragments
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initUi(); //Esto va en el onRestart() por si cambiaron los datos de las shared preferences
    }

    @Override
    protected void onStart() {
        super.onStart();
        mActivityIsVisible = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mActivityIsVisible = false;
    }

    private void initPreferences() {
        mPreferences = mContext.getSharedPreferences(Config.LOGIN_PREFERENCES_KEY, Context.MODE_PRIVATE);
        mPreferencesEditor = mPreferences.edit(); //Traemos un editor para las preferences
    }

    private void setUi() {
        mMail = (EditText) findViewById(R.id.et_email);
        mPassword = (EditText) findViewById(R.id.et_password);
        mLogIn = (Button) findViewById(R.id.btn_login);
        mSignUp = (Button) findViewById(R.id.btn_signup);
        mToS = (TextView) findViewById(R.id.tv_tos);
    }

    private void setListeners() {
        mLogIn.setOnClickListener(logInClickListener);
        mSignUp.setOnClickListener(signUpClickListener);
        mToS.setOnClickListener(tosClickListener);
    }

    private void initUi() {
        //Si existen, se traen los valores guardados previamente para la dirección de email y el password
        String prefEmail = mPreferences.getString(Config.LOGIN_EMAIL_KEY, null);
        String prefPassword = mPreferences.getString(Config.LOGIN_PASSWORD_KEY, null);
        if (prefEmail != null) mMail.setText(prefEmail);
        if (prefPassword != null) mPassword.setText(prefPassword);
    }

    private void initApiConnection() {
        //Preparamos una conexión a la API de Parse
        mAPIHelper = new ParseAPIHelper();
        mRestAdapter = mAPIHelper.getRestAdapter();
        mLogInService = mRestAdapter.create(LogInService.class);
    }

    private void initFragments() {
        mFragmentManager = getSupportFragmentManager();
        mConnectingDialogInstance = new ConnectingDialog();
    }

    private void blockUi() {
        if (mActivityIsVisible) {
            mLogIn.setEnabled(false);
            mLogIn.setTextColor(getResources().getColor(R.color.gray));
            mSignUp.setEnabled(false);
            mSignUp.setTextColor(getResources().getColor(R.color.gray));
            mMail.setEnabled(false);
            mPassword.setEnabled(false);
            mConnectingDialogInstance.show(mFragmentManager, "Spinner_fragment_tag");
        }
    }

    private void unlockUi() {
        if (mActivityIsVisible) {
            mLogIn.setEnabled(true);
            mLogIn.setTextColor(getResources().getColor(R.color.black));
            mSignUp.setEnabled(true);
            mSignUp.setTextColor(getResources().getColor(R.color.white));
            mMail.setEnabled(true);
            mPassword.setEnabled(true);
            mConnectingDialogInstance.dismiss();
        }
    }

    View.OnClickListener logInClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String mail = mMail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            //Regla: Todos los campos son requeridos
            if (mail.isEmpty() || password.isEmpty()) {
                showToast(getString(R.string.login_require_all));
                return;
            }

            //Regla: La dirección de email debe ser válida
            if (!InputCheckHelper.isValidEmail(mail)) {
                mMail.setError(getString(R.string.login_not_valid_email));
                return;
            }

            mPreferencesEditor.putString(Config.LOGIN_EMAIL_KEY, mail);
            mPreferencesEditor.putString(Config.LOGIN_PASSWORD_KEY, password);
            mPreferencesEditor.apply(); //Nota: se usa apply() en lugar de commit() porque apply() trabaja en el background

            doLogIn(mail, password);
        }
    };

    View.OnClickListener signUpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(mContext, SignUpActivity.class));
        }
    };

    View.OnClickListener tosClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Config.ToS_URL))); //La URL de los ToS esta guardada en la clase Config
        }
    };

    private void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    private void doLogIn(String email, String password) {
        mUser = new User();
        mUser.setUsername(email);
        mUser.setPassword(password);

        mLogInService.logIn(email, password, this);
        Log.d(Config.LOG_DEBUG, "(Retrofit) Log in request send");
        blockUi();
    }

    // **Inicio RETROFIT CALLBACKS**
    @Override
    public void success(User user, Response response) {
        unlockUi();
        if (response.getStatus() == 200) {
            mUser.setSessionToken(user.getSessionToken());
            mPreferencesEditor.putString(Config.LOGIN_SESSION_KEY, mUser.getSessionToken());
            mPreferencesEditor.apply();
            showToast(getString(R.string.login_welcome)); //TODO En lugar de mostrar el mensaje abrir la activity principal
        }
    }

    @Override
    public void failure(RetrofitError error) {
        Log.e(Config.LOG_ERROR, error.getMessage());
        unlockUi();
        mUser = (User) error.getBody();
        if (mUser == null) {
            showToast(getString(R.string.login_unable_to_connect));
            return;
        }
        if (mUser.getCode().contains("101")) showToast(getString(R.string.login_wrong_credentials)); //Error 101: Usuario y/o contraseña invalidos
    }
    // **Fin RETROFIT CALLBACKS**

}
