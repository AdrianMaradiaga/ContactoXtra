package hn.uth.contactoxtra.database;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UsuarioDao {
    @Insert
    long insert(Usuario nuevo);

    @Update
    void update(Usuario actualizar);

    @Delete
    void delete(Usuario eliminar);

    @Query("DELETE FROM usuarios_table")
    void deleteAll();

    @Query("SELECT * FROM usuarios_table LIMIT 1")
    LiveData<Usuario> getUsuario();

    @Query("SELECT * FROM usuarios_table WHERE usuario_id = :usuarioId")
    LiveData<Usuario> getUsuarioById(long usuarioId);
}
