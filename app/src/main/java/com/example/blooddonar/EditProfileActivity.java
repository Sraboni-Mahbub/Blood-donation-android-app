package com.example.blooddonar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.blooddonar.databinding.ActivityEditProfileBinding;
import com.example.blooddonar.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText  name, idNumber, phoneNumber;
    private Button backButton, saveButton;

    ActivityEditProfileBinding binding;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //name = findViewById(R.id.name);
        //idNumber = findViewById(R.id.idNumber);
        //phoneNumber = findViewById(R.id.phoneNumber);
        backButton = findViewById(R.id.backButton);
        //saveButton = findViewById(R.id.editButton);

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.name.getText().toString();
                String idNumber = binding.idNumber.getText().toString();
                String phoneNumber = binding.phoneNumber.getText().toString();

                updateData(name, idNumber, phoneNumber);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity3.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void updateData(String name, String idNumber, String phoneNumber) {

        HashMap User = new HashMap();
        if(name.length()>0){
            User.put("name", name);
        }
        if(idNumber.length()>0){
            User.put("idnumber",idNumber);
        }
        if(phoneNumber.length()>0){
            User.put("phonenumber",phoneNumber);
        }



        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if (task.isSuccessful()){
                    binding.name.setText("");
                    binding.idNumber.setText("");
                    binding.phoneNumber.setText("");

                    Toast.makeText(EditProfileActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(EditProfileActivity.this, "Failed to update", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}