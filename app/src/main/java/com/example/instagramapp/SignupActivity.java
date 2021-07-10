package com.example.instagramapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.instagramapp.databinding.ActivitySignupBinding;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class SignupActivity extends AppCompatActivity {

    private String TAG = "SignupActivity";
    public final static int PICK_PHOTO_CODE = 27;
    private ParseFile photoFile;
    ImageButton ibProfile;
    public static final String KEY_PROFILE = "profilePic";
    public static final String KEY_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignupBinding binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = binding.ToolbarSign;
        setSupportActionBar(toolbar);
        ibProfile = binding.ibProfile;

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values from the text view
                String username = binding.etUserSign.getText().toString();
                String password = binding.etPassSign.getText().toString();
                String email = binding.etEmail.getText().toString();
                String name = binding.etName.getText().toString();

                // Check if everything is complete
                if (username.isEmpty() || password.isEmpty() || email.isEmpty() || name.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "There's data missing, please fill everything!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create the ParseUser
                ParseUser user = new ParseUser();

                // Set properties
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);
                user.put(KEY_NAME, name);
                //Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Toast.makeText(SignupActivity.this, "Failed to signup, try again!", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "Failed to signup", e);
                            return;
                        }

                        // save profile pic first
                        ParseUser user = ParseUser.getCurrentUser();
                        user.put(KEY_PROFILE, photoFile);
                        user.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                // go back to previous activity
                                Intent result = new Intent();
                                result.putExtra("username", username);
                                result.putExtra("password", password);
                                setResult(RESULT_OK, result);
                                finish();
                            }
                        });
                    }
                });
            }
        });

        ibProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPickPhoto(v);
            }
        });
    }

    // Trigger gallery selection for a photo
    public void onPickPhoto(View view) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }

    public Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            // check version of Android on device
            if(Build.VERSION.SDK_INT > 27){
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((data != null) && requestCode == PICK_PHOTO_CODE) {
            Uri photoUri = data.getData();
            String photoPath = photoUri.getPath();

            String realPath = getRealPathFromUri(this,photoUri);
            File localFile = new File(realPath);
            boolean canRead = localFile.canRead();
            String uriToString = photoUri.toString();
            File urllocalFile = new File(uriToString);
            canRead = urllocalFile.canRead();


            // Load the image located at photoUri into selectedImage
            Bitmap selectedImage = loadFromUri(photoUri);


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            photoFile = new ParseFile(byteArray);

            // Load the selected image into a preview
            ibProfile.setImageBitmap(selectedImage);
        }
    }

    //https://stackoverflow.com/questions/20028319/how-to-convert-content-media-external-images-media-y-to-file-storage-sdc
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.btnReturnL){
            finish();
        }
        return true;
    }
}