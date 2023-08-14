package hn.uth.contactoxtra.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UbicacionDao {

    @Insert
    long insert(Ubicacion nuevo);

    @Update
    void update(Ubicacion actualizar);

    @Delete
    void delete(Ubicacion eliminar);
    @Query("DELETE FROM UBICACION_TABLE")
    void deleteAll();

    @Query("SELECT * FROM ubicacion_table")
    LiveData<List<Ubicacion>> getUbicaciones();

    @Query("SELECT * FROM ubicacion_table WHERE " +
            "REPLACE(persona || ' ' || categoria, ' ', '') " +
            "LIKE '%' || REPLACE(:query, ' ', '') || '%'")
    LiveData<List<Ubicacion>> buscarUbicacion(String query);
}
