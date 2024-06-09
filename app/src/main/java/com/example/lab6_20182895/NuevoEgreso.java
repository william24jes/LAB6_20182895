package com.example.lab6_20182895;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab6_20182895.Bean.Egreso;
import com.example.lab6_20182895.databinding.NuevoEgresoBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NuevoEgreso extends AppCompatActivity {

    private NuevoEgresoBinding nuevoEgresoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nuevoEgresoBinding = NuevoEgresoBinding.inflate(getLayoutInflater());
        setContentView(nuevoEgresoBinding.getRoot());

        nuevoEgresoBinding.botonGuardar.setOnClickListener(v -> guardarEgreso());
    }

    private void guardarEgreso() {
        String titulo = nuevoEgresoBinding.campoTitulo.getText().toString().trim();
        String montoString = nuevoEgresoBinding.campoMonto.getText().toString().trim();
        String descripcion = nuevoEgresoBinding.campoDescripcion.getText().toString().trim();

        // Validar que el título no esté vacío
        if (titulo.isEmpty()) {
            nuevoEgresoBinding.campoTitulo.setError("El título es requerido");
            nuevoEgresoBinding.campoTitulo.requestFocus();
            return;
        }

        // Validar que se ingrese un monto válido
        if (montoString.isEmpty()) {
            nuevoEgresoBinding.campoMonto.setError("El monto es requerido");
            nuevoEgresoBinding.campoMonto.requestFocus();
            return;
        }

        double monto;
        try {
            monto = Double.parseDouble(montoString);
            if (monto <= 0) {
                nuevoEgresoBinding.campoMonto.setError("El monto debe ser mayor que cero");
                nuevoEgresoBinding.campoMonto.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            nuevoEgresoBinding.campoMonto.setError("El monto debe ser un número válido");
            nuevoEgresoBinding.campoMonto.requestFocus();
            return;
        }

        // Validar que la descripción no esté vacía
        if (descripcion.isEmpty()) {
            nuevoEgresoBinding.campoDescripcion.setError("La descripción es requerida");
            nuevoEgresoBinding.campoDescripcion.requestFocus();
            return;
        }

        // Obtener la fecha actual
        String fecha = obtenerFechaActual();

        Egreso egreso = new Egreso(titulo, monto, descripcion, fecha);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Usar el título como nombre del documento
        db.collection("usuarios")
                .document(userId)
                .collection("Egresos")
                .document(titulo)
                .set(egreso)
                .addOnSuccessListener(documentReference -> {
                    // Éxito al guardar el nuevo egreso
                    // Cerrar el activity
                    finish();
                })
                .addOnFailureListener(e -> {
                    // Error al guardar el nuevo egreso
                    // Aquí puedes manejar el error de alguna manera, como mostrar un mensaje de error.
                    // Por ejemplo:
                    // Toast.makeText(NuevoEgresoActivity.this, "Error al guardar el egreso", Toast.LENGTH_SHORT).show();
                });
    }


    private String obtenerFechaActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
