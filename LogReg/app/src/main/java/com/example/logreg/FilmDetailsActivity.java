package com.example.logreg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FilmDetailsActivity extends AppCompatActivity {
TextView fname;
    DatabaseReference myRef;
    FirebaseDatabase database;
    ImageView imageView;
    Button del,notif;
    private NotificationManager mNotificationManager;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_details);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("/films");

        fname = (TextView) findViewById(R.id.film_name);
        del = (Button) findViewById(R.id.del);
        notif = (Button) findViewById(R.id.notif);


        Intent intent = getIntent();
        String str = intent.getStringExtra("fname");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String name = dataSnapshot.child("name").getValue().toString();
                    if (name.equals(str)){
                        fname.setText(dataSnapshot.child("name").getValue().toString());
                        //fcat.setText(dataSnapshot.child("cat").getValue().toString());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                            String name = dataSnapshot.child("name").getValue().toString();
                            String key = dataSnapshot.getKey().toString();
                            if (name.equals(str)){
                                myRef.child(key).removeValue();
                                Intent in = new Intent(getApplicationContext(), DashboardActivity.class);
                                startActivity(in);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mNotificationManager = (NotificationManager) FilmDetailsActivity.this.getSystemService( FilmDetailsActivity.NOTIFICATION_SERVICE );
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(FilmDetailsActivity.this, default_notification_channel_id ) ;
                mBuilder.setContentTitle( "hii" ) ;
                mBuilder.setContentText( "hoooooooooo") ;
                mBuilder.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark ) ;
                mBuilder.setAutoCancel( true ) ;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES. O ) {
                    int importance = NotificationManager.IMPORTANCE_HIGH ;
                    NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
                    mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
                    assert mNotificationManager != null;
                    mNotificationManager.createNotificationChannel(notificationChannel) ;
                }
                assert mNotificationManager != null;
                mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;

            }
        });


    }



}