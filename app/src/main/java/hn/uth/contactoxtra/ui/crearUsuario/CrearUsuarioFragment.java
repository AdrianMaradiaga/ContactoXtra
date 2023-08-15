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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import hn.uth.contactoxtra.R;
import hn.uth.contactoxtra.database.Usuario;
import hn.uth.contactoxtra.databinding.FragmentRegistroUsuarioBinding;

public class CrearUsuarioFragment extends Fragment implements LocationListener {

    private static final int REQUEST_CODE_GPS = 102;
    private FragmentRegistroUsuarioBinding binding;
    private CrearUsuarioViewModel viewModel;
    private Calendar calendar;
    private LocationManager locationManager;
    private double latitudusuario, longitudusuario, latitudTrabajo, longitudTrabajo;
    private String tipoUbicacion;
    private enum UbicacionEstado {
        NINGUNA, HOGAR, TRABAJO
    }
    private UbicacionEstado estadoUbicacion = UbicacionEstado.NINGUNA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegistroUsuarioBinding.inflate(inflater, container, false);
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);

        binding.btnUbicacion.setOnClickListener(v -> obtenerUbicacion("Hogar"));
        binding.btnUbicaciontrabajo.setOnClickListener(v -> obtenerUbicacion("Trabajo"));

        // Ocultar el Bottom Navigation
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(CrearUsuarioViewModel.class);
        calendar = Calendar.getInstance();

        binding.btnFecha.setOnClickListener(v -> showDatePickerDialog());

        binding.btnGuardar.setOnClickListener(v -> saveUser());
    }

    private void obtenerUbicacion(String tipoUbicacion) {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            estadoUbicacion = tipoUbicacion.equals("Hogar") ? UbicacionEstado.HOGAR : UbicacionEstado.TRABAJO;
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_GPS);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double latitud = location.getLatitude();
        double longitud = location.getLongitude();

        if (estadoUbicacion == UbicacionEstado.HOGAR) {
            latitudusuario = latitud;
            longitudusuario = longitud;
            binding.txtLatitud.setText(String.valueOf(latitudusuario));
            binding.txtLongitud.setText(String.valueOf(longitudusuario));
        } else if (estadoUbicacion == UbicacionEstado.TRABAJO) {
            latitudTrabajo = latitud;
            longitudTrabajo = longitud;
            binding.txtLatitudtrabajo.setText(String.valueOf(latitudTrabajo));
            binding.txtLongitudtrabajo.setText(String.valueOf(longitudTrabajo));
        }

        locationManager.removeUpdates(this);
        estadoUbicacion = UbicacionEstado.NINGUNA;
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

    private void saveUser() {
        // Obtener los valores de los campos
        String nombre = binding.tilNombre.getEditText().getText().toString().trim();
        String apellido = binding.tilApellido.getEditText().getText().toString().trim();
        String correo = binding.tilCorreo.getEditText().getText().toString().trim();
        String telefono = binding.tilTelefono.getEditText().getText().toString().trim();
        String fechaCumple = binding.txtCumple.getText().toString().trim();

        // Validar campos, crear usuario y guardar en la base de datos
        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || telefono.isEmpty() || fechaCumple.isEmpty()) {
            Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar que los campos no sean solo espacios en blanco
        if (nombre.trim().isEmpty() || apellido.trim().isEmpty() || correo.trim().isEmpty() || telefono.trim().isEmpty() || fechaCumple.trim().isEmpty()) {
            Toast.makeText(requireContext(), "Los campos no pueden ser solo espacios en blanco", Toast.LENGTH_SHORT).show();
            return;
        }


        // Validar longitud mínima y máxima para nombre y apellido (por ejemplo, 2 a 30 caracteres)
        if (nombre.length() < 2 || nombre.length() > 30) {
            binding.tilNombre.setError("El nombre debe tener entre 2 y 30 caracteres");
            binding.tilApellido.setError(null); // Eliminar error en apellido
            binding.tilTelefono.setError(null); // Eliminar error en teléfono
            binding.tilCorreo.setError(null); // Eliminar error en correo
            return;
        }

        // Validar longitud mínima y máxima para apellido (por ejemplo, 2 a 30 caracteres)
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
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            binding.tilCorreo.setError("Ingrese un correo electrónico válido");
            binding.tilNombre.setError(null); // Eliminar error en nombre
            binding.tilApellido.setError(null); // Eliminar error en apellido
            binding.tilTelefono.setError(null); // Eliminar error en teléfono
            return;
        }

        Usuario nuevoUsuario = new Usuario(nombre, apellido, correo, telefono, fechaCumple,  latitudusuario, longitudusuario, latitudTrabajo, longitudTrabajo);

        // Guardar el Usuario usando el ViewModel
        viewModel.insert(nuevoUsuario);

        // Mostrar un mensaje de éxito
        Toast.makeText(requireContext(), "Usuario guardado correctamente", Toast.LENGTH_SHORT).show();

        // Limpiar los campos
        binding.tilNombre.getEditText().setText("");
        binding.tilApellido.getEditText().setText("");
        binding.tilCorreo.getEditText().setText("");
        binding.tilTelefono.getEditText().setText("");
        binding.txtCumple.setText("");

        // Regresar a la pantalla anterior
        NavController navController = Navigation.findNavController(requireView());
        navController.popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.VISIBLE);
        binding = null;
    }
}
