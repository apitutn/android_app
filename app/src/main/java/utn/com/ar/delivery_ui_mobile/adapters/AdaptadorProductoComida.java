package utn.com.ar.delivery_ui_mobile.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import utn.com.ar.delivery_ui_mobile.ProductoComida;
import utn.com.ar.delivery_ui_mobile.R;


public class AdaptadorProductoComida extends BaseAdapter {

	private final Activity actividad;
	private final ArrayList<ProductoComida> listaProductoComida;

	public AdaptadorProductoComida(Activity actividad, ArrayList<ProductoComida> productoComidas){
		
		this.actividad=actividad;
		this.listaProductoComida = productoComidas;
	}

	@Override
	public int getCount() {
		
		return this.listaProductoComida.size();
	}


	@Override
	public Object getItem(int position) {
		
		return listaProductoComida.get(position);
	}

	@Override
	public long getItemId(int position) {

			return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflador = actividad.getLayoutInflater();
		View view = inflador.inflate(R.layout.item_lista_producto_comida,null, true);
		
		
		TextView textViewNombre = (TextView) view.findViewById(R.id.textViewName);
		TextView textViewID = (TextView) view.findViewById(R.id.textViewID);


		textViewNombre.setText(listaProductoComida.get(position).getNombre());
		textViewID.setText("ID #"+ listaProductoComida.get(position).getId().toString());

		if (listaProductoComida.get(position).getImagen() != null)
			((ImageView) view.findViewById(R.id.imageViewProducto)).setImageBitmap(listaProductoComida.get(position).getImagen());

		return view;
	}

	public boolean cargarProductoComidaEnLista(ProductoComida productoComida){
		
		 return this.listaProductoComida.add(productoComida);
	}
	
}
