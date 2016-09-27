package utn.com.ar.delivery_ui_mobile.registro;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Calendar;
import java.util.Date;

import utn.com.ar.delivery_ui_mobile.R;
import utn.com.ar.delivery_ui_mobile.domain.Usuario;
import utn.com.ar.delivery_ui_mobile.util.Constants;
import utn.com.ar.delivery_ui_mobile.util.Util;

public class RegisterActivity extends AppCompatActivity {
    public static final int CARD_REQUEST_CODE = 13;
    private static TextView mDateDisplay;
    private static int mYear, mMonth, mDay;
    EditText etUser, etPasw, etName, etApe, etEmail, etCuil, etPhone;
    Button botonRegistrarse;
    View focusView = null;
    Usuario usuario;
    Toolbar toolbar;
    private String mPhoneNumber;
    String TAG = "RegistroActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etUser = (EditText) findViewById(R.id.etUser);
        etPasw = (EditText) findViewById(R.id.etPasw);
        etName = (EditText) findViewById(R.id.etName);
        etApe = (EditText) findViewById(R.id.etApe);
        etEmail = (EditText) findViewById(R.id.etMail);
        etPhone = (EditText) findViewById(R.id.phoneNumber);

        botonRegistrarse = (Button) findViewById(R.id.btReg);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_SMS},
                    Constants.MY_PERMISSIONS_READ_SMS);
        } else {
            TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            mPhoneNumber = tMgr.getLine1Number();
            if (mPhoneNumber != null) {
                etPhone.setText(mPhoneNumber);
            }
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        RegisterActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }

    }

    public void registrar(View v) {
        final String user = etUser.getText().toString();
        final String pasw = etPasw.getText().toString();
        final String name = etName.getText().toString();
        final String lastName = etApe.getText().toString();
        final String email = etEmail.getText().toString();
        final String cuil = etCuil.getText().toString();
        final String phone = etPhone.getText().toString();

        Calendar cal = Calendar.getInstance();
        cal.set(mYear, mMonth, mDay);
        Date fechaNac = cal.getTime();

        usuario = new Usuario(user, name, lastName, pasw, cuil, email, fechaNac, phone);
        Log.d(TAG, "se inicio registrar");
        Log.d(TAG, usuario.getNombreUsuario());
        Log.d(TAG, usuario.getClave());
        Log.d(TAG, usuario.getEmail());
        Log.d(TAG, usuario.getNombre());
        Log.d(TAG, String.valueOf(usuario.getId()));

        if (validarUsuario(usuario)) {
            focusView.requestFocus();
            return;
        }
    }

    //     Process user's choice
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public boolean validarUsuario(Usuario user) {
        boolean cancel = false;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(user.getClave()) && !Util.isPasswordValid(user.getClave())) {
            etPasw.setError(getString(R.string.error_invalid_password));
            focusView = etPasw;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(user.getEmail())) {
            etEmail.setError(getString(R.string.error_field_required));
            focusView = etEmail;
            cancel = true;
        } else if (!Util.isEmailValid(user.getEmail())) {
            etEmail.setError(getString(R.string.error_invalid_email));
            focusView = etEmail;
            cancel = true;
        }

        // Check for a valid user name
        if (TextUtils.isEmpty(user.getNombreUsuario())) {
            etUser.setError(getString(R.string.error_field_required));
            focusView = etUser;
            cancel = true;
        } else if (user.getNombreUsuario().length() > 10) {
            etUser.setError(getString(R.string.error_invalid_user));
            focusView = etUser;
            cancel = true;
        }

        // Check for a valid name.
        if (TextUtils.isEmpty(user.getNombre())) {
            etName.setError(getString(R.string.error_field_required));
            focusView = etName;
            cancel = true;
        } else if (user.getNombre().length() < 3) {
            etName.setError(getString(R.string.error_invalid_name));
            focusView = etName;
            cancel = true;
        }
        // Check for a valid name.
        if (TextUtils.isEmpty(user.getApellido())) {
            etApe.setError(getString(R.string.error_field_required));
            focusView = etApe;
            cancel = true;

        } else if (user.getApellido().length() < 3) {
            etApe.setError(getString(R.string.error_invalid_lastname));
            focusView = etApe;
            cancel = true;

        }

        // Check for a valid user name
//        if (TextUtils.isEmpty(user.getPhoneNumber())) {
//            etUser.setError(getString(R.string.error_field_required));
//            focusView = etUser;
//            cancel = true;
//        }
        // Check for a valid phone number.
        if (TextUtils.isEmpty(user.getNroTelefono())) {
            etPhone.setError(getString(R.string.error_field_required));
            focusView = etPhone;
            cancel = true;
        }

        return cancel;
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            Log.d("34", urls[0]);
//            TODO aca estaria el JSON, hay que descomponerlo o probar mandarlo asi.
            try {
                HttpURLConnection httpCon = Util.crearHttpPost("usuarios", "POST", "application/json");
                OutputStreamWriter wr = new OutputStreamWriter(httpCon.getOutputStream());
                wr.write(urls[0]);
                wr.flush();
                wr.close();

                //display what returns the POST request
                StringBuilder sb = new StringBuilder();
                int HttpResult = httpCon.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_CREATED) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(httpCon.getInputStream(), "utf-8"));
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    Log.d("3", "***********" + sb.toString());
                } else {
                    Log.d("4", "ERROR al enviar registro de usuario" + httpCon.getResponseMessage());
                    return null;
                }
                return "";
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("34", "1");
            } catch (UnsupportedEncodingException e) {
                Log.d("34", "2");
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.d("34", "3");
                e.printStackTrace();
            } catch (IOException e) {
                Log.d("34", "4");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onPostExecute(String response) {
        }

    }


}