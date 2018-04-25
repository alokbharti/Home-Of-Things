package com.example.alokbharti.homeofthings;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Alok Bharti on 4/8/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;

    public Adapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);
        holder.heading.setText(listItem.getHeading());
        holder.details.setText(listItem.getDetails());

        Picasso.with(context).load(listItem.getImageUrl()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView heading;
        public TextView details;
        public ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            heading = (TextView) itemView.findViewById(R.id.topic);
            details = (TextView) itemView.findViewById(R.id.details);
            image = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
