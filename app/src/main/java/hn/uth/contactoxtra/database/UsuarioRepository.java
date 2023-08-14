package hn.uth.contactoxtra.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UsuarioRepository {

    private UsuarioDao usuariosDao;
    private LiveData<List<Usuario>> dataset;

    public UsuarioRepository(Application application) {
        UsuarioDatabase database = UsuarioDatabase.getDatabase(application);
        usuariosDao = database.usuarioDao();
        dataset = usuariosDao.getUsuario();
    }

    public void insert(Usuario nuevo) {
        UsuarioDatabase.databaseWriteExecutor.execute(()->{
            usuariosDao.insert(nuevo);
        });
    }

    public void update(Usuario actualizar) {
        UsuarioDatabase.databaseWriteExecutor.execute(() -> {
            usuariosDao.update(actualizar);
        });
    }

    public void delete(Usuario eliminar) {
        UsuarioDatabase.databaseWriteExecutor.execute(() -> {
            usuariosDao.delete(eliminar);
        });
    }

    public void deleteAll() {
        UsuarioDatabase.databaseWriteExecutor.execute(() -> {
            usuariosDao.deleteAll();
        });
    }


    public LiveData<List<Usuario>> getDataset() {
        return dataset;
    }

    public LiveData<List<Usuario>> buscarUsuario(String query) {
        return usuariosDao.buscarUsuario(query);
    }

}

