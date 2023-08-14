package hn.uth.contactoxtra.ui.usuario;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UsuarioViewModelFactory implements ViewModelProvider.Factory {
    private Application application;

    public UsuarioViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UsuarioViewModel.class)) {
            return (T) new UsuarioViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}