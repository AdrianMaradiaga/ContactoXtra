package hn.uth.contactoxtra.ui.crearContactos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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

        // Obtener el objeto Contactos de los argumentos del fragmento
        Contactos contacto = getArguments().getParcelable("contacto");

        // Asignar los valores del objeto Contactos a los elementos de vista
        if (contacto != null) {
            tvNombreContacto.setText(contacto.getNombre());
            tvApellidoContacto.setText(contacto.getApellido());
            tvTelefonoContacto.setText(contacto.getTelefono());
            tvCumplContacto.setText(contacto.getFechaCumple());
            tvCorreoContacto.setText(contacto.getCorreo());
        }

        Button btnCompartir = rootView.findViewById(R.id.btnCompartirContacto);
        btnCompartir.setOnClickListener(v -> compartirContacto(contacto));

        return rootView;
    }

    private void compartirContacto(Contactos contacto) {
        if (contacto != null) {
            String textoCompartir = "Nombre: " + contacto.getNombre() +
                    "\nApellido: " + contacto.getApellido() +
                    "\nTeléfono: " + contacto.getTelefono() +
                    "\nCumpleaños: " + contacto.getFechaCumple() +
                    "\nCorreo: " + contacto.getCorreo();
            // Agrega el valor de ubicación si lo tienes en tu objeto de contacto
            // textoCompartir += "\nUbicación: " + contacto.getUbicacion();

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

