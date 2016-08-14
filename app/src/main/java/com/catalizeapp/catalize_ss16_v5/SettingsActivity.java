package com.catalizeapp.catalize_ss16_v5;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;


public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private static int RESULT_LOAD_IMAGE = 1;
    String firstName;
    String lastName;
    String personEmail;
    private Uri imageUri;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        final Context context = this;


        Button buttonLoadImage = (Button) findViewById(R.id.picture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (ContextCompat.checkSelfPermission(SettingsActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(SettingsActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    } else {

                        ActivityCompat.requestPermissions(SettingsActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                0);
                    }
                }
                pickImage();
            }
        });


        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = sampleSize;
        //BitmapFactory.decodeFile(path, options);

        final EditText firstNameEdited = (EditText) findViewById(R.id.first_name_edit);
        final EditText lastNameEdited = (EditText) findViewById(R.id.last_name_edit);
        final EditText emailEdited = (EditText) findViewById(R.id.email_edit);
         imageView = (ImageView) findViewById(R.id.imageViewEdit);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        imageUri = user.getPhotoUrl();
        firstNameEdited.setText(user.getDisplayName());
        emailEdited.setText(user.getEmail());
        Picasso.with(getApplicationContext())
                .load(user.getPhotoUrl())
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(imageView);
        firstNameEdited.setHint(firstName);
        lastNameEdited.setHint(lastName);
        emailEdited.setHint(personEmail);

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View view) {

                if (firstNameEdited.getText().toString().length() > 0
                        && lastNameEdited.getText().toString().length() > 0
                        && emailEdited.getText().toString().length() > 0) {
                    //Toast.makeText(context, "Please fill in both fields.",
                    //      Toast.LENGTH_SHORT).show();
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        // User is signed in
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(firstNameEdited.getText().toString())
                                .setPhotoUri(imageUri)
                                .build();

                        user.updateProfile(profileUpdates);
                    }
                }

                Intent intent = new Intent(SettingsActivity.this, Nav.class);
                startActivity(intent);
            }
        };
        Button btn1 = (Button)findViewById(R.id.save_prefs);
        btn1.setOnClickListener(listener);

    }

    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && requestCode>0) {
            if(data != null){
                imageUri = data.getData();
                imageView.setImageURI(imageUri);
            }

        }
    }

    @Override
    public void onBackPressed() {
        Intent intentReportBug = new Intent(SettingsActivity.this, Nav.class); //
        startActivity(intentReportBug);
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
}

