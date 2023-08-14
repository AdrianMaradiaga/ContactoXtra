package hn.uth.contactoxtra.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


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

    @Query("SELECT * FROM usuarios_table")
    LiveData<List<Usuario>> getUsuario();

    @Query("SELECT * FROM usuarios_table WHERE " +
            "REPLACE(nombre_usuario || ' ' || apellido_usuario, ' ', '') " +
            "LIKE '%' || REPLACE(:query, ' ', '') || '%'")
    LiveData<List<Usuario>> buscarUsuario(String query);

}
