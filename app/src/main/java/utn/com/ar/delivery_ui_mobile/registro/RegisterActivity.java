package utn.com.ar.delivery_ui_mobile.registro;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import utn.com.ar.delivery_ui_mobile.FirstActivity;
import utn.com.ar.delivery_ui_mobile.R;
import utn.com.ar.delivery_ui_mobile.deliveryApp;
import utn.com.ar.delivery_ui_mobile.domain.Usuario;
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

        botonRegistrarse = (Button) findViewById(R.id.btReg);

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
        final String email = etEmail.getText().toString();

        usuario = new Usuario(user, name, pasw, email);
        Log.d(TAG, "se inicio registrar");
        Log.d(TAG, usuario.getUser());
        Log.d(TAG, usuario.getClave());
        Log.d(TAG, usuario.getMail());
        Log.d(TAG, usuario.getNombre());

        if (!validarUsuario(usuario)) {
            focusView.requestFocus();
            return;
        } else {
            // set
            try {
                ((deliveryApp) this.getApplication()).addUsuario(usuario);
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
                Toast.makeText(getApplicationContext(), "El usuario ya existe.", Toast.LENGTH_LONG).show();
                return;
            }
            new AlertDialog.Builder(RegisterActivity.this)
                    .setTitle("Registro Exitoso")
                    .setMessage("Se ha registrado con exito el nuevo usuario")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(RegisterActivity.this, FirstActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        }
    }

    //     Process user's choice
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public boolean validarUsuario(Usuario user) {
        boolean validado = true;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(user.getClave()) && !Util.isPasswordValid(user.getClave())) {
            etPasw.setError(getString(R.string.error_invalid_password));
            focusView = etPasw;
            validado = false;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(user.getMail())) {
            etEmail.setError(getString(R.string.error_field_required));
            focusView = etEmail;
            validado = false;
        } else if (!Util.isEmailValid(user.getMail())) {
            etEmail.setError(getString(R.string.error_invalid_email));
            focusView = etEmail;
            validado = false;
        }

        // Check for a valid user name
        if (TextUtils.isEmpty(user.getUser())) {
            etUser.setError(getString(R.string.error_field_required));
            focusView = etUser;
            validado = false;
        } else if (user.getUser().length() > 10) {
            etUser.setError(getString(R.string.error_invalid_user));
            focusView = etUser;
            validado = false;
        }

        // Check for a valid name.
        if (TextUtils.isEmpty(user.getNombre())) {
            etName.setError(getString(R.string.error_field_required));
            focusView = etName;
            validado = false;
        } else if (user.getNombre().length() < 3) {
            etName.setError(getString(R.string.error_invalid_name));
            focusView = etName;
            validado = false;
        }

        return validado;
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            Log.d("34", urls[0]);
            try {
                HttpURLConnection httpCon = Util.crearHttpPost("signup", "POST", "application/json");
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
            if (response != null) {
                new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Registro Exitoso")
                        .setMessage("Se ha registrado con exito el nuevo usuario")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            } else {
                Toast.makeText(RegisterActivity.this, "No se puede registrar el usuario en este momento", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}