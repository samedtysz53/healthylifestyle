package com.tuysuz.healthylifestyle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    Button politicy;
    SignInButton btSignIn;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(getApplicationContext());
        // Firebase'i başlat

        FirebaseAuth.getInstance().signOut();
        // Gerekli değişkenleri tanımla
        btSignIn = findViewById(R.id.logins);
        politicy=findViewById(R.id.politicy);
        politicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,activity_privacy_policy.class);
                startActivity(intent);
            }
        });


        // Google Sign-In seçeneklerini yapılandır
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("412319616496-c9kirrcfmv90qertem0jfaqp87jkomlq.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // GoogleSignInClient'ı başlat
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Giriş butonuna tıklama olayını dinle
        btSignIn.setOnClickListener(view -> signIn());

        // FirebaseAuth'u başlat
        firebaseAuth = FirebaseAuth.getInstance();

        // Kullanıcı zaten oturum açmışsa
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            // Oturum açıksa Anasayfa aktivitesine yönlendir
            //startActivity(new Intent(MainActivity.this, Cardio.class));
            finish();
        }
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account.getIdToken());
                }
            } catch (ApiException e) {
                Log.w("TAG", "Google girişi başarısız", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Toast.makeText(MainActivity.this, "Yetkilendirme başarılı.", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this,Cardio.class);
                        intent.putExtra("Email",user.getEmail());

                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Yetkilendirme başarısız: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
