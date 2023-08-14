package hn.uth.contactoxtra.ui.crearContactos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import hn.uth.contactoxtra.database.Contactos;
import hn.uth.contactoxtra.database.ContactosRepository;
import hn.uth.contactoxtra.database.Ubicacion;
import hn.uth.contactoxtra.database.UbicacionRepository;

public class CrearContactoViewModel extends AndroidViewModel {
    private ContactosRepository repository;
    private UbicacionRepository repositoryUbicacion;

    private LiveData<List<Contactos>> allContactos;

    public CrearContactoViewModel(@NonNull Application app){
        super(app);
        this.repository = new ContactosRepository(app);
        this.repositoryUbicacion = new UbicacionRepository(app);
    }
    public LiveData<List<Contactos>> getAllContactos() {
        return allContactos;
    }

    public void insert(Contactos contactos) {
        repository.insert(contactos);
    }
    public void insert(Ubicacion ubicacion) {
        repositoryUbicacion.insert(ubicacion);
    }

    public void update(Contactos actualizar){
        repository.update(actualizar);
    }

}
