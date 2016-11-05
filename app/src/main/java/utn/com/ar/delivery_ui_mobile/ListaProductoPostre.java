package utn.com.ar.delivery_ui_mobile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import utn.com.ar.delivery_ui_mobile.adapters.AdaptadorProductoPostre;
import utn.com.ar.delivery_ui_mobile.util.Constants;

public class ListaProductoPostre extends AppCompatActivity {

    private TextView lblEtiqueta;
    private ListView lstOpciones;
    private ArrayList<ProductoPostre> vectorProductoPostre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_producto_postre);

        lblEtiqueta = (TextView)findViewById(R.id.LblEtiqueta);
        lstOpciones = (ListView)findViewById(R.id.LstOpciones);

        ListaProductoPostre.ListaPostresTask listaPostresTask = new ListaProductoPostre.ListaPostresTask(this);
        listaPostresTask.execute();

        lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                String opcionSeleccionada = ((ProductoPostre)a.getItemAtPosition(position)).getNombre();

                Intent intent = new Intent(ListaProductoPostre.this, SelectionActivity.class);
                intent.putExtra("postre",opcionSeleccionada);
                startActivity(intent);
                finish();
            }
        });
    }

    public class ListaPostresTask extends AsyncTask<Void, Void, Boolean> {

        private Activity activity;
        ProgressDialog dialogo;


        public ListaPostresTask(Activity activity) {
            this.activity = activity;

            dialogo = new ProgressDialog(activity);
            dialogo.setMessage("Obteniendo Información");
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialogo.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {

                vectorProductoPostre = new ArrayList<ProductoPostre>();

                URL url = new URL(Constants.URL + "products/desserts");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                JSONObject jsonObject = new JSONObject(buffer.toString());
                JSONArray respJSON = jsonObject.getJSONArray("data");

                for (int i = 0; i < respJSON.length(); i++) {

                    JSONObject obj = respJSON.getJSONObject(i);

                    URL urImg = new URL(obj.getString("image"));
                    Bitmap bmp = BitmapFactory.decodeStream(urImg.openConnection().getInputStream());

                    ProductoPostre productoPostre = new ProductoPostre(obj.getInt("id"),obj.getString("name"),obj.getString("description"),obj.getString("image"), bmp);
                    vectorProductoPostre.add(productoPostre);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            AdaptadorProductoPostre adaptador = new AdaptadorProductoPostre(activity, vectorProductoPostre);
            lstOpciones.setAdapter(adaptador);

            dialogo.dismiss();
        }
    }
}
