package hn.uth.contactoxtra;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import hn.uth.contactoxtra.databinding.ActivityMainBinding;
import hn.uth.contactoxtra.ui.usuario.UsuarioViewModel;
import hn.uth.contactoxtra.ui.usuario.UsuarioViewModelFactory;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private UsuarioViewModel usuarioViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_ubicaciones, R.id.navigation_usuario, R.id.navigation_contactos)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

        UsuarioViewModelFactory viewModelFactory = new UsuarioViewModelFactory(getApplication());

        // Crear la instancia de UsuarioViewModel usando UsuarioViewModelFactory
        usuarioViewModel = new ViewModelProvider(this, viewModelFactory).get(UsuarioViewModel.class);

    }
}

//public class MainActivity extends AppCompatActivity {
//
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.fragment_registro_usuario);
////    }
//}