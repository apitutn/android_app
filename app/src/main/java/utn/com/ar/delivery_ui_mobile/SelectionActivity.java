package utn.com.ar.delivery_ui_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class SelectionActivity extends AppCompatActivity {

    TextView etFood;
    TextView etBebida;
    TextView etDessert;
    EditText etDireccion;
    RadioButton rbEta;
    RadioButton rbPrecio;

    private String tokenRespuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        etFood = (TextView) this.findViewById(R.id.etFood);
        etBebida = (TextView) this.findViewById(R.id.etBebida);
        etDessert = (TextView) this.findViewById(R.id.etPostre);
        etDireccion = (EditText) this.findViewById(R.id.etDireccion);

        rbEta = (RadioButton) this.findViewById(R.id.radio_ETA);
        rbPrecio = (RadioButton) this.findViewById(R.id.radio_precio);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        Intent myIntent = getIntent();
        if (myIntent != null){
            if(myIntent.getStringExtra("comida") != null)
                editor.putString("comida", myIntent.getStringExtra("comida").toString());
            if(myIntent.getStringExtra("bebida") != null)
                editor.putString("bebida", myIntent.getStringExtra("bebida").toString());
            if(myIntent.getStringExtra("postre") != null)
                editor.putString("postre", myIntent.getStringExtra("postre").toString());
        }

        editor.commit();
        etFood.setText(pref.getString("comida", ""));
        etBebida.setText(pref.getString("bebida", ""));
        etDessert.setText(pref.getString("postre", ""));
    }


    public void selectDrink(View v) {
        Intent intent = new Intent(SelectionActivity.this, ListaProductoBebida.class);
        startActivity(intent);
        finish();
    }

    public void selectFood(View v) {
        Intent intent = new Intent(SelectionActivity.this, ListaProductoComida.class);
        startActivity(intent);
        finish();
    }

    public void selectDessert(View v) {
        Intent intent = new Intent(SelectionActivity.this, ListaProductoPostre.class);
        startActivity(intent);
        finish();
    }


    public void procesarPedido(View v) {
        final String comida = etFood.getText().toString();
        final String bebida = etBebida.getText().toString();
        final String postre = etDessert.getText().toString();
        final String direccion = etDireccion.getText().toString();
        String criteria = null;

        if (comida.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debe seleccionar la comida.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (bebida.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debe seleccionar la bebida.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (postre.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debe seleccionar el postre.", Toast.LENGTH_SHORT).show();
        }

        if (direccion.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debe ingresar una dirección", Toast.LENGTH_SHORT).show();
        }

        if (rbEta.isChecked()) {
            criteria = "lessETA";
        } else if (rbPrecio.isChecked()) {
            criteria = "lessPrice";
        } else {
            Toast.makeText(getApplicationContext(), "Debe seleccionar un criterio.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(SelectionActivity.this, confirmation.class);
        intent.putExtra("criteria", criteria);
        startActivity(intent);
        etFood.setText(null);
        etBebida.setText(null);
        etDessert.setText(null);
        finish();
    }
}
