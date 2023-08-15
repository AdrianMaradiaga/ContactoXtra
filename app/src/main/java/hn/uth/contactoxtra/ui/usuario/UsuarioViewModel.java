package hn.uth.contactoxtra.ui.usuario;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import hn.uth.contactoxtra.database.Usuario;
import hn.uth.contactoxtra.database.UsuarioRepository;

public class UsuarioViewModel extends AndroidViewModel {

    private UsuarioRepository usuarioRepository;
    private LiveData<Usuario> usuarioLiveData;

    public UsuarioViewModel(@NonNull Application application) {
        super(application);
        usuarioRepository = new UsuarioRepository(application);
        usuarioLiveData = usuarioRepository.getUsuarioLiveData();
    }

    public LiveData<Usuario> getUsuarioLiveData() {
        return usuarioLiveData;
    }

    public void insert(Usuario usuario){
        usuarioRepository.insert(usuario);
    }
    public void delete(Usuario usuario){
        usuarioRepository.delete(usuario);
    }

    public LiveData<Usuario> getUsuarioById(long usuarioId) {
        return usuarioRepository.getUsuarioById(usuarioId);
    }
}
