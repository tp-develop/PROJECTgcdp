package com.example.projectgcdp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.projectgcdp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class create extends AppCompatActivity {


    private static final int CHOOSE_IMAGE = 1;
    EditText etName, etAge;
    Button btSave;
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    ImageView imProfile;
    Uri uriProfileImage;
    String profileImageUri;
    Bitmap bitmap;
    RadioGroup radioGroup;
    public String gender ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        etName = findViewById(R.id.profilename);
        etAge = findViewById(R.id.age);
        btSave = findViewById(R.id.btSave);
        progressBar = findViewById(R.id.progressBar);
        imProfile = findViewById(R.id.improFile);
        radioGroup = findViewById(R.id.radioGroup);


        // Firebase Database referece
        databaseReference = FirebaseDatabase.getInstance().getReference("Accout");
        firebaseAuth = FirebaseAuth.getInstance();
        /////////////////////////////////////////

        imProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               saveUserInformation();
            }
        });

        switch (radioGroup.getCheckedRadioButtonId()) {

            case R.id.radioM:
                gender = "male";
                break;
            case R.id.radioF:
                gender = "female";
                break;
        }

//        Toast.makeText(getApplicationContext(), gender, Toast.LENGTH_SHORT).show();

    }


    private void saveUserInformation() {
        final String profileName = etName.getText().toString();
        final int Age = Integer.parseInt(etAge.getText().toString());

        Bundle bundle = getIntent().getExtras();
        final String email = bundle.getString("email");
        String password = bundle.getString("password");

        if (profileName.isEmpty()){
            etName.setError("Name required");
            etName.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(create.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User information = new User(
                                    profileName,
                                    email,
                                    Age,
                                    gender
                            );

//                            int selectedId = radioGroup.getCheckedRadioButtonId();
//                            RadioButton radioSexButton = findViewById(selectedId);


                            FirebaseDatabase.getInstance().getReference("Accout")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(create.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(new Intent(create.this, Home.class));
                                    startActivity(i);
                                    finish();
                                }
                            });
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Intent i = new Intent(new Intent(create.this, Register.class));
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        if (uriProfileImage != null) {

                            FirebaseStorage.getInstance().getReference("profilePic/" + FirebaseAuth.getInstance().getCurrentUser().getUid() +
                                    ".jpg")
                                    .putFile(uriProfileImage)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            profileImageUri = taskSnapshot.getUploadSessionUri().toString();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }


                    }


                });


    }


    private void showImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select profile Image"), CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE){
            uriProfileImage = data.getData();
            try {
                 bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                imProfile.setImageBitmap(bitmap);

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


}






