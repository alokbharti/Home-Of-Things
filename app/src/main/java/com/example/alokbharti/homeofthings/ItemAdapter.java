package com.example.alokbharti.homeofthings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alok Bharti on 4/9/2018.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<User> listItems;
    private Context context;
    private int lastSelectedPosition = -1;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference;
    String email,time;

    public ItemAdapter(String email, String time) {
        this.email = email;
        this.time = time;
    }

    public ItemAdapter(List<User> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User listItem = listItems.get(position);
        holder.name.setText(listItem.getName());
        holder.itemDetails.setText(listItem.getDetails());
        holder.address.setText(listItem.getAddress());
        holder.pincode.setText(listItem.pincode);
        holder.radioButton.setChecked( lastSelectedPosition == position);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemDetails;
        public TextView address;
        public TextView name;
        public RadioButton radioButton;
        public TextView pincode;
        String key="";

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            itemDetails = (TextView)itemView.findViewById(R.id.item);
            address = (TextView) itemView.findViewById(R.id.donor_address);
            pincode = (TextView)itemView.findViewById(R.id.donor_pincode);
            radioButton = (RadioButton)itemView.findViewById(R.id.radiobutton);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    lastSelectedPosition = getAdapterPosition();

                    final AlertDialog.Builder alertdialog = new AlertDialog.Builder(v.getRootView().getContext());
                    alertdialog.setTitle("Wanna Claim this!!!");
                    alertdialog.setMessage("Please be sure that you will have to take this item from donor within 24 hours");
                    alertdialog.setNegativeButton("No",null);
                    alertdialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //getting time of current logging in
                            Date d=new Date();
                            SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");
                            String currentDateTimeString = sdf.format(d);
                            mDatabaseReference = mDatabase.getReference().child("User");
                            writeuser(email,time,key);


                            AlertDialog.Builder alert = new AlertDialog.Builder(v.getRootView().getContext());
                            alert.setTitle("Congratulation!!");
                            alert.setMessage("Please be sure that you will have to take this item from donor within 24 hours");
                            alert.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   // context.startActivity(new Intent(context,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                }
                            });
                            alert.show();

                            //when a data is claimed it has to remove from the list
                            listItems.remove(lastSelectedPosition);
                            notifyDataSetChanged();

                        }
                    });
                    alertdialog.show();
                }
            });

        }
    }
    public void writeuser(String email,String time,String key){
        user_database user = new user_database(email, time, key);
        mDatabaseReference.push().setValue(user);
    }
    public void getDonorKey(){

    }
}
