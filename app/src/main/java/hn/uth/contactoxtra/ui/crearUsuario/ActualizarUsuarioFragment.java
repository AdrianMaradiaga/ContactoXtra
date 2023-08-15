package hn.uth.contactoxtra.ui.crearUsuario;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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
import hn.uth.contactoxtra.database.Usuario;
import hn.uth.contactoxtra.databinding.FragmentRegistroUsuarioBinding;

public class ActualizarUsuarioFragment extends Fragment implements LocationListener {
    private static final int REQUEST_CODE_GPS = 102;
    private FragmentRegistroUsuarioBinding binding;
    private CrearUsuarioViewModel usuarioViewModel;
    private Calendar calendar;
    private LocationManager locationManager;
    private double latitudHogar, longitudHogar, latitudTrabajo, longitudTrabajo;
    private String tipoUbicacion;
    private Usuario usuarioExistente;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegistroUsuarioBinding.inflate(inflater, container, false);
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);

        usuarioViewModel = new ViewModelProvider(requireActivity()).get(CrearUsuarioViewModel.class);

        binding.btnUbicacion.setOnClickListener(v -> obtenerUbicacion("Hogar"));
        binding.btnUbicaciontrabajo.setOnClickListener(v -> obtenerUbicacion("Trabajo"));

        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);
        calendar = Calendar.getInstance();

        // Obtener el usuario existente de los argumentos si los hay
        if (getArguments() != null) {
            usuarioExistente = getArguments().getParcelable("usuario");
            if (usuarioExistente != null) {
                // Rellenar los campos con los datos del usuario existente
                binding.tilNombre.getEditText().setText(usuarioExistente.getNombre());
                binding.tilApellido.getEditText().setText(usuarioExistente.getApellido());
                binding.tilTelefono.getEditText().setText(usuarioExistente.getTelefono());
                binding.txtCumple.setText(usuarioExistente.getFechaCumple());
                binding.tilCorreo.getEditText().setText(usuarioExistente.getCorreo());

                // Rellenar ubicación de hogar
                double latitudHogar = usuarioExistente.getLatitudusuario();
                double longitudHogar = usuarioExistente.getLongitudusuario();
                binding.txtLatitud.setText(String.valueOf(latitudHogar));
                binding.txtLongitud.setText(String.valueOf(longitudHogar));

                // Rellenar ubicación de trabajo
                double latitudTrabajo = usuarioExistente.getLatitudTrabajo();
                double longitudTrabajo = usuarioExistente.getLongitudTrabajo();
                binding.txtLatitudtrabajo.setText(String.valueOf(latitudTrabajo));
                binding.txtLongitudtrabajo.setText(String.valueOf(longitudTrabajo));
            }
        }
        // Configurar el botón de guardar
        binding.btnGuardar.setOnClickListener(v -> saveUsuario());
        binding.btnFecha.setOnClickListener(v -> showDatePickerDialog());
        return binding.getRoot();
    }

    private void obtenerUbicacion(String tipoUbicacion) {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
            this.tipoUbicacion = tipoUbicacion;
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_GPS);
        }
    }

    private boolean hogarObtenido = false;
    private boolean trabajoObtenido = false;

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double latitud = location.getLatitude();
        double longitud = location.getLongitude();
        Snackbar.make(binding.getRoot(), "Obteniendo ubicación...", Snackbar.LENGTH_SHORT).show();

        if ("Hogar".equals(tipoUbicacion)) {
            latitudHogar = latitud;
            longitudHogar = longitud;
            binding.txtLatitud.setText(String.valueOf(latitudHogar));
            binding.txtLongitud.setText(String.valueOf(longitudHogar));
        } else if ("Trabajo".equals(tipoUbicacion)) {
            latitudTrabajo = latitud;
            longitudTrabajo = longitud;
            binding.txtLatitudtrabajo.setText(String.valueOf(latitudTrabajo));
            binding.txtLongitudtrabajo.setText(String.valueOf(longitudTrabajo));
        }
        if (hogarObtenido && trabajoObtenido) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
    private void saveUsuario() {
        String nombre = binding.tilNombre.getEditText().getText().toString().trim();
        String apellido = binding.tilApellido.getEditText().getText().toString().trim();
        String telefono = binding.tilTelefono.getEditText().getText().toString().trim();
        String fechaCumple = binding.txtCumple.getText().toString().trim();
        String correo = binding.tilCorreo.getEditText().getText().toString().trim();

        // Validar campos, actualizar usuario y guardar en la base de datos
        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || telefono.isEmpty() || fechaCumple.isEmpty()) {
            Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar que los campos no sean solo espacios en blanco
        if (nombre.trim().isEmpty() || apellido.trim().isEmpty() || correo.trim().isEmpty() || telefono.trim().isEmpty() || fechaCumple.trim().isEmpty()) {
            Toast.makeText(requireContext(), "Los campos no pueden ser solo espacios en blanco", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nombre.length() < 2 || nombre.length() > 30) {
            binding.tilNombre.setError("El nombre debe tener entre 2 y 30 caracteres");
            binding.tilApellido.setError(null); // Eliminar error en apellido
            binding.tilTelefono.setError(null); // Eliminar error en teléfono
            binding.tilCorreo.setError(null); // Eliminar error en correo
            return;
        }

        if (apellido.length() < 2 || apellido.length() > 30) {
            binding.tilApellido.setError("El apellido debe tener entre 2 y 30 caracteres");
            binding.tilNombre.setError(null); // Eliminar error en nombre
            binding.tilTelefono.setError(null); // Eliminar error en teléfono
            binding.tilCorreo.setError(null); // Eliminar error en correo
            return;
        }

        // Validar que el número de teléfono sea numérico y tenga una longitud válida (por ejemplo, 8 a 15 dígitos)
        if (!TextUtils.isDigitsOnly(telefono) || telefono.length() < 8 || telefono.length() > 15) {
            binding.tilTelefono.setError("Ingrese un número válido (8 a 15 dígitos)");
            binding.tilNombre.setError(null); // Eliminar error en nombre
            binding.tilApellido.setError(null); // Eliminar error en apellido
            binding.tilCorreo.setError(null); // Eliminar error en correo
            return;
        }

        // Validar que el correo tenga un formato válido
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            binding.tilCorreo.setError("Ingrese un correo electrónico válido");
            binding.tilNombre.setError(null); // Eliminar error en nombre
            binding.tilApellido.setError(null); // Eliminar error en apellido
            binding.tilTelefono.setError(null); // Eliminar error en teléfono
            return;
        }

        if (usuarioExistente != null) {
            // Actualizar los datos del usuario existente con los valores ingresados
            usuarioExistente.setNombre(nombre);
            usuarioExistente.setApellido(apellido);
            usuarioExistente.setTelefono(telefono);
            usuarioExistente.setFechaCumple(fechaCumple);
            usuarioExistente.setCorreo(correo);

            // Actualiza las ubicaciones solo si se han obtenido de la ubicación previamente
            if (latitudHogar != 0 || longitudHogar != 0) {
                usuarioExistente.setLatitudusuario(latitudHogar);
                usuarioExistente.setLongitudusuario(longitudHogar);
            }
            if (latitudTrabajo != 0 || longitudTrabajo != 0) {
                usuarioExistente.setLatitudTrabajo(latitudTrabajo);
                usuarioExistente.setLongitudTrabajo(longitudTrabajo);
            }

            // Llama al método de actualización en el ViewModel
            usuarioViewModel.update(usuarioExistente);

            // Mostrar mensaje de éxito y navegar hacia atrás
            Toast.makeText(requireContext(), "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(binding.btnGuardar).navigateUp();
        }
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
        binding.txtCumple.setText(formattedDate);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.VISIBLE);
        binding = null;
    }
}
