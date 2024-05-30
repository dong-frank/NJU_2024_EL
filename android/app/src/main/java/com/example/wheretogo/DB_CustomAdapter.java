package com.example.wheretogo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DB_CustomAdapter extends RecyclerView.Adapter<DB_CustomAdapter.MyViewHolder> {
    private Context context;// LOCAL context (QUESTION)
    private ArrayList site_id, site_name, site_city, site_address;
    private ArrayList site_PID , site_intro;
    private Activity activity;
    private DB_MyDatabaseHelper myDB;
    private int TourStatus_Curren_At;
    // Constructor;
    DB_CustomAdapter(Activity activity, Context context,
                     ArrayList site_id, ArrayList site_name, ArrayList site_city, ArrayList site_address,ArrayList site_PID, ArrayList site_intro){
        this.activity = activity;
        this.context = context;

        this.site_id = site_id;

        this.site_name = site_name;
        this.site_city = site_city;
        this.site_address = site_address;
        this.site_PID = site_PID;
        this.site_intro = site_intro;

        myDB = new DB_MyDatabaseHelper(context);
        TourStatus_Curren_At = myDB.getCurrentAt();
    }

    // This method is called when the adapter needs to create a new ViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent, false);
        return new MyViewHolder(view);
    }

    //  This method is called repeatedly as the user scrolls, to update the ViewHolders with the correct data.
    @NonNull
    @Override
    public void onBindViewHolder(@NonNull DB_CustomAdapter.MyViewHolder holder, final int position) {

        if(position < TourStatus_Curren_At){
            holder.site_name_txt.setText(String.valueOf(site_name.get(position)));
            holder.site_city_txt.setText(String.valueOf(site_city.get(position)));
            holder.site_address_txt.setText(String.valueOf(site_address.get(position)));
            holder.site_PID_txt.setText(String.valueOf(site_PID.get(position)));
            holder.site_intro_txt.setText(String.valueOf(site_intro.get(position)));
        }
        else{
            holder.site_name_txt.setText("???");
            holder.site_city_txt.setText("???");
            holder.site_address_txt.setText("???");
            holder.site_PID_txt.setText("???");
            holder.site_intro_txt.setText("???");
        }

        // this OnClickListener is used for update (so that you can touch the line to modify it)
        holder.mainLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DB_UpdateActivity.class);
                intent.putExtra("id",String.valueOf(site_id.get(position)));
                intent.putExtra("name",String.valueOf(site_name.get(position)));
                intent.putExtra("city",String.valueOf(site_city.get(position)));
                intent.putExtra("address",String.valueOf(site_address.get(position)));
                intent.putExtra("PID",String.valueOf(site_PID.get(position)));
                intent.putExtra("intro",String.valueOf(site_intro.get(position)));
                //TODO:
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return site_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView site_city_txt, site_name_txt, site_address_txt;
        TextView  site_intro_txt,site_PID_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            site_name_txt = itemView.findViewById(R.id.site_name_txt);
            site_city_txt = itemView.findViewById(R.id.site_city_txt);
            site_address_txt = itemView.findViewById(R.id.site_address_txt);
            site_intro_txt = itemView.findViewById(R.id.site_intro_txt);
            site_PID_txt= itemView.findViewById(R.id.site_PID_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}

