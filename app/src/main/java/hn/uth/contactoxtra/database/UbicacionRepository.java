package hn.uth.contactoxtra.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UbicacionRepository {

    private UbicacionDao ubicacionDao;
    private LiveData<List<Ubicacion>> dataset;

    public UbicacionRepository(Application application) {
        ContactoXtraDatabase database = ContactoXtraDatabase.getDatabase(application);
        ubicacionDao = database.ubicacionDao();
        dataset = ubicacionDao.getUbicaciones();
    }

    public void insert(Ubicacion nuevo) {
        ContactoXtraDatabase.databaseWriteExecutor.execute(() -> {
            ubicacionDao.insert(nuevo);
        });
    }

    public void update(Ubicacion actualizar) {
        ContactoXtraDatabase.databaseWriteExecutor.execute(() -> {
            ubicacionDao.update(actualizar);
        });
    }

    public void delete(Ubicacion eliminar) {
        ContactoXtraDatabase.databaseWriteExecutor.execute(() -> {
            ubicacionDao.delete(eliminar);
        });
    }

    public void deleteAll() {
        ContactoXtraDatabase.databaseWriteExecutor.execute(() -> {
            ubicacionDao.deleteAll();
        });
    }

    public LiveData<List<Ubicacion>> getDataset() {
        return dataset;
    }

    public LiveData<List<Ubicacion>> buscarUbicacion(String query) {
        return ubicacionDao.buscarUbicacion(query);
    }
}

