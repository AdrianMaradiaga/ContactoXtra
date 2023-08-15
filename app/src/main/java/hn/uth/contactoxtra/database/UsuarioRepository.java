package hn.uth.contactoxtra.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

public class UsuarioRepository {

    private UsuarioDao usuariosDao;
    private LiveData<Usuario> usuarioLiveData;

    public UsuarioRepository(Application application) {
        ContactoXtraDatabase database = ContactoXtraDatabase.getDatabase(application);
        usuariosDao = database.usuarioDao();
        usuarioLiveData = usuariosDao.getUsuario();
    }

    public void insert(Usuario nuevo) {
        ContactoXtraDatabase.databaseWriteExecutor.execute(() -> {
            usuariosDao.insert(nuevo);

        });
    }

    public void update(Usuario actualizar) {
        ContactoXtraDatabase.databaseWriteExecutor.execute(() -> {
            usuariosDao.update(actualizar);
        });
    }

    public void delete(Usuario eliminar) {
        ContactoXtraDatabase.databaseWriteExecutor.execute(() -> {
            usuariosDao.delete(eliminar);
        });
    }

    public void deleteAll() {
        ContactoXtraDatabase.databaseWriteExecutor.execute(() -> {
            usuariosDao.deleteAll();
        });
    }



    public LiveData<Usuario> getUsuarioLiveData() {
        return usuarioLiveData;
    }
}
