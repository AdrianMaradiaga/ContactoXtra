package hn.uth.contactoxtra.ui.usuario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import hn.uth.contactoxtra.R;
import hn.uth.contactoxtra.database.Usuario;
import hn.uth.contactoxtra.databinding.FragmentUsuarioBinding;

public class UsuarioFragment extends Fragment {

    private UsuarioViewModel usuarioViewModel;
    private FragmentUsuarioBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUsuarioBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        usuarioViewModel.getUsuarioLiveData().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                if (usuario != null) {
                    binding.tvNombre.setText(usuario.getNombre());
                    binding.tvApellido.setText(usuario.getApellido());
                    binding.tvTelefono.setText(usuario.getTelefono());
                    binding.tvCumpleUsuario.setText(usuario.getFechaCumple());
                    binding.tvCorreoUsuario.setText(usuario.getCorreo());

                    String usuarioHogar = "Latitud: " + usuario.getLatitudusuario()
                            + ", Longitud: " + usuario.getLongitudusuario();

                    binding.tvUbicacionUsuario.setText(usuarioHogar);

                }
            }
        });


        // Configura acciones para botones u otros elementos de la interfaz
        binding.btnAccionUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén el NavController desde el View actual
                NavController navController = Navigation.findNavController(v);

                // Navegar a CrearUsuarioFragment
                navController.navigate(R.id.action_fragmentActual_to_crearUsuarioFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
