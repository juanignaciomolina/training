package ar.com.wolox.woloxtrainingmolina.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ar.com.wolox.woloxtrainingmolina.Config;
import ar.com.wolox.woloxtrainingmolina.R;
import ar.com.wolox.woloxtrainingmolina.TrainingApp;
import ar.com.wolox.woloxtrainingmolina.api.ParseAPIHelper;
import ar.com.wolox.woloxtrainingmolina.api.SignUpService;
import ar.com.wolox.woloxtrainingmolina.entities.*;
import ar.com.wolox.woloxtrainingmolina.ui.ConnectingDialog;
import ar.com.wolox.woloxtrainingmolina.utils.InputCheckHelper;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SignUpActivity extends ActionBarActivity implements Callback<User> {

    private TrainingApp mContext;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mPreferencesEditor;

    private EditText mMail;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mJoin;
    private TextView mToS;
    private Toolbar mToolbar;

    private SignUpService mSignUpService;

    private User mUser;

    private boolean mActivityIsVisible;

    private FragmentManager mFragmentManager;
    private ConnectingDialog mConnectingDialogInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mContext = (TrainingApp) getApplicationContext();

        initPreferences();
        setUi();
        setToolbar();
        setListeners();
        initApiConnection();
        initFragments();
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

    private void setUi() {
        mMail = (EditText) findViewById(R.id.et_email);
        mPassword = (EditText) findViewById(R.id.et_password);
        mConfirmPassword = (EditText) findViewById(R.id.et_password_confirm);
        mJoin = (Button) findViewById(R.id.btn_join);
        mToS = (TextView) findViewById(R.id.tv_tos);
    }

    private void initPreferences() {
        mPreferences = mContext.getSharedPreferences(Config.LOGIN_PREFERENCES_KEY, Context.MODE_PRIVATE);
        mPreferencesEditor = mPreferences.edit(); //Traemos un editor para las preferences
    }

    private void setToolbar() {
        // Set a toolbar to replace the action bar.
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setElevation(Config.UI_ELEVATION);
        getSupportActionBar().setTitle(null); //El title lo ponemos en toolbar_title sino queda a la izquierda del logo
        ImageView logo = (ImageView) findViewById(R.id.toolbar_logo);
        logo.setImageResource(R.drawable.topbarlogo);
        TextView activity_name = (TextView) findViewById(R.id.toolbar_title);
        activity_name.setText(R.string.title_activity_sign_up);
    }

    private void initApiConnection() {
        //Preparamos una conexión a la API de Parse
        mSignUpService = mContext.getRestAdapter().create(SignUpService.class);
    }

    private void setListeners() {
        mJoin.setOnClickListener(joinClickListener);
        mToS.setOnClickListener(tosClickListener);
    }

    private void initFragments() {
        mFragmentManager = getSupportFragmentManager();
        mConnectingDialogInstance = new ConnectingDialog();
    }

    private void blockUi() {
        if (mActivityIsVisible) {
            mJoin.setEnabled(false);
            mJoin.setTextColor(getResources().getColor(R.color.gray));
            mMail.setEnabled(false);
            mPassword.setEnabled(false);
            mConfirmPassword.setEnabled(false);
            mConnectingDialogInstance.show(mFragmentManager, "Spinner_fragment_tag");
        }
    }

    private void unlockUi() {
        if (mActivityIsVisible) {
            mJoin.setEnabled(true);
            mJoin.setTextColor(getResources().getColor(R.color.white));
            mMail.setEnabled(true);
            mPassword.setEnabled(true);
            mConfirmPassword.setEnabled(true);
            mConnectingDialogInstance.dismiss();
        }
    }

    View.OnClickListener joinClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String mail = mMail.getText().toString().trim();
            String password = mPassword.getText().toString();
            String confirmPassword = mConfirmPassword.getText().toString();

            //Regla: Todos los campos son requeridos
            if ( mail.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ) {
                showToast(getString(R.string.login_require_all));
                return;
            }

            //Regla: La dirección de email debe ser válida
            if (!InputCheckHelper.isValidEmail(mail)) {
                mMail.setError(getString(R.string.login_not_valid_email));
                return;
            }

            //Regla: El campo "contraseña" y "confirmar contraseña" deben coincidir
            if (!password.equals(confirmPassword)) {
                mConfirmPassword.setError(getString(R.string.signup_passwords_dont_match));
                return;
            }

            doSignUp(mail, password);
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

    private void doSignUp(String email, String password) {
        mUser = new User();
        mUser.setUsername(email);
        mUser.setPassword(password);

        mSignUpService.signUp(mUser, this);
        Log.d(Config.LOG_DEBUG, "(Retrofit) Log in request send");
        blockUi();
    }

    // **Inicio RETROFIT CALLBACKS**
    @Override
    public void success(User user, Response response) {
        unlockUi();
        if (response.getStatus() == 201) { //Status 201: Usuario creado
            //Nota: No se puede hacer this.mUser = user porque Parse no devuelve todos los atributos en el SignUp,
            //algunos atributos quedarían incompletos
            mUser.setCreatedAt(user.getCreatedAt());
            mUser.setObjectId(user.getObjectId());
            mUser.setSessionToken(user.getSessionToken());
            mPreferencesEditor.putString(Config.LOGIN_EMAIL_KEY, mUser.getUsername());
            mPreferencesEditor.putString(Config.LOGIN_PASSWORD_KEY, mUser.getPassword());
            mPreferencesEditor.putString(Config.LOGIN_SESSION_KEY, mUser.getSessionToken());
            mPreferencesEditor.apply();
            showToast("User created"); //TODO En lugar de mostrar el mensaje abrir la activity principal
        }
        //No debería haber ninguna situación en que la response sea del tipo success y aún así no se
        //haya creado el usuario. Si llegase a suceder esto por algún motivo extraño, se le avisa al usuario
        else {
            showToast(getString(R.string.error_connection_unknown));
            Log.e(Config.LOG_ERROR, "Unknown connection response: " + response.getStatus());
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
        if (mUser.getCode().contains("202")) showToast(getString(R.string.signup_invalid_username)); //Error 202: El usuario ya existe
    }
    // **Fin RETROFIT CALLBACKS**

}
