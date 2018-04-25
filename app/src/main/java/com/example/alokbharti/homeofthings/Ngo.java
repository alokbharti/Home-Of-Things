package com.example.alokbharti.homeofthings;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Ngo extends AppCompatActivity {


    private TextView cloth;
    private TextView book;
    private TextView food;
    private TextView other;
    private FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo);
        setTitle("NGO section");

        //for back button
        if(getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ClothDatabase = mDatabase.getReference().child("Donor").child("Clothes");
        DatabaseReference FoodDatabase = mDatabase.getReference().child("Donor").child("Food");
        DatabaseReference BookDatabase = mDatabase.getReference().child("Donor").child("Books");
        DatabaseReference OtherDatabase = mDatabase.getReference().child("Donor").child("Others");

        cloth = (TextView) findViewById(R.id.cloth);
        book = (TextView) findViewById(R.id.book);
        food = (TextView) findViewById(R.id.food);
        other = (TextView) findViewById(R.id.other);
        ClothDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = (int) dataSnapshot.getChildrenCount();
                cloth.setText("Total cloth items = " + count);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                }
        });
        FoodDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = (int) dataSnapshot.getChildrenCount();
                food.setText("Total food items = " + count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        BookDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = (int) dataSnapshot.getChildrenCount();
                book.setText("Total books = " + count);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        OtherDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = (int) dataSnapshot.getChildrenCount();
                other.setText("Total other items = " + count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail("2016017@iiitdmj.ac.in","Regarding Distribution of your stock",
                        "We want to distribute your stock. So,please reply this mail with details of your database.\n Thank you,");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void composeEmail(String addresses, String subject,String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","2016017@iiitdmj.ac.in", null));
        //intent.setData(Uri.parse("mailto:"));
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT,body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
