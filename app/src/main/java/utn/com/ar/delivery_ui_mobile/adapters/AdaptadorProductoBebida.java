package utn.com.ar.delivery_ui_mobile.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import utn.com.ar.delivery_ui_mobile.ProductoBebida;
import utn.com.ar.delivery_ui_mobile.R;


public class AdaptadorProductoBebida extends BaseAdapter {

	private final Activity actividad;
	private final ArrayList<ProductoBebida> listaProductoBebidas;

	public AdaptadorProductoBebida(Activity actividad, ArrayList<ProductoBebida> productoBebidas){
		this.actividad=actividad;
		this.listaProductoBebidas = productoBebidas;
	}

	@Override
	public int getCount() {
		return this.listaProductoBebidas.size();
	}


	@Override
	public Object getItem(int position) {
		return listaProductoBebidas.get(position);
	}

	@Override
	public long getItemId(int position) {
			return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflador = actividad.getLayoutInflater();
		View view = inflador.inflate(R.layout.item_lista_producto_bebida,null, true);
		
		
		TextView textViewNombre = (TextView) view.findViewById(R.id.textViewName);
		TextView textViewID = (TextView) view.findViewById(R.id.textViewID);


		textViewNombre.setText(listaProductoBebidas.get(position).getNombre());
		textViewID.setText("ID #"+ listaProductoBebidas.get(position).getId().toString());

		if (listaProductoBebidas.get(position).getImagen() != null)
			((ImageView) view.findViewById(R.id.imageViewProducto)).setImageBitmap(listaProductoBebidas.get(position).getImagen());

		return view;
	}

	public boolean cargarProductoBebidaEnLista(ProductoBebida productoBebida){
		
		 return this.listaProductoBebidas.add(productoBebida);
	}
	
}
