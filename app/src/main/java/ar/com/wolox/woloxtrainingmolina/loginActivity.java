package ar.com.wolox.woloxtrainingmolina;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class loginActivity extends Activity implements View.OnClickListener{

    private Context mContext;
    private SharedPreferences mPreferences;

    private EditText mMail;
    private EditText mPassword;
    private Button mLogIn;
    private Button mSignUp;
    private TextView mToS;

    private static final String LOGIN_PREFERENCES_KEY = "Login_preferences";
    private static final String EMAIL_KEY = "Email";
    private static final String PASSWORD_KEY = "Password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = getApplicationContext();
        mPreferences = mContext.getSharedPreferences(LOGIN_PREFERENCES_KEY, Context.MODE_PRIVATE);

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

        if (prefEmail != null) mMail.setText(prefEmail);
        if (prefPassword != null) mPassword.setText(prefPassword);

    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_login: // LogIn button

                    //Regla: Todos los campos son requeridos
                    if (mMail.getText().toString().equals("") && mPassword.getText().toString().equals("")) {
                        muestraToast(getString(R.string.login_require_all));
                        return;
                    }
                    //Regla: La dirección de email debe ser válida
                    if (!validaEmail(mMail.getText().toString())) {
                        mMail.setError(getString(R.string.login_not_valid_email));
                        return;
                    }

                    SharedPreferences.Editor editor = mPreferences.edit();
                    editor.putString(EMAIL_KEY, mMail.getText().toString());
                    editor.putString(PASSWORD_KEY, mPassword.getText().toString());
                    editor.apply(); //Nota: se usa apply() en lugar de commit() porque apply() trabaja en el background

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

}
