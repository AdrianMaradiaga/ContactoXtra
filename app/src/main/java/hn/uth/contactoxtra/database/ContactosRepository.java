package hn.uth.contactoxtra.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactosRepository {

    private ContactosDao contactosDao;
    private LiveData<List<Contactos>> dataset;

    public ContactosRepository(Application application) {
        ContactoXtraDatabase database = ContactoXtraDatabase.getDatabase(application);
        contactosDao = database.contactosDao();
        dataset = contactosDao.getContactos();
    }

    public long insert(Contactos nuevo) {
        return contactosDao.insert(nuevo);
    }

    public void update(Contactos actualizar) {
        contactosDao.update(actualizar);
    }

    public void delete(Contactos eliminar) {
        contactosDao.delete(eliminar);
    }

    public void deleteAll() {
        contactosDao.deleteAll();
    }

    public LiveData<List<Contactos>> getDataset() {
        return dataset;
    }

    public LiveData<List<Contactos>> buscarContacto(String nombre) {
        return contactosDao.buscarContacto(nombre);
    }
}

