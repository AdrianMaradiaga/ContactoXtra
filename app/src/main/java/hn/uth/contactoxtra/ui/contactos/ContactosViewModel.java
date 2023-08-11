package hn.uth.contactoxtra.ui.contactos;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

import hn.uth.contactoxtra.database.Contactos;
import hn.uth.contactoxtra.database.ContactosRepository;

public class ContactosViewModel extends AndroidViewModel {

    private ContactosRepository repository;
    private final LiveData<List<Contactos>> contactosDataset;

    public ContactosViewModel(Application application) {
        super(application);
        this.repository = new ContactosRepository(application);
        this.contactosDataset = repository.getDataset();
    }

    public ContactosRepository getRepository() {
        return repository;
    }

    public LiveData<List<Contactos>> getContactosDataset(){
        return contactosDataset;
    }
    public LiveData<List<Contactos>> buscarContacto(String query) {
        return repository.buscarContacto("%" + query + "%");
    }



    public void insert(Contactos contacto) {
        repository.insert(contacto);
    }

    public void update(Contactos contacto) {
        repository.update(contacto);
    }

    public void delete(Contactos contacto) {
        repository.delete(contacto);
    }
}
