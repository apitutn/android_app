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

import utn.com.ar.delivery_ui_mobile.adapters.AdaptadorProductoBebida;
import utn.com.ar.delivery_ui_mobile.util.Constants;

public class ListaProductoBebida extends Activity {

    private TextView lblEtiqueta;
    private ListView lstOpciones;
    private ArrayList<ProductoBebida> vectorProductoBebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_producto_bebida);

        lblEtiqueta = (TextView)findViewById(R.id.LblEtiqueta);
        lstOpciones = (ListView)findViewById(R.id.LstOpciones);

        ListaBebidasTask listaBebidasTask = new ListaBebidasTask(this);
        listaBebidasTask.execute();

        lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                String opcionSeleccionada = ((ProductoBebida)a.getItemAtPosition(position)).getNombre();

                Intent intent = new Intent(ListaProductoBebida.this, SelectionActivity.class);
                intent.putExtra("bebida",opcionSeleccionada);
                startActivity(intent);
                finish();
            }
        });
    }


    public class ListaBebidasTask extends AsyncTask<Void, Void, Boolean> {

        private Activity activity;
        ProgressDialog dialogo;


        public ListaBebidasTask(Activity activity) {
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

                vectorProductoBebida = new ArrayList<ProductoBebida>();

                URL url = new URL(Constants.URL + "products/drinks");

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

                    ProductoBebida productoBebida = new ProductoBebida(obj.getInt("id"),obj.getString("name"),obj.getString("description"),obj.getString("image"), bmp);
                    vectorProductoBebida.add(productoBebida);
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
            AdaptadorProductoBebida adaptador = new AdaptadorProductoBebida(activity, vectorProductoBebida);
            lstOpciones.setAdapter(adaptador);

            dialogo.dismiss();
        }
    }
}
