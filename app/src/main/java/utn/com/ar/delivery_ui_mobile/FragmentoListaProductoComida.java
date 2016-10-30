package utn.com.ar.delivery_ui_mobile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import utn.com.ar.delivery_ui_mobile.util.Util;


/**
 * Created by Juanca on 18/9/16.
 */
public class FragmentoListaProductoComida extends Fragment {

    private View contexto;
    private AdaptadorProductoComida adaptador;
    private ArrayList<ProductoComida> vectorProductoComida;
    private ListView listViewProductoComida;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Bundle args = getArguments();

        FragmentoPiezaAsincronico tareaAsync = new FragmentoPiezaAsincronico(this.getActivity());
        tareaAsync.execute();

        contexto = inflater.inflate(R.layout.lista_productos_comida, container, false);
        return contexto;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public class FragmentoPiezaAsincronico extends AsyncTask<String , Intent, Boolean> {

        Activity asyncActividad;
        ProgressDialog dialogo;

        public FragmentoPiezaAsincronico(Activity contexto) {

            asyncActividad = contexto;
            dialogo = new ProgressDialog(contexto);
            dialogo.setMessage("Obteniendo Información");
        }

        @Override
        protected Boolean doInBackground(String... strings) {

            try {

                HttpURLConnection httpCon = Util.crearHttpPost("products", "GET", "application/x-www-form-urlencoded; charset=UTF-8");
                OutputStreamWriter wr = new OutputStreamWriter(httpCon.getOutputStream());

                wr.flush();
                wr.close();

                if (httpCon.getResponseCode() == HttpURLConnection.HTTP_OK) {


                    String respStr = httpCon.getContentEncoding();
                    JSONArray respJSON = new JSONArray(respStr);

                    for (int i = 0; i < respJSON.length(); i++) {

                        JSONObject obj = respJSON.getJSONObject(i);

                        ProductoComida pc = new ProductoComida(
                                obj.getInt("id"),
                                obj.getString("name"),
                                obj.getString("descripcion"),
                                obj.getString("image"));
                        vectorProductoComida.add(i, pc);
                    }

                }

            } catch (JSONException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return true;
        }

            @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogo.show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            adaptador = new AdaptadorProductoComida(asyncActividad, vectorProductoComida);
            listViewProductoComida = (ListView) getView().findViewById(android.R.id.list);
            listViewProductoComida.setAdapter(adaptador);

            if ( vectorProductoComida.size() == 0)
            {
                ((TextView)getView().findViewById(android.R.id.empty)).setVisibility(View.VISIBLE);
            }
            else
            {
                ((TextView)getView().findViewById(android.R.id.empty)).setVisibility(View.GONE);
            }

            dialogo.dismiss();

            listViewProductoComida.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                }
            });

        }
    }
}