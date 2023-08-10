package hn.uth.contactoxtra.ui.crearContactos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import hn.uth.contactoxtra.database.Contactos;
import hn.uth.contactoxtra.database.ContactosRepository;

public class CrearContactoViewModel extends AndroidViewModel {
    private ContactosRepository repository;
    private LiveData<List<Contactos>> allContactos;

    public CrearContactoViewModel(@NonNull Application app){
        super(app);
        this.repository = new ContactosRepository(app);
    }
    public LiveData<List<Contactos>> getAllContactos() {
        return allContactos;
    }

    public void insert(Contactos contactos) {
        repository.insert(contactos);
    }

}
