package com.example.lab6_20182895;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab6_20182895.databinding.Login2Binding;
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

        login2Binding = Login2Binding.inflate(getLayoutInflater());
        setContentView(login2Binding.getRoot());

        // Configura los clics de los botones
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

        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(Arrays.asList(provider))
                .build();
        signInLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Log.d("msg-test", "Firebase uid: " + user.getUid());
                } else {
                    Log.d("msg-test", "Canceló el Log-in");
                }
            }
    );
}
