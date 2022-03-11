package com.example.logreg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.google.firebase.database.ChildEventListener;

public class FilmsListActivity extends AppCompatActivity  {

    DatabaseReference myRef;
    FirebaseDatabase database;
    ArrayAdapter<String> arrayAdapter;

    int index=0;
    ArrayList<String> films;

    Film f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_films_list);



        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("/films");
        films = new ArrayList<>();

        ListView listView = (ListView) findViewById(R.id.listview);
        final TextView textView = (TextView) findViewById(R.id.textview);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, films);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Intent in = new Intent(FilmsListActivity.this ,FilmDetailsActivity.class);
                in.putExtra("fname", selectedItem);
                startActivity(in);
            }
        });


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.child("name").getValue().toString();
                films.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });



//        getData();
        //arrayAdapter.notifyDataSetChanged();


    }

    private void getData() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    films.add(dataSnapshot.child("name").getValue().toString());
                    Log.i("cccccccccccccc : ",dataSnapshot.getKey().toString());
                    Log.i("jjjjjjjjjjjjjj : ",dataSnapshot.getValue().toString());


                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}