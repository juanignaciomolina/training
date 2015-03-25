package ar.com.wolox.woloxtrainingmolina.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ar.com.wolox.woloxtrainingmolina.Config;
import ar.com.wolox.woloxtrainingmolina.R;
import ar.com.wolox.woloxtrainingmolina.ui.ConnectingDialog;
import ar.com.wolox.woloxtrainingmolina.utils.InputCheckHelper;


public class SignUpActivity extends ActionBarActivity {

    private Context mContext;

    private EditText mMail;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mJoin;
    private TextView mToS;
    private Toolbar mToolbar;

    private FragmentManager mFragmentManager;
    private ConnectingDialog mConnectingDialogInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mContext = getApplicationContext();

        setUI();
        setToolbar();
        setListeners();
        initFragments();
    }

    private void setUI() {
        mMail = (EditText) findViewById(R.id.et_email);
        mPassword = (EditText) findViewById(R.id.et_password);
        mConfirmPassword = (EditText) findViewById(R.id.et_password_confirm);
        mJoin = (Button) findViewById(R.id.btn_join);
        mToS = (TextView) findViewById(R.id.tv_tos);
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

    private void setListeners() {
        mJoin.setOnClickListener(joinClickListener);
        mToS.setOnClickListener(tosClickListener);
    }

    private void initFragments() {
        mFragmentManager = getSupportFragmentManager();
        mConnectingDialogInstance = new ConnectingDialog();
    }

    private void blockUI() {
        mJoin.setEnabled(false);
        mJoin.setTextColor(getResources().getColor(R.color.gray));
        mMail.setEnabled(false);
        mPassword.setEnabled(false);
        mConfirmPassword.setEnabled(false);
        mConnectingDialogInstance.show(mFragmentManager, "Spinner_fragment_tag");
    }

    private void unlockUI() {
        mJoin.setEnabled(true);
        mJoin.setTextColor(getResources().getColor(R.color.white));
        mMail.setEnabled(true);
        mPassword.setEnabled(true);
        mConfirmPassword.setEnabled(true);
        mConnectingDialogInstance.dismiss();
    }

    View.OnClickListener joinClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Regla: Todos los campos son requeridos
            if (    mMail.getText().toString().equals("")
                    || mPassword.getText().toString().equals("")
                    || mConfirmPassword.getText().toString().equals("")) {
                showToast(getString(R.string.login_require_all));
                return;
            }

            //Regla: La dirección de email debe ser válida
            if (!InputCheckHelper.isValidEmail(mMail.getText().toString())) {
                mMail.setError(getString(R.string.login_not_valid_email));
                return;
            }

            //Regla: El campo "contraseña" y "confirmar contraseña" deben coincidir
            if (!mPassword.getText().toString().equals(mConfirmPassword.getText().toString())) {
                mConfirmPassword.setError(getString(R.string.signup_passwords_dont_match));
                return;
            }

            //TODO Logica para crear un usuario
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

}
