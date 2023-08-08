package hn.uth.contactoxtra.ui.contactos;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hn.uth.contactoxtra.R;
import hn.uth.contactoxtra.entity.Contacto;

public class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ContactoViewHolder>{
    private List<Contacto> listaContactos;

    public ContactosAdapter(List<Contacto> listaContactos) {
        this.listaContactos = listaContactos;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacto_item, parent, false);
        return new ContactoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        Contacto contacto = listaContactos.get(position);
        holder.txtNombreContacto.setText(contacto.getName());

        holder.itemView.setOnClickListener(view -> {
            String nombreContacto = contacto.getName();
            String telefonoContacto = contacto.getPhone();
            String correoContacto = contacto.getEmail();



        });
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    static class ContactoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreContacto;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreContacto = itemView.findViewById(R.id.tvContacto); // Cambio aquí
        }
    }
}
