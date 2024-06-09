package com.example.lab6_20182895;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.lab6_20182895.databinding.InicioBinding;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Inicio extends AppCompatActivity {

    private InicioBinding inicioBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicioBinding = InicioBinding.inflate(getLayoutInflater());
        setContentView(inicioBinding.getRoot());

        // Configurar el listener para la barra de navegación
        inicioBinding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemID = menuItem.getItemId();

                if (itemID == R.id.ingresos) {
                    loadFragment(new Ingresos(), false);
                } else if (itemID == R.id.egresos) {
                    loadFragment(new Egresos(), false);
                } else if (itemID == R.id.resumen) {
                    loadFragment(new Resumen(), false);
                } else if (itemID == R.id.log_out) {
                    AuthUI.getInstance()
                            .signOut(Inicio.this) // Usar el contexto de la actividad
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d("infoApp", "logout exitoso");
                                    Intent intent = new Intent(Inicio.this, InicioSesion.class);
                                    startActivity(intent);
                                    finish(); // Finalizar la actividad actual
                                }
                            });
                }
                return true;
            }
        });

        // Cargar el fragmento inicial
        loadFragment(new Ingresos(), true);
    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isAppInitialized) {
            fragmentTransaction.add(R.id.frame_layout, fragment);
        } else {
            fragmentTransaction.replace(R.id.frame_layout, fragment);
        }

        fragmentTransaction.commit();
    }
}
