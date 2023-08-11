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

    public void insert(Contactos nuevo) {
        ContactoXtraDatabase.databaseWriteExecutor.execute(()->{
            contactosDao.insert(nuevo);
        });
    }

    public void update(Contactos actualizar) {
        ContactoXtraDatabase.databaseWriteExecutor.execute(() -> {
            contactosDao.update(actualizar);
        });
    }

    public void delete(Contactos eliminar) {
        ContactoXtraDatabase.databaseWriteExecutor.execute(() -> {
            contactosDao.delete(eliminar);
        });
    }

    public void deleteAll() {
        ContactoXtraDatabase.databaseWriteExecutor.execute(() -> {
            contactosDao.deleteAll();
        });
    }


    public LiveData<List<Contactos>> getDataset() {
        return dataset;
    }

    public LiveData<List<Contactos>> buscarContacto(String query) {
        return contactosDao.buscarContacto(query);
    }

}

