package ar.com.wolox.woloxtrainingmolina;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class loginActivity extends Activity implements View.OnClickListener{

    private EditText mMail;
    private EditText mPassword;
    private Button mLogIn;
    private Button mSignUp;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = getApplicationContext();

        mMail = (EditText) findViewById(R.id.et_email);
        mPassword = (EditText) findViewById(R.id.et_password);
        mLogIn = (Button) findViewById(R.id.btn_login);
        mSignUp = (Button) findViewById(R.id.btn_signup);

        mLogIn.setOnClickListener(this);
        mSignUp.setOnClickListener(this);

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

                break;

            case R.id.btn_signup: // SignUp button

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
