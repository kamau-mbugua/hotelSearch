package com.example.hotelsearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.hotelsearch.databinding.ActivityForgortBinding;
import com.example.hotelsearch.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding registerBinding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = registerBinding.getRoot();
        setContentView(view);

        registerBinding.cardViewProgress.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
        registerBinding.textViewBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        registerBinding.outlinedButtonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = registerBinding.editTextEmail.getText().toString();
                String password = registerBinding.editTextPassword.getText().toString();
                String name = registerBinding.editTextName.getText().toString();
                String phone = registerBinding.editTextPhone.getText().toString();

                if (email.isEmpty()){
                    registerBinding.filledTextField.setError("Email Required");
                }
                else if (password.isEmpty()){
                    registerBinding.filledTextFieldpassword.setError("Password Required");
                }
                else if (name.isEmpty()){
                    registerBinding.filledTextFieldName.setError("Name Required");
                }
                else {
                    registerBinding.cardViewProgress.setVisibility(View.VISIBLE);
                    //registerUser(email, password,name,navController);
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            registerBinding.cardViewProgress.setVisibility(View.GONE);
                            if (task.isSuccessful()){

                                User user = new User(name, email, phone);

                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                        Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), AddHotelActivity.class));
                                    }
                                });

                                ;

                                // startActivity(new Intent(getActivity(), MainActivity.class));
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                                registerBinding.cardViewProgress.setVisibility(View.GONE);
                            }
                        }
                    });
                }

            }
        });
    }
}