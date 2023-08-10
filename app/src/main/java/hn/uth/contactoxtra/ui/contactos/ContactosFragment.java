package hn.uth.contactoxtra.ui.contactos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import hn.uth.contactoxtra.R;
import hn.uth.contactoxtra.database.Contactos;

import hn.uth.contactoxtra.databinding.FragmentContactosBinding;

import hn.uth.contactoxtra.ui.OnItemClickListener;

public class ContactosFragment extends Fragment implements OnItemClickListener<Contactos> {

    private FragmentContactosBinding binding;
    private ContactosAdapter adaptador;
    private ContactosViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentContactosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(ContactosViewModel.class);

        adaptador = new ContactosAdapter(getContext(), new ArrayList<>(), this, viewModel);
        viewModel.getContactosDataset().observe(getViewLifecycleOwner(), contactos -> adaptador.setItems(contactos));

        setupRecyclerView();

        FloatingActionButton fabAgregarContacto = root.findViewById(R.id.fabAgregarContacto);
        fabAgregarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener el NavController
                NavController navController = Navigation.findNavController(view);

                // Navegar al fragmento CrearContactoFragment
                navController.navigate(R.id.action_fragmentActual_to_crearContactoFragment);
            }
        });

        return root;
    }



    private void setupRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rvContactos.setLayoutManager(linearLayoutManager);
        binding.rvContactos.setAdapter(adaptador);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(Contactos data) {

    }

}