package hn.uth.contactoxtra.ui.crearContactos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import hn.uth.contactoxtra.R;
import hn.uth.contactoxtra.databinding.FragmentCrearContactoBinding;
import hn.uth.contactoxtra.ui.contactos.ContactosViewModel;

public class CrearContactoFragment extends Fragment {
    private FragmentCrearContactoBinding binding;
    private ContactosViewModel contactosViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCrearContactoBinding.inflate(inflater, container, false);

        // Ocultar el Bottom Navigation en este fragmento
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);

        return binding.getRoot();
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contactosViewModel = new ViewModelProvider(requireActivity()).get(ContactosViewModel.class);

        // Aquí puedes configurar tus botones y vistas para la creación de contactos
        // y manejar las interacciones del usuario, como seleccionar la fecha de cumpleaños
        // y guardar el contacto en la base de datos usando el ViewModel.

        // Ejemplo: Configurar un clic en el botón de guardar
        binding.btnGuardarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes obtener los datos del formulario (nombre, apellido, correo, etc.)
                // y luego crear un objeto Contactos con esos datos y guardarlo usando el ViewModel
                // contactosViewModel.insert(contactoNuevo);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Mostrar el Bottom Navigation nuevamente al salir del fragmento
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.VISIBLE);

        binding = null;
    }
}
