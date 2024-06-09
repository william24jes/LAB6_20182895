package com.example.lab6_20182895;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab6_20182895.Bean.Usuario;
import com.example.lab6_20182895.databinding.Login2Binding;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InicioSesion extends AppCompatActivity {

    private Login2Binding login2Binding;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth.getInstance().setLanguageCode("es-419");

        login2Binding = Login2Binding.inflate(getLayoutInflater());
        setContentView(login2Binding.getRoot());

        // Inicializar Firebase Firestore
        db = FirebaseFirestore.getInstance();

        login2Binding.buttonEmailSignIn.setOnClickListener(v -> launchSignInFlow(AuthUI.IdpConfig.EmailBuilder.class));
        login2Binding.buttonGoogleSignIn.setOnClickListener(v -> launchSignInFlow(AuthUI.IdpConfig.GoogleBuilder.class));
    }

    private void launchSignInFlow(Class<? extends AuthUI.IdpConfig.Builder> providerClass) {
        AuthUI.IdpConfig provider;
        if (providerClass == AuthUI.IdpConfig.EmailBuilder.class) {
            provider = new AuthUI.IdpConfig.EmailBuilder().build();
        } else {
            provider = new AuthUI.IdpConfig.GoogleBuilder().build();
        }

        AuthMethodPickerLayout authMethodPickerLayout = new AuthMethodPickerLayout.Builder(R.layout.inicio)
                .setGoogleButtonId(R.id.buttonGoogleSignIn)
                .setEmailButtonId(R.id.buttonEmailSignIn)
                .build();

        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(Arrays.asList(provider))
                .setAuthMethodPickerLayout(authMethodPickerLayout)
                .build();
        signInLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Log.d("msg-test", "Firebase uid: " + user.getUid());

                    // Guardar los datos del usuario en Firestore
                    guardarDatosUsuario(user);

                    goToMainActivity(); // Aquí llamamos al método para ir a la actividad principal
                } else {
                    Log.d("msg-test", "Canceló el Log-in");
                }
            }
    );

    private void guardarDatosUsuario(FirebaseUser user) {
        // Crear un objeto Usuario con los datos del usuario
        Usuario usuario = new Usuario(user.getDisplayName(), user.getEmail());

        // Obtener una referencia al documento del usuario en la colección "usuarios"
        DocumentReference userRef = db.collection("usuarios").document(user.getUid());

        // Comprobar si el documento del usuario ya existe
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                } else {
                    // El documento del usuario no existe, crearlo con el UID del usuario y establecer los datos del usuario
                    userRef.set(usuario)
                            .addOnSuccessListener(aVoid -> {
                                // Los datos del usuario se guardaron exitosamente
                            })
                            .addOnFailureListener(e -> {
                                // Error al guardar los datos del usuario
                            });
                }
            } else {
                // Error al intentar obtener el documento del usuario
            }
        });
    }


    public void goToMainActivity() {
        Intent intent = new Intent(this, Inicio.class);
        startActivity(intent);
        finish(); // Finalizar la actividad actual para que no se pueda volver atrás
    }

}
