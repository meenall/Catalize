package com.catalizeapp.catalize_ss16_v5;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private static int RESULT_LOAD_IMAGE = 1;
    String firstName;
    String lastName;
    String personEmail;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        final Context context = this;

        final SharedPreferences prefs = this.getSharedPreferences("com.catalizeapp.catalize_ss16_v5", Context.MODE_PRIVATE);
        path = prefs.getString("pic", "");
        File imgFile = new File(path);
        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            //ImageView myImage = (ImageView) findViewById(R.id.imageView);
            //myImage.setImageBitmap(myBitmap);

        }
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
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        firstName = prefs.getString("first_name","");
        lastName = prefs.getString("last_name","");
        personEmail  = prefs.getString("email", "");
        path  = prefs.getString("pic", "");

        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = sampleSize;
        //BitmapFactory.decodeFile(path, options);

        final EditText firstNameEdited = (EditText) findViewById(R.id.first_name_edit);
        final EditText lastNameEdited = (EditText) findViewById(R.id.last_name_edit);
        final EditText emailEdited = (EditText) findViewById(R.id.email_edit);
        final ImageView imageView = (ImageView) findViewById(R.id.imageViewEdit);

        if (path.compareTo("") != 0) {
            Glide.with(context).load(path).into(imageView);
        }
        firstNameEdited.setHint(firstName);
        lastNameEdited.setHint(lastName);
        emailEdited.setHint(personEmail);

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View view) {

                SharedPreferences.Editor editor = prefs.edit();
                if (firstNameEdited.getText().toString().compareTo("") != 0) {
                    //Toast.makeText(context, "Please fill in both fields.",
                    //      Toast.LENGTH_SHORT).show();
                    editor.putString("first_name", firstNameEdited.getText().toString()).apply();
                }
                if (lastNameEdited.getText().toString().compareTo("") != 0) {
                    editor.putString("last_name", lastNameEdited.getText().toString()).apply();
                }
                if (emailEdited.getText().toString().compareTo("") != 0) {
                    editor.putString("email", emailEdited.getText().toString()).apply();
                }
                editor.apply();
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
        super.onActivityResult(requestCode, resultCode, data);
        Context context = this;
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            ImageView imageView = (ImageView) findViewById(R.id.imageViewEdit);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            SharedPreferences prefs = this.getSharedPreferences("com.catalizeapp.catalize_ss16_v5", Context.MODE_PRIVATE);
            SharedPreferences.Editor e = prefs.edit();
            e.putString("pic", picturePath);
            e.commit();
            Toast.makeText(context, prefs.getString("pic",""),
                    Toast.LENGTH_SHORT).show();
            cursor.close();
            //edit.putString(picturePath,"/sdcard/imh.jpeg");
            Glide.with(context).load(picturePath).into(imageView);
        }
    }
}

