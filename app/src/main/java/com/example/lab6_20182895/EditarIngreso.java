package com.example.lab6_20182895;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab6_20182895.databinding.EditarIngresoBinding;

public class EditarIngreso extends AppCompatActivity {

    private EditarIngresoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EditarIngresoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtener los extras del Intent
        String titulo = getIntent().getStringExtra("titulo");
        double monto = getIntent().getDoubleExtra("monto", 0.0);
        String descripcion = getIntent().getStringExtra("descripcion");
        String fecha = getIntent().getStringExtra("fecha");

        // Llenar los campos con los datos recibidos
        binding.campoTitulo.setText(titulo);
        binding.campoMonto.setText(String.valueOf(monto));
        binding.campoDescripcion.setText(descripcion);
        binding.campoFecha.setText(fecha);
    }
}
