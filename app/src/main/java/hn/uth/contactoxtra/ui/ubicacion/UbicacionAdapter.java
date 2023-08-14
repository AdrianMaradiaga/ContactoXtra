package hn.uth.contactoxtra.ui.ubicacion;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hn.uth.contactoxtra.database.Ubicacion;
import hn.uth.contactoxtra.databinding.UbicacionItemBinding;
import hn.uth.contactoxtra.ui.OnItemClickListener;

public class UbicacionAdapter extends RecyclerView.Adapter<UbicacionAdapter.ViewHolder> {

    private List<Ubicacion> dataset;
    private OnItemClickListener<Ubicacion> manejadorEventoClick;
    private Context context;
    private UbicacionViewModel viewModel;
    private Ubicacion ubicacionSeleccionada;



    public UbicacionAdapter(Context context, List<Ubicacion> dataset, OnItemClickListener<Ubicacion> manejadorEventoClick, UbicacionViewModel viewModel){

        this.context = context;
        this.dataset = dataset;
        this.manejadorEventoClick = manejadorEventoClick;
        this.viewModel = viewModel;



    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UbicacionItemBinding binding = UbicacionItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }


    @Override

    public void onBindViewHolder(@NonNull UbicacionAdapter.ViewHolder holder, int position) {
        Ubicacion ubicacionItem = dataset.get(position);

        holder.binding.tvCategoria.setText(ubicacionItem.getCategoria());
        holder.binding.tvUsuario.setText(ubicacionItem.getPersona());
        holder.binding.tvlatitud.setText(String.valueOf(ubicacionItem.getLatitud())); // Convertir a cadena de texto
        holder.binding.tvlongitud.setText(String.valueOf(ubicacionItem.getLongitud())); // Convertir a cadena de texto

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v){
                showDialog(ubicacionItem);
                return true;
            }
        });
    }

    private void showDialog(Ubicacion ubicacionItem) {
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public void setItems(List<Ubicacion> ubicacion){
        this.dataset = ubicacion;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        UbicacionItemBinding binding;

        public ViewHolder(@NonNull UbicacionItemBinding itemView){
            super(itemView.getRoot());
            binding = itemView;
        }


    }
}
