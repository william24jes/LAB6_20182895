package com.example.lab6_20182895;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab6_20182895.databinding.InicioBinding;

public class Inicio extends AppCompatActivity {
    private InicioBinding inicioBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicioBinding = InicioBinding.inflate(getLayoutInflater());
        setContentView(inicioBinding.getRoot());
    }
}
