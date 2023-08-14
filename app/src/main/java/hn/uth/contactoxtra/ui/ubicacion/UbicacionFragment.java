package hn.uth.contactoxtra.ui.ubicacion;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import hn.uth.contactoxtra.database.Ubicacion;
import hn.uth.contactoxtra.databinding.FragmentUbicacionesBinding;
import hn.uth.contactoxtra.ui.OnItemClickListener;
import java.util.ArrayList;
public class UbicacionFragment extends Fragment implements OnItemClickListener<Ubicacion> {
    private UbicacionAdapter adaptador;
    private UbicacionViewModel viewModel;
    private FragmentUbicacionesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUbicacionesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(UbicacionViewModel.class);

        adaptador = new UbicacionAdapter(getContext(), new ArrayList<>(), this, viewModel);
        viewModel.getUbicacionDataset().observe(getViewLifecycleOwner(), ubicaciones -> adaptador.setItems(ubicaciones));

        setupRecyclerView();
        setupSearchView();

        return root;
    }

    private void setupSearchView() {
        binding.buscarUbicaciones.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                buscarUbicacionesporNombreApellido(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscarUbicacionesporNombreApellido(newText);
                return false;
            }
        });
    }

    private void buscarUbicacionesporNombreApellido(String query) {
        viewModel.buscarUbicacion(query)
                .observe(getViewLifecycleOwner(), ubicacions -> adaptador.setItems(ubicacions));
    }
    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rvUbicaciones.setLayoutManager(linearLayoutManager);
        binding.rvUbicaciones.setAdapter(adaptador);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(Ubicacion data) {
    }
}
