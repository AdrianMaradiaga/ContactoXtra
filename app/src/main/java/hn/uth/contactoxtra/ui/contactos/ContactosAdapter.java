package hn.uth.contactoxtra.ui.contactos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import hn.uth.contactoxtra.database.Contactos;
import hn.uth.contactoxtra.databinding.ContactoItemBinding;
import hn.uth.contactoxtra.ui.OnItemClickListener;

public class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ViewHolder>{
    private List<Contactos> dataset;
    private OnItemClickListener<Contactos> manejadorEventoClick;
    private Context context;
    private ContactosViewModel viewModel;
    private Contactos contactoSeleccioando;

    public ContactosAdapter(Context context, List<Contactos> dataset, OnItemClickListener<Contactos> manejadorEventoClick, ContactosViewModel viewModel) {
        this.context = context;
        this.dataset = dataset;
        this.manejadorEventoClick = manejadorEventoClick;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ContactosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContactoItemBinding binding = ContactoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactosAdapter.ViewHolder holder, int position) {
        Contactos contactoItem = dataset.get(position);

        holder.binding.tvContacto.setText(contactoItem.getNombre() + " " + contactoItem.getApellido());
        holder.binding.tvTelefono.setText(contactoItem.getTelefono());
        holder.binding.tvCorreo.setText(contactoItem.getCorreo());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v){
                showDialog(contactoItem);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setItems(List<Contactos> contactos){
        this.dataset = contactos;
        notifyDataSetChanged();
    }

    private void showDialog(Contactos contacto){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Eliminar contacto");
        builder.setMessage("¿Estás seguro de que deseas eliminar este contacto?");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteContacto(contacto);
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    /**
     * Elimina un evento utilizando el ViewModel.
     * @param contacto El evento a eliminar.
     */
    private void deleteContacto(Contactos contacto){
        viewModel.delete(contacto);
    }

    /**
     * Clase ViewHolder que contiene los componentes de vista del elemento de lista.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ContactoItemBinding binding;

        public ViewHolder(@NonNull ContactoItemBinding itemView){
            super(itemView.getRoot());
            binding = itemView;
        }

        public void setOnClickListener(Contactos contactoMostrar, OnItemClickListener<Contactos> listener){
            //
        }
    }
}
