package utn.com.ar.delivery_ui_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import utn.com.ar.delivery_ui_mobile.registro.RegisterActivity;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {
    Button loguear;
    TextView registerLink;
    String TAG = "FirstActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        registerLink = (TextView) findViewById(R.id.btn_reg);
        loguear = (Button) findViewById(R.id.btn_ingresar);

        setStatusBarTranslucent(true);

        loguear.setOnClickListener(this);
        registerLink.setOnClickListener(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String value = preferences.getString("username", null);
        if (value == null) {
            Log.d(TAG, "USUARIO ES NULO");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ingresar:
                Intent intentLog = new Intent(FirstActivity.this, LoginActivity.class);
                startActivity(intentLog);
                finish();
                break;
            case R.id.btn_reg:
                Intent intentReg = new Intent(FirstActivity.this, RegisterActivity.class);
                startActivity(intentReg);
                break;
        }
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


}
