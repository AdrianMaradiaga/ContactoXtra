package hn.uth.contactoxtra.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(version = 3, exportSchema = false, entities = {Usuario.class})
public abstract class UsuarioDatabase extends RoomDatabase {

    public abstract UsuarioDao usuarioDao();

    private static volatile UsuarioDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    // ExecutorService para ejecutar operaciones de escritura en el hilo de fondo
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static UsuarioDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UsuarioDatabase.class) {
                if (INSTANCE == null) {
                    Callback miCallback = new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);

                            databaseWriteExecutor.execute(() -> {
                                UsuarioDao usuarioDao = INSTANCE.usuarioDao();

                                usuarioDao.deleteAll();
                            });
                        }
                    };
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UsuarioDatabase.class, "usuariosxtra_db").addCallback(miCallback).build();
                }
            }
        }
        return INSTANCE;
    }
}