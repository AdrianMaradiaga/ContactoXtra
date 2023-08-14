package hn.uth.contactoxtra.ui.ubicacion;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import hn.uth.contactoxtra.database.Ubicacion;
import hn.uth.contactoxtra.database.UbicacionRepository;

public class UbicacionViewModel extends AndroidViewModel {


    private UbicacionRepository repository;
    private final LiveData<List<Ubicacion>> ubicacionDataset;


    public UbicacionViewModel(Application application) {
        super(application);
        this.repository = new UbicacionRepository(application);
        this.ubicacionDataset = repository.getDataset();
    }

    public UbicacionRepository getRepository() {
        return repository;
    }

    public LiveData<List<Ubicacion>> getUbicacionDataset(){
        return ubicacionDataset;
    }


    public LiveData<List<Ubicacion>> buscarUbicacion(String query) {
        return repository.buscarUbicacion("%" + query + "%");
    }

    public void insert(Ubicacion ubicacion) {
        repository.insert(ubicacion);
    }

    public void update(Ubicacion ubicacion) {
        repository.update(ubicacion);
    }

    public void delete(Ubicacion ubicacion) {
        repository.delete(ubicacion);
    }
}
