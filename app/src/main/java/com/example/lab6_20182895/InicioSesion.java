package com.example.lab6_20182895;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab6_20182895.databinding.Login2Binding;

public class InicioSesion extends AppCompatActivity {
    private Login2Binding login2Binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializa el binding
        login2Binding = Login2Binding.inflate(getLayoutInflater());

        // Configura el contenido de la vista con el binding root
        setContentView(login2Binding.getRoot());

    }
}