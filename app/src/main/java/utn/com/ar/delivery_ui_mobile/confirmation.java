package utn.com.ar.delivery_ui_mobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;

public class confirmation extends AppCompatActivity {

    RadioButton rbEta;
    RadioButton rbPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rbEta = (RadioButton) this.findViewById(R.id.radio_ETA);
        rbPrecio = (RadioButton) this.findViewById(R.id.radio_precio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void enviarPedido(View v) {

    }
}
