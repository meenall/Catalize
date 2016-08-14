package com.catalizeapp.catalize_ss16_v5.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.catalize.backend.model.userApi.model.User;
import com.catalizeapp.catalize_ss16_v5.Nav;
import com.catalizeapp.catalize_ss16_v5.R;
import com.catalizeapp.catalize_ss16_v5.utils.DbManager;
import com.catalizeapp.catalize_ss16_v5.utils.FileUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = RegisterActivity.class.getSimpleName();


    @BindView(R.id.user_image_button)
    ImageButton userImageButton;
    @BindView(R.id.user_username_text)
    EditText userUsernameText;
    @BindView(R.id.user_displayname_text)
    EditText userDisplaynameText;
    @BindView(R.id.user_password_text)
    EditText userPasswordText;
    @BindView(R.id.user_password2_text)
    EditText userPassword2Text;
    @BindView(R.id.user_cancell_button)
    Button userCancellButton;
    @BindView(R.id.user_register_button)
    Button userRegisterButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        imageUri = FileUtil.getUriToResource(getApplicationContext(), R.drawable.profile);
        userUsernameText.setText("marcus.johnson226@gmail.com");
        userDisplaynameText.setText("marcus");
        userPassword2Text.setText("password");
        userPasswordText.setText("password");

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(userDisplaynameText.getText().toString())
                            .setPhotoUri(imageUri)
                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User profile updated.");
                                        User login = new User();
                                        login.setDisplayName(user.getDisplayName());
                                        login.setEmail(user.getEmail());
                                        login.setUid(user.getUid());
                                        DbManager.getInstance().addUser(login);
                                        Intent intent = new Intent(RegisterActivity.this, Nav.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                    }
                                }
                            });
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 1);
    }


    private void registerClick() {
        if (validUser()) {
            mAuth.createUserWithEmailAndPassword(userUsernameText.getText().toString(), userPasswordText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "error: " + e.getLocalizedMessage());

                }
            });
        }

    }

    private boolean validUser() {
        if (userPasswordText.getText().toString().length() < 6) {
            userPasswordText.setError("Password too short");
            return false;
        }
        if (!userPasswordText.getText().toString().equals(userPassword2Text.getText().toString())) {
            userPasswordText.setError("Passwords don't match");

            return false;
        }
        if (userDisplaynameText.getText().toString().length() == 0) {
            userDisplaynameText.setError("Cannot be blank");
            return false;
        }
        if (userUsernameText.getText().toString().length() == 0) {
            userUsernameText.setError("Cannot be blank");
            return false;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 1) {

            imageUri = data.getData();
            userImageButton.setImageURI(imageUri);
        }
    }


    @OnClick({R.id.user_image_button, R.id.user_cancell_button, R.id.user_register_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_cancell_button:
                finish();
                break;
            case R.id.user_register_button:
                registerClick();
                break;
            case R.id.user_image_button:
                pickImage();
                break;
        }
    }
}
