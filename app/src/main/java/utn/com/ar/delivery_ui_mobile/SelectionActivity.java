package utn.com.ar.delivery_ui_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class SelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        EditText etFood = (EditText) this.findViewById(R.id.etFood);
        EditText etBebida = (EditText) this.findViewById(R.id.etBebida);
        EditText etDessert = (EditText) this.findViewById(R.id.etPostre);

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
}