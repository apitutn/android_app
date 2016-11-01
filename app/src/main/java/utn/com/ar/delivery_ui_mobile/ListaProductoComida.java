package utn.com.ar.delivery_ui_mobile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

public class ListaProductoComida extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lista_productos_comida);
    }

    public void cerrarSesion(View v) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("username").apply();
        ((deliveryApp) this.getApplication()).setLoggedUser(null);
        Intent intentFirst = new Intent(ListaProductoComida.this, FirstActivity.class);
        startActivity(intentFirst);
        finish();
    }
}
