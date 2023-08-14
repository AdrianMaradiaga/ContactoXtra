package hn.uth.contactoxtra.ui.usuario;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hn.uth.contactoxtra.R;
import hn.uth.contactoxtra.database.Usuario;
import hn.uth.contactoxtra.databinding.FragmentUsuarioBinding;
import hn.uth.contactoxtra.ui.OnItemClickListener;


public class UsuarioAdapter {
    private List<Usuario> dataset;
    private OnItemClickListener<Usuario> manejadorUsuarioClick;
    private Context context;
    private UsuarioViewModel viewModel;

    public UsuarioAdapter(Context context, List<Usuario> dataset, OnItemClickListener<Usuario> manejadorContactoClick, UsuarioViewModel viewModel) {

        this.context = context;
        this.dataset = dataset;
        this.manejadorUsuarioClick = manejadorUsuarioClick;
        this.viewModel = viewModel;

    }

    @NonNull
    public UsuarioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentUsuarioBinding binding = FragmentUsuarioBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UsuarioAdapter.ViewHolder(binding);
    }

    public void onBindViewHolder(@NonNull UsuarioAdapter.ViewHolder holder, int position) {
        Usuario usuarioItem = dataset.get(position);

        String nombreCompleto = usuarioItem.getNombre().trim() + " " + usuarioItem.getApellido().trim();
        holder.binding.tvApellido.setText(nombreCompleto);
        holder.binding.textView5.setText(usuarioItem.getTelefono());
        holder.binding.tvemail.setText(usuarioItem.getCorreo());

    }


    public void setItems(List<Usuario> usuarios) {
        this.dataset = usuarios;
        notifyDataSetChanged();
    }

    private void notifyDataSetChanged() {
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        FragmentUsuarioBinding binding;

        public ViewHolder(@NonNull FragmentUsuarioBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }


    }
}
