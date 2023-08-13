package hn.uth.contactoxtra.ui.crearContactos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import hn.uth.contactoxtra.R;
import hn.uth.contactoxtra.database.Contactos;

public class DetalleContactoFragment extends Fragment {
    private DetalleContactoFragment binding;
    private TextView tvNombreContacto;
    private TextView tvApellidoContacto;
    private TextView tvTelefonoContacto;
    private TextView tvCumplContacto;
    private TextView tvCorreoContacto;
    private TextView tvUbicacionContacto;
    private TextView tvTrabajoContacto;
    private Contactos contacto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detalle_contacto, container, false);

        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);

        tvNombreContacto = rootView.findViewById(R.id.tvNombreContacto);
        tvApellidoContacto = rootView.findViewById(R.id.tvApellidoContacto);
        tvTelefonoContacto = rootView.findViewById(R.id.tvTelefonoContacto);
        tvCumplContacto = rootView.findViewById(R.id.tvCumplContacto);
        tvCorreoContacto = rootView.findViewById(R.id.tvCorreoContacto);
        tvUbicacionContacto = rootView.findViewById(R.id.tvUbicacionContacto);
        tvTrabajoContacto = rootView.findViewById(R.id.tvTrabajoContacto);
        // Obtener el objeto Contactos de los argumentos del fragmento
        contacto = getArguments().getParcelable("contacto");

        // Asignar los valores del objeto Contactos a los elementos de vista
        if (contacto != null) {
            tvNombreContacto.setText(contacto.getNombre());
            tvApellidoContacto.setText(contacto.getApellido());
            tvTelefonoContacto.setText(contacto.getTelefono());
            tvCumplContacto.setText(contacto.getFechaCumple());
            tvCorreoContacto.setText(contacto.getCorreo());

            // Concatenar latitud y longitud del hogar en el mismo TextView
            String ubicacionHogar = "Latitud: " + contacto.getLatitudHogar() +
                    ", Longitud: " + contacto.getLongitudHogar();
            tvUbicacionContacto.setText(ubicacionHogar);

            String ubicacionTrabajo = "Latitud: " + contacto.getLatitudTrabajo() +
                    ", Longitud: " + contacto.getLongitudTrabajo();
            tvTrabajoContacto.setText(ubicacionTrabajo);
        }

        Button btnCompartir = rootView.findViewById(R.id.btnCompartirContacto);
        btnCompartir.setOnClickListener(v -> compartirContacto(contacto));

        Button btnAbrirMapa = rootView.findViewById(R.id.btnAbrirMapa);
        btnAbrirMapa.setOnClickListener(v -> abrirUbicacionHogar());

        Button btnMapaTrabajo = rootView.findViewById(R.id.btnMapaTrabajo);
        btnMapaTrabajo.setOnClickListener(v -> abrirUbicacionTrabajo());


        return rootView;
    }



    public void abrirUbicacionHogar() {
        if (contacto != null && contacto.getLatitudTrabajo() != 0 && contacto.getLongitudTrabajo() != 0) {
            double latitud = contacto.getLatitudTrabajo();
            double longitud = contacto.getLongitudTrabajo();

            // Crea un intent para abrir Google Maps con la ubicación
            Uri gmmIntentUri = Uri.parse("geo:" + latitud + "," + longitud + "?q=" + latitud + "," + longitud);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            startActivity(mapIntent);
        }
    }

    public void abrirUbicacionTrabajo() {
        if (contacto != null && contacto.getLatitudHogar() != 0 && contacto.getLongitudHogar() != 0) {
            double latitud = contacto.getLatitudHogar();
            double longitud = contacto.getLongitudHogar();

            // Crea un intent para abrir Google Maps con la ubicación
            Uri gmmIntentUri = Uri.parse("geo:" + latitud + "," + longitud + "?q=" + latitud + "," + longitud);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            startActivity(mapIntent);
        }
    }


    private void compartirContacto(Contactos contacto) {
        if (contacto != null) {
            String textoCompartir = "Nombre: " + contacto.getNombre() +
                    "\nApellido: " + contacto.getApellido() +
                    "\nTeléfono: " + contacto.getTelefono() +
                    "\nCumpleaños: " + contacto.getFechaCumple() +
                    "\nCorreo: " + contacto.getCorreo();

            // Concatenar la ubicación del hogar si está disponible
            if (contacto.getLatitudHogar() != 0 && contacto.getLongitudHogar() != 0) {
                String ubicacionHogar = "Ubicación Hogar: " +
                        "\nLatitud: " + contacto.getLatitudHogar() +
                        "\nLongitud: " + contacto.getLongitudHogar();
                textoCompartir += "\n" + ubicacionHogar;
            }

            if (contacto.getLatitudTrabajo() != 0 && contacto.getLongitudTrabajo() != 0) {
                String ubicacionTrabajo = "Ubicación Trabajo: " +
                        "\nLatitud: " + contacto.getLatitudTrabajo() +
                        "\nLongitud: " + contacto.getLongitudTrabajo();
                textoCompartir += "\n" + ubicacionTrabajo;
            }

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Detalle de contacto");
            shareIntent.putExtra(Intent.EXTRA_TEXT, textoCompartir);

            startActivity(Intent.createChooser(shareIntent, "Compartir Detalle de Contacto"));
        }
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.VISIBLE);
        binding = null;
    }
}

