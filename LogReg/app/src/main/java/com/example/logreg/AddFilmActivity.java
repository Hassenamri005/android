package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFilmActivity extends AppCompatActivity {
Button addfilm;
EditText name;
Film f;
FirebaseDatabase database;
DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film);

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("/");

        name = (EditText) findViewById(R.id.name);
        addfilm = (Button) findViewById(R.id.addfilm);





        addfilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addValue();
            }
        });
    }

    private void addValue() {
        f = new Film();
        f.setName(name.getText().toString());

        myRef.child("films").push().setValue(f);
        Toast.makeText(AddFilmActivity.this, "done", Toast.LENGTH_LONG).show();
        name.setText("");
    }
}