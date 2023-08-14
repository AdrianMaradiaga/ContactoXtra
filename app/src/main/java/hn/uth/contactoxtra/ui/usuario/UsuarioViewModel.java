package hn.uth.contactoxtra.ui.usuario;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.List;

import hn.uth.contactoxtra.database.Usuario;
import hn.uth.contactoxtra.database.UsuarioRepository;

public class UsuarioViewModel extends ViewModel {

    private MutableLiveData<Usuario> usuarioLiveData = new MutableLiveData<>();
    private UsuarioRepository usuarioRepository;

    public UsuarioViewModel(Application application) {
        usuarioRepository = new UsuarioRepository(application);
    }

    public void setUsuario(Usuario usuario) {
        usuarioLiveData.setValue(usuario);
    }

    public LiveData<Usuario> getUsuario() {
        return usuarioLiveData;
    }

    public LiveData<Boolean> tieneUsuario() {
        MutableLiveData<Boolean> tieneUsuarioLiveData = new MutableLiveData<>();

        usuarioRepository.getDataset().observeForever(new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                tieneUsuarioLiveData.setValue(usuarios != null && !usuarios.isEmpty());
            }
        });

        return tieneUsuarioLiveData;
    }
}