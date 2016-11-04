package utn.com.ar.delivery_ui_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

    }


    public void selectDrink(View v) {
        Intent intent = new Intent(SelectionActivity.this, ListaProductoComida.class);
        startActivity(intent);
        finish();
    }

    public void selectFood(View v) {
        Intent intent = new Intent(SelectionActivity.this, ListaProductoComida.class);
        startActivity(intent);
        finish();
    }

    public void selectPostre(View v) {
        Intent intent = new Intent(SelectionActivity.this, ListaProductoComida.class);
        startActivity(intent);
        finish();
    }
}