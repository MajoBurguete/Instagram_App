package com.example.instagramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class EditActivity extends AppCompatActivity {

    public static final String KEY_PROFILEDESC = "profileDescription";
    private String TAG = "EditActivity";
    ImageView ivUserPict;
    EditText etDescEdit;
    Button btnSave;
    ImageButton ibClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ivUserPict = findViewById(R.id.ivUserPict);
        etDescEdit = findViewById(R.id.etDescEdit);
        btnSave = findViewById(R.id.btnSaveProf);
        ibClose = findViewById(R.id.ibClose);

        ParseUser user = ParseUser.getCurrentUser();

        etDescEdit.setText(user.getString(KEY_PROFILEDESC));

        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newDesc = etDescEdit.getText().toString();
                saveDescription(newDesc, user);
            }
        });

    }

    private void saveDescription(String description, ParseUser currentUser) {
        currentUser.put(KEY_PROFILEDESC, description);
        currentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.e(TAG, "Error while saving description", e);
                    Toast.makeText(EditActivity.this, "Error while saving!", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(EditActivity.this, "Your description was save successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }
}