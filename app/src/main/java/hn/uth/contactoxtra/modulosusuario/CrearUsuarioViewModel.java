package hn.uth.contactoxtra.modulosusuario;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import hn.uth.contactoxtra.database.Contactos;
import hn.uth.contactoxtra.database.ContactosRepository;
import hn.uth.contactoxtra.database.Usuario;
import hn.uth.contactoxtra.database.UsuarioRepository;

public class CrearUsuarioViewModel extends AndroidViewModel {


    private UsuarioRepository repository;
    private LiveData<List<Usuario>> allUsuarios;

    public CrearUsuarioViewModel(@NonNull Application app){
        super(app);
        this.repository = new UsuarioRepository(app);
    }
    public LiveData<List<Usuario>> allUsuarios() {
        return allUsuarios;
    }

    public void insert(Usuario usuario) {
        repository.insert(usuario);
    }
    public void update(Usuario actualizar){
        repository.update(actualizar);
    }

}
