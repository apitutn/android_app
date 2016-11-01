package utn.com.ar.delivery_ui_mobile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
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

import utn.com.ar.delivery_ui_mobile.domain.Usuario;
import utn.com.ar.delivery_ui_mobile.util.Constants;
import utn.com.ar.delivery_ui_mobile.util.Util;

public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    private UserLoginTask mAuthTask = null;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "LoginActivity";

    // UI references.
    private AutoCompleteTextView mUserNameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private String tokenRespuesta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUserNameView = (AutoCompleteTextView) findViewById(R.id.userName);
        setStatusBarTranslucent(true);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        assert mEmailSignInButton != null;
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUserNameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String userName = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !Util.isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(userName)) {
            mUserNameView.setError(getString(R.string.error_field_required));
            focusView = mUserNameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            Usuario usuario = ((deliveryApp) this.getApplication()).getUsuarioLogeado(userName);
            if (usuario == null) {
                Toast.makeText(getApplicationContext(), "El usuario no existe", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!usuario.getClave().equals(password)) {
                Toast.makeText(getApplicationContext(), "La contraseña es incorrecta.", Toast.LENGTH_SHORT).show();
                return;
            }
            ((deliveryApp) this.getApplication()).setLoggedUser(usuario);
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("username", userName).apply();

            Intent intentMenu = new Intent(LoginActivity.this, SelectionActivity.class);
            startActivity(intentMenu);
            finish();
//            showProgress(true);
//            InputMethodManager inputManager = (InputMethodManager)
//                    getSystemService(Context.INPUT_METHOD_SERVICE);
//
//            inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(),
//                    InputMethodManager.HIDE_NOT_ALWAYS);
//            mAuthTask = new UserLoginTask(userName, password);
//            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUserName;
        private final String mPassword;
        private String respuesta;

        UserLoginTask(String userName, String password) {
            mUserName = userName;
            mPassword = password;
        }

        protected Boolean callApi() {
            try {
                HttpURLConnection httpCon = Util.crearHttpPost("autenticacion", "POST", "application/x-www-form-urlencoded; charset=UTF-8");
                OutputStreamWriter wr = new OutputStreamWriter(httpCon.getOutputStream());
                String md5psw = Util.toMd5(mPassword);

                wr.write("username=" + mUserName + "&password=" + md5psw);
                wr.flush();
                wr.close();

                if (httpCon.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Log.d("Response message login", "***********" + httpCon.getResponseMessage());
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(httpCon.getInputStream(), "utf-8"));
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    tokenRespuesta = sb.toString();
                    return true;
                } else {
                    respuesta = Util.printearRespuesta(httpCon);
                    return false;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("login", "1");
            } catch (UnsupportedEncodingException e) {
                Log.d("login", "2");
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.d("login", "3");
                e.printStackTrace();
            } catch (IOException e) {
                Log.d("login", "4");
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            return callApi();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if (success) {
                String stUrl = Constants.URL + "usuarios/notificaciones";
            } else {
                Toast.makeText(getApplicationContext(), "El usuario o la contraseña son incorrectas. Intentelo nuevamente", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    public void onBackPressed() {
        this.startActivity(new Intent(LoginActivity.this, FirstActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}

