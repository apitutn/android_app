package utn.com.ar.delivery_ui_mobile.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import utn.com.ar.delivery_ui_mobile.ProductoPostre;
import utn.com.ar.delivery_ui_mobile.R;


public class AdaptadorProductoPostre extends BaseAdapter {

	private final Activity actividad;
	private final ArrayList<ProductoPostre> listaProductoPostre;

	public AdaptadorProductoPostre(Activity actividad, ArrayList<ProductoPostre> productoPostres){
		
		this.actividad=actividad;
		this.listaProductoPostre = productoPostres;
	}

	@Override
	public int getCount() {
		
		return this.listaProductoPostre.size();
	}


	@Override
	public Object getItem(int position) {
		
		return listaProductoPostre.get(position);
	}

	@Override
	public long getItemId(int position) {

			return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflador = actividad.getLayoutInflater();
		View view = inflador.inflate(R.layout.item_lista_producto_postre,null, true);
		
		
		TextView textViewNombre = (TextView) view.findViewById(R.id.textViewName);
		TextView textViewID = (TextView) view.findViewById(R.id.textViewID);


		textViewNombre.setText(listaProductoPostre.get(position).getNombre());
		textViewID.setText("ID #"+ listaProductoPostre.get(position).getId().toString());

		if (listaProductoPostre.get(position).getImagen() != null)
			((ImageView) view.findViewById(R.id.imageViewProducto)).setImageBitmap(listaProductoPostre.get(position).getImagen());

		return view;
	}

	public boolean cargarProductoPostreEnLista(ProductoPostre productoPostre){
		 return this.listaProductoPostre.add(productoPostre);
	}
	
}
