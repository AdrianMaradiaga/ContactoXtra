package hn.uth.contactoxtra.ui.usuario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import hn.uth.contactoxtra.R;
import hn.uth.contactoxtra.database.Contactos;
import hn.uth.contactoxtra.database.Usuario;
import hn.uth.contactoxtra.databinding.FragmentUsuarioBinding;
import hn.uth.contactoxtra.ui.contactos.ContactosAdapter;

public class UsuarioFragment extends Fragment {

    private FragmentUsuarioBinding binding;
    private TextView tvNombreUsuario;
    private TextView tvApellidoUsuario;
    private TextView tvTelefonoUsuario;
    private TextView tvCumplUsuario;
    private TextView tvUbicacionUsuario;
    private TextView tvemailUsuario;
    private UsuarioViewModel usuarioViewModel;

    private List<Usuario> dataset;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUsuarioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);

        tvNombreUsuario = root.findViewById(R.id.tvNombre);
        tvApellidoUsuario = root.findViewById(R.id.tvApellido);
        tvTelefonoUsuario = root.findViewById(R.id.tvTelefono);
        tvCumplUsuario = root.findViewById(R.id.tvFecha);
        tvemailUsuario = root.findViewById(R.id.tvemail);
        tvUbicacionUsuario = root.findViewById(R.id.tvUbicacion);

        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        usuarioViewModel.getUsuario().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                if (usuario != null) {
                    // Actualiza los elementos de la interfaz de usuario con los datos del usuario
                    tvNombreUsuario.setText(usuario.getNombre());
                    tvApellidoUsuario.setText(usuario.getApellido());
                    tvApellidoUsuario.setText(usuario.getApellido());
                    tvTelefonoUsuario.setText(usuario.getTelefono());
                    tvCumplUsuario.setText(usuario.getFechaCumple());
                    tvemailUsuario.setText(usuario.getCorreo());

                    // Concatenar latitud y longitud del hogar en el mismo TextView
                    String ubicacionHogar = "Latitud: " + usuario.getLatitudusuario() +
                            ", Longitud: " + usuario.getLongitudusuario();
                    tvUbicacionUsuario.setText(ubicacionHogar);

                    String ubicacionTrabajo = "Latitud: " + usuario.getLatitudTrabajo() +
                            ", Longitud: " + usuario.getLongitudTrabajo();
                    tvUbicacionUsuario.setText(ubicacionTrabajo);
                    // ...
                }
            }
        });


        NavController navController = NavHostFragment.findNavController(this);

        usuarioViewModel.tieneUsuario().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean tieneUsuario) {
                if (tieneUsuario) {
                    binding.btnActualizarUsuario.setText("Modificar Usuario");
                } else {
                    binding.btnActualizarUsuario.setText("Crear Usuario");
                }
            }
        });
        binding.btnActualizarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioViewModel.tieneUsuario().observe(getViewLifecycleOwner(), new Observer<Boolean>() {


                    public void onChanged(Boolean tieneUsuario) {
                    if (tieneUsuario) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("usuario" , bundle);

                        NavController navController = Navigation.findNavController(v);
                        navController.navigate(R.id.action_fragmentActual_to_crearContactoFragment, bundle);
                    } else {
                        navController.navigate(R.id.action_fragmentActual_to_crearusuarioFragment);
                    }
                }
            });

            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}