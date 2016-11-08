package utn.com.ar.delivery_ui_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import utn.com.ar.delivery_ui_mobile.util.Util;

public class confirmation extends AppCompatActivity {

    TextView eta;
    TextView price;
    String tokenRespuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        eta = (TextView) this.findViewById(R.id.tvEta);
        price = (TextView) this.findViewById(R.id.tvPrecio);
        Intent myIntent = getIntent();
        String criteria = null;
        if (myIntent != null) {
            if (myIntent.getStringExtra("criteria") != null)
                criteria = myIntent.getStringExtra("criteria").toString();
            final ProcesarPedidoTask procesarPedidoTask = new ProcesarPedidoTask(criteria);
            procesarPedidoTask.execute();
        }
    }

    public void enviarPedido(View v) {
        //Log.d("login", tokenRespuesta);
        Toast.makeText(getApplicationContext(), "El pedido se ha realizado con exito.", Toast.LENGTH_SHORT).show();
        Intent intentMenu = new Intent(confirmation.this, SelectionActivity.class);
        startActivity(intentMenu);
        finish();
    }

    public class ProcesarPedidoTask extends AsyncTask<Void, Void, Boolean> {

        private String respuesta;
        private String criterio;

        public ProcesarPedidoTask(String criterio) {
            this.criterio = criterio;
        }


        protected Boolean callApi() {
            HttpURLConnection httpCon = Util.crearHttpPost("etaprice", "POST", "application/json; charset=UTF-8");
            try {
                OutputStreamWriter wr = new OutputStreamWriter(httpCon.getOutputStream());
                //Create JSONObject here
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("criterio", this.criterio);
                Log.d("Response message login", "***********" + jsonParam.toString());
                wr.write(jsonParam.toString());
                wr.flush();
                wr.close();

                if (httpCon.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
                    Log.d("Response getcriterios", "*****" + httpCon.getResponseMessage());
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(httpCon.getInputStream(), "utf-8"));
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    JSONObject jsonObject = new JSONObject(line.toString());
                    JSONArray respJSON = jsonObject.getJSONArray("data");

                    for (int i = 0; i < respJSON.length(); i++) {

                        JSONObject obj = respJSON.getJSONObject(i);

                        eta.setText(obj.getString("eta"));
                        price.setText(obj.getString("price"));
                    }
                    return true;
                } else {
                    respuesta = Util.printearRespuesta(httpCon);
                    return false;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("criterios", "1");
            } catch (UnsupportedEncodingException e) {
                Log.d("criterios", "2");
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.d("criterios", "3");
                e.printStackTrace();
            } catch (IOException e) {
                Log.d("criterios", "4");
                e.printStackTrace();
            } catch (JSONException e) {
                Log.d("criterios", "5");
                e.printStackTrace();
            } finally {
                httpCon.disconnect();
            }
            return false;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            return callApi();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (!success) {
                Toast.makeText(getApplicationContext(), "El pedido no se pudo procesar", Toast.LENGTH_SHORT).show();
            } else {

            }
        }

        @Override
        protected void onCancelled() {
        }
    }

    public void onBackPressed() {
        this.startActivity(new Intent(confirmation.this, SelectionActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
