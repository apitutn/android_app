//package utn.com.ar.delivery_ui_mobile.adapters;
//
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//import utn.com.ar.delivery_ui_mobile.ProductoComida;
//import utn.com.ar.enviolibre.R;
//import utn.com.ar.enviolibre.domain.Publicacion;
//
//public class ComidasAdapter extends RecyclerView.Adapter<ComidasAdapter.ListadoHolder> {
//    private static String LOG_TAG = "ComidasAdapter";
//    private ArrayList<ProductoComida> mDataset;
//    private static MyClickListener myClickListener;
//    // Provide a reference to the views for each data item
//    // Complex data items may need more than one view per item, and
//    // you provide access to all the views for a data item in a view holder
//    public static class ListadoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        // each data item is just a string in this case
//        public TextView name;
//        public TextView id;
//        public TextView description;
//        public TextView destino;
//        public ListadoHolder(View v) {
//            super(v);
//            name = (TextView) itemView.findViewById(R.id.fecha);
//            id = (TextView) itemView.findViewById(R.id.id);
//            description = (TextView) itemView.findViewById(R.id.receptor);
//            destino = (TextView) itemView.findViewById(R.id.destino);
//            Log.i(LOG_TAG, "Adding Listener");
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            myClickListener.onItemClick(getPosition(), v);
//        }
//    }
//
//
//    public void setOnItemClickListener(MyClickListener myClickListener) {
//        this.myClickListener = myClickListener;
//    }
//
//    public ComidasAdapter(ArrayList<ProductoComida> myDataset) {
//        mDataset = myDataset;
//        Log.d(LOG_TAG,mDataset.toString());
//    }
//
//    // Create new views (invoked by the layout manager)
//    @Override
//    public ComidasAdapter.ListadoHolder onCreateViewHolder(ViewGroup parent,
//                                                                      int viewType) {
//        // create a new view
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.fra parent, false);
//        // set the view's size, margins, paddings and layout parameters
////            ...
//            ListadoHolder vh = new ListadoHolder(v);
//            return vh;
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(ListadoHolder holder, int position) {
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        holder.fecha.setText(mDataset.get(position).getFecha());
//        holder.id.setText(String.valueOf(mDataset.get(position).getId()));
//        holder.description.setText(mDataset.get(position).getReceptor());
//        holder.destino.setText(mDataset.get(position).getDireccion_destino().getDireccion());
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//        return mDataset.size();
//    }
//
//    public void addItem(Publicacion dataObj, int index) {
//        mDataset.add(dataObj);
//        notifyItemInserted(index);
//    }
//
//    public void deleteItem(int index) {
//        mDataset.remove(index);
//        notifyItemRemoved(index);
//    }
//
//    public Publicacion getItem(int index) {
//        Publicacion pub = mDataset.get(index);
//        return pub;
//    }
//
//    public interface MyClickListener {
//        public void onItemClick(int position, View v);
//    }
//}