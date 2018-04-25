package com.example.alokbharti.homeofthings;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Donate extends AppCompatActivity {

    private int itemSelected;
    ArrayAdapter<String> adapter;

    String items[] = {"Clothes","Food","Books","Others"};

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        setTitle("Donate");

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Wonderful..");
        dialog.setMessage("You are really doing a great job. \nWe really appreciate your effort.");
        dialog.setPositiveButton("Ok", null);
        dialog.show();

        //for back button
        if(getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        final EditText name = (EditText)findViewById(R.id.name);
        final EditText address = (EditText)findViewById(R.id.address);
        final EditText pincode = (EditText)findViewById(R.id.pincode);
        final EditText details = (EditText)findViewById(R.id.description);


        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                itemSelected=position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Button button = (Button)findViewById(R.id.donate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getText().toString().matches("")&&address.getText().toString().matches("")&&
                        pincode.getText().toString().matches("")&&details.getText().toString().matches("")) {
                    Toast.makeText(Donate.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else{

                    if (itemSelected == 0) {
                        mDatabaseReference = mDatabase.getReference().child("Donor").child("Clothes");
                        writeuser(name.getText().toString(),address.getText().toString(),pincode.getText().toString(),details.getText().toString());
                        Intent i =new Intent(Donate.this,MainActivity.class);
                        startActivity(i);
                    }
                    else if (itemSelected==1) {
                        mDatabaseReference = mDatabase.getReference().child("Donor").child("Food");
                        writeuser(name.getText().toString(),address.getText().toString(),pincode.getText().toString(),details.getText().toString());
                        Intent i =new Intent(Donate.this,MainActivity.class);
                        startActivity(i);
                    }
                    else if (itemSelected==2) {
                        mDatabaseReference = mDatabase.getReference().child("Donor").child("Books");
                        writeuser(name.getText().toString(),address.getText().toString(),pincode.getText().toString(),details.getText().toString());
                        Intent i =new Intent(Donate.this,MainActivity.class);
                        startActivity(i);
                    }
                    else if (itemSelected==3) {
                        mDatabaseReference = mDatabase.getReference().child("Donor").child("Others");
                        writeuser(name.getText().toString(),address.getText().toString(),pincode.getText().toString(),details.getText().toString());
                        Intent i =new Intent(Donate.this,MainActivity.class);
                        startActivity(i);
                    }
                }
            }
        });


    }
    //return to previous activty when back button is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void writeuser(String name,String address,String pincode,String details){
        User user = new User(name, address, pincode, details,false);
        mDatabaseReference.push().setValue(user);
        Toast.makeText(Donate.this,"Your details is submitted, Thank you",Toast.LENGTH_SHORT).show();

    }

}
