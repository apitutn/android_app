package utn.com.ar.delivery_ui_mobile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import utn.com.ar.delivery_ui_mobile.adapters.AdaptadorProductoComida;
import utn.com.ar.delivery_ui_mobile.util.Constants;


public class ListaProductoComida extends Activity {


    private TextView lblEtiqueta;
    private ListView lstOpciones;
    private ArrayList<ProductoComida> vectorProductoComida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_producto_comida);

        lblEtiqueta = (TextView)findViewById(R.id.LblEtiqueta);
        lstOpciones = (ListView)findViewById(R.id.LstOpciones);

        ListaComidasTask listaComidasTask = new ListaComidasTask(this);
        listaComidasTask.execute();

        lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                String opcionSeleccionada = ((ProductoComida)a.getItemAtPosition(position)).getNombre();

                Intent intent = new Intent(ListaProductoComida.this, SelectionActivity.class);
                intent.putExtra("comida",opcionSeleccionada);
                startActivity(intent);
                finish();
            }
        });
    }



    public class ListaComidasTask extends AsyncTask<Void, Void, Boolean> {

        private Activity activity;
        ProgressDialog dialogo;


        public ListaComidasTask(Activity activity) {
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

                vectorProductoComida = new ArrayList<ProductoComida>();

                URL url = new URL(Constants.URL + "products/foods");

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

                    ProductoComida productoComida = new ProductoComida(obj.getInt("id"),obj.getString("name"),obj.getString("description"),obj.getString("image"), bmp);
                    vectorProductoComida.add(productoComida);
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

            AdaptadorProductoComida adaptador = new AdaptadorProductoComida(activity, vectorProductoComida);
            lstOpciones.setAdapter(adaptador);


            dialogo.dismiss();
        }
    }
}