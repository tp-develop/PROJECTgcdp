package com.example.projectgcdp;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectgcdp.Model.Post;
import com.example.projectgcdp.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class NewPostActivity extends BaseActivity  {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";
    private static final int RESULT_LOAD_IMAGE = 1 ;
    User getinfo;
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]
    String postUri;
    UploadTask uploadTask;
    private EditText mTitleField;
    private EditText mBodyField;
    private FloatingActionButton mSubmitButton;
    private TextView name;
    public ImageView imageView, imButton, imnewpost;
    Uri fullPhotoUri;
    StorageReference mStorageRef;
    public String key;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        mDatabase = FirebaseDatabase.getInstance().getReference("");
        // [END initialize_database_ref]
        mStorageRef = FirebaseStorage.getInstance().getReference("picture-post");
        mTitleField = findViewById(R.id.fieldTitle);
        mBodyField = findViewById(R.id.fieldBody);
        mSubmitButton = findViewById(R.id.fabSubmitPost);

        imageView =findViewById(R.id.improFilenewpost);


        name = findViewById(R.id.name);
       imnewpost = findViewById(R.id.imageViewPost);
        imButton = findViewById(R.id.imButton);

        Bundle bundle = getIntent().getExtras();

        String NameUser = bundle.getString("namecurrent");
        name.setText(NameUser);

//        imageView.setImageResource(getinfo.getProfile().getId());

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }
    private void submitPost() {
        final String title = mTitleField.getText().toString();
        final String body = mBodyField.getText().toString();

        if (imnewpost != null) {
            ImageView beforeImaage = imnewpost;
        }
        // Title is required
        if (TextUtils.isEmpty(title)) {
            mTitleField.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(body)) {
            mBodyField.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]

        final String userId = getUid();
        mDatabase.child("Accout").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(NewPostActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(userId, user.profileName, title, body);
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });


        // [END single_value_read]


        //// Firebase Storage

//        mStorageRef.

    }

    private void setEditingEnabled(boolean enabled) {
        mTitleField.setEnabled(enabled);
        mBodyField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.show();
        } else {
            mSubmitButton.hide();
        }
    }

    // [START write_fan_out]
    private void writeNewPost(String userId, String username, String title, String body) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
         key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId, username, title, body);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);

        if (fullPhotoUri != null){
            FirebaseStorage.getInstance().getReference("picture-Post/" + key +
                    ".jpg")
                    .putFile(fullPhotoUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            postUri = taskSnapshot.getUploadSessionUri().toString();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });}

        fullPhotoUri = null;
    }


    public void setImButton(View view){
        switch (view.getId()){
            case R.id.imButton :
                    Intent getImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getImageIntent.setType("image/*");
                startActivityForResult(getImageIntent , RESULT_LOAD_IMAGE );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

             fullPhotoUri = data.getData();
            imnewpost.setVisibility(View.VISIBLE);
            imnewpost.setImageURI(fullPhotoUri);

        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    // [END write_fan_out]
}
