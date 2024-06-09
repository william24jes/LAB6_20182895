package com.example.lab6_20182895;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab6_20182895.databinding.EditarIngresoBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditarIngreso extends AppCompatActivity {

    private EditarIngresoBinding binding;
    private FirebaseFirestore db;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EditarIngresoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

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

        binding.botonGuardar.setOnClickListener(v -> guardarCambios(titulo));

        binding.botonEliminar.setOnClickListener(v -> eliminarIngreso(titulo));

    }

    private void guardarCambios(String tituloOriginal) {
        String titulo = binding.campoTitulo.getText().toString();
        double monto = Double.parseDouble(binding.campoMonto.getText().toString());
        String descripcion = binding.campoDescripcion.getText().toString();
        String fecha = binding.campoFecha.getText().toString();

        // Actualizar los datos en Firestore
        DocumentReference ingresoRef = db.collection("usuarios")
                .document(user.getUid())
                .collection("Ingresos")
                .document(tituloOriginal);

        ingresoRef.update("titulo", titulo,
                        "monto", monto,
                        "descripcion", descripcion,
                        "fecha", fecha)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditarIngreso.this, "Datos actualizados exitosamente", Toast.LENGTH_SHORT).show();
                    finish(); // Cierra la actividad actual y regresa al fragmento anterior
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditarIngreso.this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
                });
    }

    private void eliminarIngreso(String titulo) {
        // Eliminar el ingreso de Firestore
        db.collection("usuarios")
                .document(user.getUid())
                .collection("Ingresos")
                .document(titulo)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditarIngreso.this, "Ingreso eliminado exitosamente", Toast.LENGTH_SHORT).show();
                    finish(); // Cierra la actividad actual y regresa al fragmento anterior
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditarIngreso.this, "Error al eliminar el ingreso", Toast.LENGTH_SHORT).show();
                });
    }


}
