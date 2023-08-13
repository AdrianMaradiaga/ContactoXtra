package hn.uth.contactoxtra.ui.crearContactos;

import android.Manifest;
import android.app.DatePickerDialog;
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
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
        calendar = Calendar.getInstance();

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
                    double latitudHogar = contactoExistente.getLatitudHogar();
                    double longitudHogar = contactoExistente.getLongitudHogar();
                    binding.tvLatitudHogar.setText(String.valueOf(latitudHogar));
                    binding.tvLongitudHogar.setText(String.valueOf(longitudHogar));

                    // Rellenar ubicación de trabajo
                    double latitudTrabajo = contactoExistente.getLatitudTrabajo();
                    double longitudTrabajo = contactoExistente.getLongitudTrabajo();
                    binding.tvLatitudTrabajo.setText(String.valueOf(latitudTrabajo));
                    binding.tvLongitudTrabajo.setText(String.valueOf(longitudTrabajo));

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

    private boolean hogarObtenido = false;
    private boolean trabajoObtenido = false;


    @Override
    public void onLocationChanged(@NonNull android.location.Location location) {
        double latitud = location.getLatitude();
        double longitud = location.getLongitude();
        Snackbar.make(binding.getRoot(), "Obteniendo ubicación...", Snackbar.LENGTH_SHORT).show();

        if ("Hogar".equals(tipoUbicacion) && !hogarObtenido) {
            latitudHogar = latitud;
            longitudHogar = longitud;
            binding.tvLatitudHogar.setText(String.valueOf(latitudHogar));
            binding.tvLongitudHogar.setText(String.valueOf(longitudHogar));
            hogarObtenido = true;
        } else if ("Trabajo".equals(tipoUbicacion) && !trabajoObtenido) {
            latitudTrabajo = latitud;
            longitudTrabajo = longitud;
            binding.tvLatitudTrabajo.setText(String.valueOf(latitudTrabajo));
            binding.tvLongitudTrabajo.setText(String.valueOf(longitudTrabajo));
            trabajoObtenido = true;
        }
        // Detener las actualizaciones de ubicación después de obtener los valores de ambas ubicaciones
        if (hogarObtenido && trabajoObtenido) {
            locationManager.removeUpdates(this);
        }
    }



    private void saveContact() {
        String nombre = binding.tilNombreContacto.getEditText().getText().toString();
        String apellido = binding.tilApellidoContacto.getEditText().getText().toString();
        String correo = binding.tilCorreoContacto.getEditText().getText().toString();
        String telefono = binding.tilTelefonoContacto.getEditText().getText().toString();
        String fechaCumple = binding.tvCumpleContacto.getText().toString();

        if (contactoExistente != null) {
            // Actualizar los datos del contacto existente con los valores ingresados
            contactoExistente.setNombre(nombre);
            contactoExistente.setApellido(apellido);
            contactoExistente.setCorreo(correo);
            contactoExistente.setTelefono(telefono);
            contactoExistente.setFechaCumple(fechaCumple);
            // Actualiza las ubicaciones solo si se han obtenido de la ubicación previamente
            if (latitudHogar != 0 || longitudHogar != 0) {
                contactoExistente.setLatitudHogar(latitudHogar);
                contactoExistente.setLongitudHogar(longitudHogar);
            }
            if (latitudTrabajo != 0 || longitudTrabajo != 0) {
                contactoExistente.setLatitudTrabajo(latitudTrabajo);
                contactoExistente.setLongitudTrabajo(longitudTrabajo);
            }

            // Llama al método de actualización en el ViewModel
            viewModel.update(contactoExistente);
        }

        Navigation.findNavController(binding.btnGuardarContacto).navigateUp();
        Toast.makeText(requireContext(), "Contacto actualizado correctamente", Toast.LENGTH_SHORT).show();

        finish();
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

    private void finish() {
        // Método personalizado utilizado para realizar alguna acción adicional al finalizar el fragmento
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
