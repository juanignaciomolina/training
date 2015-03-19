package ar.com.wolox.woloxtrainingmolina;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ar.com.wolox.woloxtrainingmolina.entities.Usuario;
import ar.com.wolox.woloxtrainingmolina.ui.ConectandoDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class loginActivity extends FragmentActivity implements View.OnClickListener, Callback<Usuario> {

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
    private Response mHTTPResponse;

    private Usuario mUsuario;

    private FragmentManager mFragmentManager;
    private ConectandoDialog mConectandoDialogInstance;

    private static final String LOGIN_PREFERENCES_KEY = "Login_preferences";
    private static final String EMAIL_KEY = "Email";
    private static final String PASSWORD_KEY = "Password";
    private static final String SESSION_KEY = "Session";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = getApplicationContext();
        mPreferences = mContext.getSharedPreferences(LOGIN_PREFERENCES_KEY, Context.MODE_PRIVATE);

        mFragmentManager = getSupportFragmentManager();
        mConectandoDialogInstance= new ConectandoDialog();

        mMail = (EditText) findViewById(R.id.et_email);
        mPassword = (EditText) findViewById(R.id.et_password);
        mLogIn = (Button) findViewById(R.id.btn_login);
        mSignUp = (Button) findViewById(R.id.btn_signup);
        mToS = (TextView) findViewById(R.id.tv_tos);

        mLogIn.setOnClickListener(this);
        mSignUp.setOnClickListener(this);
        mToS.setOnClickListener(this);

        //Si existen, se traen los valores guardados previamente para la dirección de email y el password
        String prefEmail = mPreferences.getString(EMAIL_KEY, null);
        String prefPassword = mPreferences.getString(PASSWORD_KEY, null);
        mPreferencesEditor = mPreferences.edit(); //Tremos un editor para las preferences

        if (prefEmail != null) mMail.setText(prefEmail);
        if (prefPassword != null) mPassword.setText(prefPassword);

        //Preparamos una conexión a la API de Parse
        mAPIHelper = new ParseAPIHelper();
        mRestAdapter = mAPIHelper.getRestAdapter();
        mLogInService = mRestAdapter.create(LogInService.class);
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_login: // LogIn button

                    //Regla: Todos los campos son requeridos
                    if (mMail.getText().toString().equals("") || mPassword.getText().toString().equals("")) {
                        muestraToast(getString(R.string.login_require_all));
                        return;
                    }
                    //Regla: La dirección de email debe ser válida
                    if (!validaEmail(mMail.getText().toString())) {
                        mMail.setError(getString(R.string.login_not_valid_email));
                        return;
                    }

                    mPreferencesEditor.putString(EMAIL_KEY, mMail.getText().toString());
                    mPreferencesEditor.putString(PASSWORD_KEY, mPassword.getText().toString());
                    mPreferencesEditor.apply(); //Nota: se usa apply() en lugar de commit() porque apply() trabaja en el background

                    doLogIn(mMail.getText().toString(), mPassword.getText().toString());

                break;

            case R.id.btn_signup: // SignUp button

                break;

            case R.id.tv_tos: // Terms of Service textView
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Config.ToS_URL))); //La URL de los ToS esta guardada en la clase Config
                break;
        }
    }

    private void muestraToast (String mensaje) {
        Toast.makeText(mContext, mensaje, Toast.LENGTH_SHORT).show();
    }

    private static boolean validaEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void doLogIn(String email, String password) {
        mLogInService.logIn(email, password, this);
        Log.d(Config.LOG_DEBUG, "(Retrofit) Log in request enviado");
        mConectandoDialogInstance.show(mFragmentManager, "Spinner_fragment_tag");
    }

    //RETROFIT CALLBACKS
    @Override
    public void success(Usuario usuario, Response response) {
        mConectandoDialogInstance.dismiss();
        if (response.getStatus() == 200) {
            this.mUsuario = usuario;
            mPreferencesEditor.putString(SESSION_KEY, this.mUsuario.sessionToken);
            muestraToast(getString(R.string.login_welcome));
        }
    }

    @Override
    public void failure(RetrofitError error) {
        Log.e(Config.LOG_ERROR, error.getMessage());
        mConectandoDialogInstance.dismiss();
        if (error.getMessage().contains("404")) muestraToast(getString(R.string.login_wrong_credentials)); //Error 404: Usuario y/o contraseña invalidos
        else muestraToast(getString(R.string.login_unable_to_connect));
    }

}
