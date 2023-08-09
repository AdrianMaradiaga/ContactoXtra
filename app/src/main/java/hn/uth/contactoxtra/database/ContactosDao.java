package hn.uth.contactoxtra.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactosDao {

    @Insert
    long insert(Contactos nuevo);

    @Update
    void update(Contactos actualizar);

    @Delete
    void delete(Contactos eliminar);

    @Query("DELETE FROM contactos_table")
    void deleteAll();

    @Query("SELECT * FROM contactos_table")
    LiveData<List<Contactos>> getContactos();

    @Query("SELECT * FROM contactos_table WHERE nombre_contacto LIKE '%' || :nombre || '%'")
    LiveData<List<Contactos>> buscarContacto(String nombre);
}
