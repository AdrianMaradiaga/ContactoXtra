package hn.uth.contactoxtra.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(version = 6, exportSchema = false, entities = {Contactos.class, Ubicacion.class, Usuario.class})
public abstract class ContactoXtraDatabase extends RoomDatabase {
    public abstract ContactosDao contactosDao();
    public abstract UbicacionDao ubicacionDao();
    public abstract UsuarioDao usuarioDao();
    private static volatile ContactoXtraDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ContactoXtraDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactoXtraDatabase.class) {
                if (INSTANCE == null) {
                    Callback miCallback = new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);

                            databaseWriteExecutor.execute(() -> {
                                ContactosDao contactosDao = INSTANCE.contactosDao();
                                UbicacionDao ubicacionDao = INSTANCE.ubicacionDao();
                                UsuarioDao usuarioDao = INSTANCE.usuarioDao();

                                ubicacionDao.deleteAll();
                                contactosDao.deleteAll();
                                usuarioDao.deleteAll();
                            });
                        }
                    };
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ContactoXtraDatabase.class, "contactoxtra_db").addCallback(miCallback).build();
                }
            }
        }
        return INSTANCE;
    }
}