package hn.uth.contactoxtra.ui.crearUsuario;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import hn.uth.contactoxtra.database.Usuario;
import hn.uth.contactoxtra.database.UsuarioRepository;

public class CrearUsuarioViewModel extends AndroidViewModel {

    private UsuarioRepository usuarioRepository;
    private LiveData<Usuario> usuarioLiveData;

    public CrearUsuarioViewModel(@NonNull Application application) {
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
    public void update(Usuario usuario){
        usuarioRepository.update(usuario);
    }
}

