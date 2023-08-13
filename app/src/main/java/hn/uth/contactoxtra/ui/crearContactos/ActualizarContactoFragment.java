package hn.uth.contactoxtra.ui.crearContactos;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

import hn.uth.contactoxtra.R;
import hn.uth.contactoxtra.database.Contactos;
import hn.uth.contactoxtra.databinding.FragmentCrearContactoBinding;

public class ActualizarContactoFragment extends Fragment implements LocationListener {
    private static final int REQUEST_CODE_GPS = 102;
    private FragmentCrearContactoBinding binding;
    private CrearContactoViewModel viewModel;
    private Calendar calendar;
    private LocationManager locationManager;
    private double latitudHogar, longitudHogar, latitudTrabajo, longitudTrabajo;
    private String tipoUbicacion;
    private Contactos contactoExistente;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCrearContactoBinding.inflate(inflater, container, false);
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);

        binding.btnUbicacionHogar.setOnClickListener(v -> obtenerUbicacion("Hogar"));
        binding.btnUbicacionTrabajo.setOnClickListener(v -> obtenerUbicacion("Trabajo"));

        // Ocultar el Bottom Navigation
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);

        // Verificar si se pasó un contacto existente como argumento
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            if (bundle != null) {
                contactoExistente = bundle.getParcelable("contacto");
                if (contactoExistente != null) {
                    // Rellenar los campos con los datos del contacto existente
                    binding.tilNombreContacto.getEditText().setText(contactoExistente.getNombre());
                    binding.tilApellidoContacto.getEditText().setText(contactoExistente.getApellido());
                    binding.tilCorreoContacto.getEditText().setText(contactoExistente.getCorreo());
                    binding.tilTelefonoContacto.getEditText().setText(contactoExistente.getTelefono());
                    binding.tvCumpleContacto.setText(contactoExistente.getFechaCumple());

                    // Rellenar ubicación de hogar
                    binding.tvLatitudHogar.setText("Latitud: " + contactoExistente.getLatitudHogar());
                    binding.tvLongitudHogar.setText("Longitud: " + contactoExistente.getLongitudHogar());

                    // Rellenar ubicación de trabajo
                    binding.tvLatitudTrabajo.setText("Latitud: " + contactoExistente.getLatitudTrabajo());
                    binding.tvLongitudTrabajo.setText("Longitud: " + contactoExistente.getLongitudTrabajo());
                }
            }
        }


        // Inicializar el ViewModel asociado al fragmento
        viewModel = new ViewModelProvider(requireActivity()).get(CrearContactoViewModel.class);

        // Configurar el botón de guardar
        binding.btnGuardarContacto.setOnClickListener(v -> saveContact());

        // Configurar el botón de selección de fecha
        binding.btnCumpleContacto.setOnClickListener(v -> showDatePickerDialog());

        return binding.getRoot();
    }

    private void obtenerUbicacion(String tipoUbicacion) {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Obtén la instancia del LocationManager y luego llama a requestSingleUpdate
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
            this.tipoUbicacion = tipoUbicacion;
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_GPS);
        }
    }

    @Override
    public void onLocationChanged(@NonNull android.location.Location location) {
        double latitud = location.getLatitude();
        double longitud = location.getLongitude();

        if ("Hogar".equals(tipoUbicacion)) {
            latitudHogar = latitud;
            longitudHogar = longitud;
            binding.tvLatitudHogar.setText(String.valueOf(latitudHogar));
            binding.tvLongitudHogar.setText(String.valueOf(longitudHogar));
        } else if ("Trabajo".equals(tipoUbicacion)) {
            latitudTrabajo = latitud;
            longitudTrabajo = longitud;
            binding.tvLatitudTrabajo.setText(String.valueOf(latitudTrabajo));
            binding.tvLongitudTrabajo.setText(String.valueOf(longitudTrabajo));
        }
        // Detener las actualizaciones de ubicación después de obtener los valores
        locationManager.removeUpdates(this);
    }

    // Resto del código del onStatusChanged, onProviderEnabled, onProviderDisabled, showDatePickerDialog

    private void saveContact() {
        String nombre = binding.tilNombreContacto.getEditText().getText().toString();
        String apellido = binding.tilApellidoContacto.getEditText().getText().toString();
        // Obtén los valores de los demás campos de acuerdo a tu Contacto

        if (contactoExistente != null) {
            // Actualizar los datos del contacto existente con los valores ingresados
            contactoExistente.setNombre(nombre);
            contactoExistente.setApellido(apellido);
            // Actualiza los demás campos de acuerdo a la estructura de tu Contacto
            contactoExistente.setLatitudHogar(latitudHogar);
            contactoExistente.setLongitudHogar(longitudHogar);
            contactoExistente.setLatitudTrabajo(latitudTrabajo);
            contactoExistente.setLongitudTrabajo(longitudTrabajo);

            // Llama al método de actualización en el ViewModel
            viewModel.update(contactoExistente);
        }

        Navigation.findNavController(binding.btnGuardarContacto).navigateUp();
        Toast.makeText(requireContext(), "Contacto actualizado correctamente", Toast.LENGTH_SHORT).show();

        finish();
    }

    private void showDatePickerDialog() {
        // Implementa el código para mostrar el diálogo de selección de fecha
    }

    private void finish() {
        // Método personalizado utilizado para realizar alguna acción adicional al finalizar el fragmento
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
