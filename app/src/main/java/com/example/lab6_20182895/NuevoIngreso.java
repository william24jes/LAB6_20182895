package com.example.lab6_20182895;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab6_20182895.databinding.NuevoIngresoBinding;

public class NuevoIngreso extends AppCompatActivity {
    private NuevoIngresoBinding nuevoIngresoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nuevoIngresoBinding = NuevoIngresoBinding.inflate(getLayoutInflater());
        setContentView(nuevoIngresoBinding.getRoot());
    }
}
