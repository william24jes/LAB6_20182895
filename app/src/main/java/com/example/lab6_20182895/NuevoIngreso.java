package com.example.lab6_20182895;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab6_20182895.Bean.Ingreso;
import com.example.lab6_20182895.databinding.NuevoIngresoBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NuevoIngreso extends AppCompatActivity {

    private NuevoIngresoBinding nuevoIngresoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nuevoIngresoBinding = NuevoIngresoBinding.inflate(getLayoutInflater());
        setContentView(nuevoIngresoBinding.getRoot());

        nuevoIngresoBinding.botonGuardar.setOnClickListener(v -> guardarIngreso());
    }

    private void guardarIngreso() {
        String titulo = nuevoIngresoBinding.campoTitulo.getText().toString().trim();
        String montoString = nuevoIngresoBinding.campoMonto.getText().toString().trim();
        String descripcion = nuevoIngresoBinding.campoDescripcion.getText().toString().trim();

        // Validar que el título no esté vacío
        if (titulo.isEmpty()) {
            nuevoIngresoBinding.campoTitulo.setError("El título es requerido");
            nuevoIngresoBinding.campoTitulo.requestFocus();
            return;
        }

        // Validar que se ingrese un monto válido
        if (montoString.isEmpty()) {
            nuevoIngresoBinding.campoMonto.setError("El monto es requerido");
            nuevoIngresoBinding.campoMonto.requestFocus();
            return;
        }

        double monto;
        try {
            monto = Double.parseDouble(montoString);
            if (monto <= 0) {
                nuevoIngresoBinding.campoMonto.setError("El monto debe ser mayor que cero");
                nuevoIngresoBinding.campoMonto.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            nuevoIngresoBinding.campoMonto.setError("El monto debe ser un número válido");
            nuevoIngresoBinding.campoMonto.requestFocus();
            return;
        }

        // Validar que la descripción no esté vacía
        if (descripcion.isEmpty()) {
            nuevoIngresoBinding.campoDescripcion.setError("La descripción es requerida");
            nuevoIngresoBinding.campoDescripcion.requestFocus();
            return;
        }

        // Obtener la fecha actual
        String fecha = obtenerFechaActual();

        Ingreso ingreso = new Ingreso(titulo, monto, descripcion, fecha);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Usar el título como nombre del documento
        db.collection("usuarios")
                .document(userId)
                .collection("Ingresos")
                .document(titulo)
                .set(ingreso)
                .addOnSuccessListener(documentReference -> {
                    // Éxito al guardar el nuevo ingreso
                    // Cerrar el activity
                    finish();
                })
                .addOnFailureListener(e -> {
                    // Error al guardar el nuevo ingreso
                    // Aquí puedes manejar el error de alguna manera, como mostrar un mensaje de error.
                    // Por ejemplo:
                    // Toast.makeText(NuevoIngresoActivity.this, "Error al guardar el ingreso", Toast.LENGTH_SHORT).show();
                });
    }


    private String obtenerFechaActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
