package hn.uth.contactoxtra.ui.crearContactos;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import hn.uth.contactoxtra.R;
import hn.uth.contactoxtra.database.Contactos;
import hn.uth.contactoxtra.databinding.FragmentCrearContactoBinding;
import hn.uth.contactoxtra.ui.contactos.ContactosViewModel;

public class CrearContactoFragment extends Fragment {
    private FragmentCrearContactoBinding binding;
    private CrearContactoViewModel viewModel;
    private Calendar calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCrearContactoBinding.inflate(inflater, container, false);
        // Ocultar el Bottom Navigation
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(CrearContactoViewModel.class);
        calendar = Calendar.getInstance();

        binding.btnCumpleContacto.setOnClickListener(v -> showDatePickerDialog());

        binding.btnGuardarContacto.setOnClickListener(v -> saveContact());
    }

    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateTextView();
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void updateDateTextView() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(calendar.getTime());
        binding.tvCumpleContacto.setText(formattedDate);
    }

    private void saveContact() {
        String nombre = binding.tilNombreContacto.getEditText().getText().toString();
        String apellido = binding.tilApellidoContacto.getEditText().getText().toString();
        String correo = binding.tilCorreoContacto.getEditText().getText().toString();
        String telefono = binding.tilTelefonoContacto.getEditText().getText().toString();
        String fechaCumple = binding.tvCumpleContacto.getText().toString();

        // Validar que los campos no estén vacíos
        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || telefono.isEmpty() || fechaCumple.isEmpty()) {
            Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un objeto Contacto con los datos ingresados
        Contactos nuevoContacto = new Contactos(nombre, apellido, correo, telefono, fechaCumple, 0);

        // Guardar el contacto usando el ViewModel
        viewModel.insert(nuevoContacto);

        // Mostrar un mensaje de éxito
        Toast.makeText(requireContext(), "Contacto guardado correctamente", Toast.LENGTH_SHORT).show();

        // Limpiar los campos
        binding.tilNombreContacto.getEditText().setText("");
        binding.tilApellidoContacto.getEditText().setText("");
        binding.tilCorreoContacto.getEditText().setText("");
        binding.tilTelefonoContacto.getEditText().setText("");
        binding.tvCumpleContacto.setText("");

        // Navegar al fragmento de contactos
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_crearContactoFragment_to_contactosFragment);
    }


    private void finish() {
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

