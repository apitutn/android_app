package utn.com.ar.delivery_ui_mobile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import utn.com.ar.delivery_ui_mobile.util.Util;

public class ListaProductoComida extends Activity {

    private ProgressBar progressBar;

    private String token, user;

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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mAdapter = new AdaptadorProductoComida(ListaProductoComida, new ArrayList<ProductoComida>());
//        mRecyclerView.setAdapter(mAdapter);
//        GetComidas task = new GetComidas();
//        task.execute(Constants.URL + "productos/foods/");
//    }

    private class GetComidas extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... urls) {
            Log.d("11", urls[0]);
            InputStream is = null;

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);

                Log.d("EL HEADER AUTO", "Bearer " + token);
                conn.setRequestProperty("Authorization", "Bearer " + token);
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                Log.d("DE", "The response is: " + response);
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = Util.readIt(is);
                Log.d("respuesta", contentAsString);
                return contentAsString;
                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(final String success) {
        }

    }
}
