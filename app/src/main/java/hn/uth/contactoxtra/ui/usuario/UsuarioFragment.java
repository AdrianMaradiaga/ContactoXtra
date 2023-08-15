package hn.uth.contactoxtra.ui.usuario;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import hn.uth.contactoxtra.R;
import hn.uth.contactoxtra.database.Usuario;
import hn.uth.contactoxtra.databinding.FragmentUsuarioBinding;

public class UsuarioFragment extends Fragment {
    private UsuarioViewModel usuarioViewModel;
    private FragmentUsuarioBinding binding;
    private Usuario usuario;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUsuarioBinding.inflate(inflater, container, false);

        Button btnCompartirUsuario = binding.btnCompartirUsuario;
        Button btnHogarUsuario = binding.btnHogarUsuario;
        Button btnTrabajoUsuario = binding.btnTrabajoUsuario;
        Button btnEliminarUsuario = binding.btnEliminarUsuario;

        btnCompartirUsuario.setOnClickListener(v -> compartirUsuario(usuario));
        btnHogarUsuario.setOnClickListener(v -> abrirUbicacionHogar());
        btnTrabajoUsuario.setOnClickListener(v -> abrirUbicacionTrabajo());
        btnEliminarUsuario.setOnClickListener(v -> eliminarUsuario());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        usuarioViewModel.getUsuarioLiveData().observe(getViewLifecycleOwner(), new Observer<Usuario>() {

            @Override
            public void onChanged(Usuario nuevoUsuario) {
                usuario = nuevoUsuario;
                // Mostrar u ocultar los botones según la existencia del usuario
                if (usuarioExists(usuario)) {
                    binding.btnAccionUsuario.setVisibility(View.GONE);
                    binding.btnEditarUsuario.setVisibility(View.VISIBLE);
                    binding.btnTrabajoUsuario.setVisibility(View.VISIBLE);
                    binding.btnCompartirUsuario.setVisibility(View.VISIBLE);
                    binding.btnHogarUsuario.setVisibility(View.VISIBLE);
                    binding.btnEliminarUsuario.setVisibility(View.VISIBLE);

                } else {
                    binding.btnAccionUsuario.setVisibility(View.VISIBLE);
                    binding.btnEditarUsuario.setVisibility(View.GONE);
                    binding.btnTrabajoUsuario.setVisibility(View.GONE);
                    binding.btnCompartirUsuario.setVisibility(View.GONE);
                    binding.btnHogarUsuario.setVisibility(View.GONE);
                    binding.btnEliminarUsuario.setVisibility(View.GONE);
                }

                if (usuario != null) {
                    binding.tvNombre.setText(usuario.getNombre());
                    binding.tvApellido.setText(usuario.getApellido());
                    binding.tvTelefono.setText(usuario.getTelefono());
                    binding.tvCumpleUsuario.setText(usuario.getFechaCumple());
                    binding.tvCorreoUsuario.setText(usuario.getCorreo());

                    String usuarioHogar = "Latitud: " + usuario.getLatitudusuario()
                            + ", Longitud: " + usuario.getLongitudusuario();

                    String ubicacionTrabajo = "Latitud: " + usuario.getLatitudTrabajo() +
                            ", Longitud: " + usuario.getLongitudTrabajo();
                    binding.tvTrabajoUsuario.setText(ubicacionTrabajo);
                    binding.tvUbicacionUsuario.setText(usuarioHogar);
                }
            }
        });

        binding.btnAccionUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_fragmentActual_to_crearUsuarioFragment);
            }
        });

        binding.btnEditarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                Bundle bundle = new Bundle();
                bundle.putParcelable("usuario", usuarioViewModel.getUsuarioLiveData().getValue());
                navController.navigate(R.id.action_fragmentActual_to_actualizarUsuarioFragment, bundle);
            }
        });
    }

    public void abrirUbicacionHogar() {
        if (usuario != null && usuario.getLatitudusuario() != 0 && usuario.getLongitudusuario() != 0) {
            double latitud = usuario.getLatitudusuario();
            double longitud = usuario.getLongitudusuario();

            // Crea un intent para abrir Google Maps con la ubicación
            Uri gmmIntentUri = Uri.parse("geo:" + latitud + "," + longitud + "?q=" + latitud + "," + longitud);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            startActivity(mapIntent);
        }else{
            Snackbar.make(requireView(), "No hay ubicaciones", Snackbar.LENGTH_LONG).show();
        }
    }

    public void abrirUbicacionTrabajo() {
        if (usuario != null && usuario.getLatitudTrabajo() != 0 && usuario.getLongitudTrabajo() != 0) {
            double latitud = usuario.getLatitudTrabajo();
            double longitud = usuario.getLongitudTrabajo();

            // Crea un intent para abrir Google Maps con la ubicación
            Uri gmmIntentUri = Uri.parse("geo:" + latitud + "," + longitud + "?q=" + latitud + "," + longitud);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            startActivity(mapIntent);
        }else{
            Snackbar.make(requireView(), "No hay ubicaciones", Snackbar.LENGTH_LONG).show();
        }
    }

    private void compartirUsuario(Usuario usuario) {
        if (usuario != null) {
            String textoCompartir = "Nombre: " + usuario.getNombre() +
                    "\nApellido: " + usuario.getApellido() +
                    "\nTeléfono: " + usuario.getTelefono() +
                    "\nCumpleaños: " + usuario.getFechaCumple() +
                    "\nCorreo: " + usuario.getCorreo();

            if (usuario.getLongitudusuario() != 0 && usuario.getLongitudusuario() != 0) {
                String ubicacionHogar = "Ubicación Hogar: " +
                        "\nLatitud: " + usuario.getLongitudusuario() +
                        "\nLongitud: " + usuario.getLongitudusuario();
                textoCompartir += "\n" + ubicacionHogar;
            }

            if (usuario.getLatitudTrabajo() != 0 && usuario.getLongitudTrabajo() != 0) {
                String ubicacionTrabajo = "Ubicación Trabajo: " +
                        "\nLatitud: " + usuario.getLatitudTrabajo() +
                        "\nLongitud: " + usuario.getLongitudTrabajo();
                textoCompartir += "\n" + ubicacionTrabajo;
            }

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Detalle del usuario");
            shareIntent.putExtra(Intent.EXTRA_TEXT, textoCompartir);

            startActivity(Intent.createChooser(shareIntent, "Compartir Detalle de Usuario"));
        }else{
            Snackbar.make(requireView(), "No hay valores que compartir", Snackbar.LENGTH_LONG).show();
        }
    }

    private void eliminarUsuario() {
        if (usuario != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Eliminar Usuario");
            builder.setMessage("¿Estás seguro de que deseas eliminar este usuario?");
            builder.setPositiveButton("Eliminar", (dialog, which) -> {
                usuarioViewModel.delete(usuario);
                limpiarTextViews();
                Snackbar.make(requireView(), "Usuario eliminado", Snackbar.LENGTH_LONG).show();
            });
            builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Snackbar.make(requireView(), "No hay usuario para eliminar", Snackbar.LENGTH_LONG).show();
        }
    }

    private void limpiarTextViews() {
        binding.tvNombre.setText("");
        binding.tvApellido.setText("");
        binding.tvTelefono.setText("");
        binding.tvCumpleUsuario.setText("");
        binding.tvCorreoUsuario.setText("");
        binding.tvTrabajoUsuario.setText("");
        binding.tvUbicacionUsuario.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean usuarioExists(Usuario usuario) {
        return usuario != null;
    }
}
