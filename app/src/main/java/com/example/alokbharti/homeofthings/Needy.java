package com.example.alokbharti.homeofthings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Needy extends AppCompatActivity {

    private EditText pincode;
    private Button search;
    private String pin="";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<User> listItems;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseDatabase mDatabase;
    DatabaseReference ClothDatabase ;
    DatabaseReference FoodDatabase ;
    DatabaseReference BookDatabase ;
    DatabaseReference OtherDatabase ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_needy);
        setTitle("Claim");

        //for back button
        if(getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerView =(RecyclerView)findViewById(R.id.second_RecylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        listItems = new ArrayList<>();


        mDatabase = FirebaseDatabase.getInstance();
        ClothDatabase = mDatabase.getReference().child("Donor").child("Clothes");
        FoodDatabase = mDatabase.getReference().child("Donor").child("Food");
        BookDatabase = mDatabase.getReference().child("Donor").child("Books");
        OtherDatabase = mDatabase.getReference().child("Donor").child("Others");

        GetItems();


        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.second_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                GetItems();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },4000);
            }
        });

        //for searching with a particular pin code
        search = (Button)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pincode = (EditText)findViewById(R.id.pin);
                pin = pincode.getText().toString();
                List<User> temp = new ArrayList();
                for(User d:listItems){
                    //or use .equal(text) with you want equal match
                    if(d.getPincode().contains(pin)){
                        temp.add(d);
                    }
                }
                mAdapter = new ItemAdapter(temp,getApplicationContext());
//                Log.e("adas",listItems.toString());
                recyclerView.setAdapter(mAdapter);

            }
        });

    }

    public void GetItems(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data....");
        progressDialog.show();

        ClothDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("error","indn");
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    listItems.add(user);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FoodDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    listItems.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        BookDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    listItems.add(user);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        OtherDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    listItems.add(user);
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAdapter = new ItemAdapter(listItems,getApplicationContext());
        Log.e("adas",listItems.toString());
        recyclerView.setAdapter(mAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_out, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
           // finish();
            startActivity(new Intent(Needy.this,MainActivity.class));
        }
        if(item.getItemId() == R.id.request){
            composeEmail("2016017@iiitdmj.ac.in","Regarding delivery of item claimed",
                    "I'm not able to take the items due to some circumstances. So,please reply this stating about the details of transportation of items.\n Thank you,");

        }
        if(item.getItemId()== R.id.sign_out){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Needy.this,MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void composeEmail(String addresses, String subject,String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",addresses, null));
        //intent.setData(Uri.parse("mailto:"));
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT,body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    /**
    @Override
    public boolean onQueryTextChange(String newText) {

        newText = pincode.getText().toString();
        final List<User> filteredModelList = filter(listItems, newText);
        if (filteredModelList.size() > 0) {
            mAdapter.setFilter(filteredModelList,newText);
            return true;
        } else {
            Toast.makeText(Needy.this, "Not Found", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    **/


}
