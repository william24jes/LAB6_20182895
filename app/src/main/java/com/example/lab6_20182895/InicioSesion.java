package com.example.lab6_20182895;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab6_20182895.databinding.Login2Binding;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Arrays;

public class InicioSesion extends AppCompatActivity {

    private Login2Binding login2Binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth.getInstance().setLanguageCode("es-419");

        login2Binding = Login2Binding.inflate(getLayoutInflater());
        setContentView(login2Binding.getRoot());

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

                    goToMainActivity(); // Aquí llamamos al método para ir a la actividad principal
                } else {
                    Log.d("msg-test", "Canceló el Log-in");
                }
            }
    );


    public void goToMainActivity() {
        Intent intent = new Intent(InicioSesion.this, Inicio.class );
        startActivity(intent);
        finish();
    }
}
