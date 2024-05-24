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

//QUESTION: I am feeling extremely confused about the MyViewHolder...
public class DB_CustomAdapter extends RecyclerView.Adapter<DB_CustomAdapter.MyViewHolder> {
    private Context context;// LOCAL context (QUESTION)
    private ArrayList book_id,book_title,book_author,book_pages;
    private Activity activity;// for

    // Constructor;
    DB_CustomAdapter(Activity activity, Context context,
                     ArrayList book_id, ArrayList book_title, ArrayList book_author , ArrayList book_pages){
        this.activity = activity;
        this.context = context;
        this.book_author = book_author;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_pages = book_pages;
    }

    // This method is called when the adapter needs to create a new ViewHolder
    //
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent, false);
        return new MyViewHolder(view);
    }

    //  the onBindViewHolder is called repeatedly as the user scrolls, to update the ViewHolders with the correct data.
    @NonNull
    @Override
    public void onBindViewHolder(@NonNull DB_CustomAdapter.MyViewHolder holder, final int position) {
        holder.book_id_txt.setText(String.valueOf(book_id.get(position)));
        holder.book_title_txt.setText(String.valueOf(book_title.get(position)));
        holder.book_author_txt.setText(String.valueOf(book_author.get(position)));
        holder.book_pages_txt.setText(String.valueOf(book_pages.get(position)));

        // this OnClickListener is used for update (so that you can touch the line to modify it)
        holder.mainLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DB_UpdateActivity.class);
                intent.putExtra("id",String.valueOf(book_id.get(position)));
                intent.putExtra("title",String.valueOf(book_title.get(position)));
                intent.putExtra("author",String.valueOf(book_author.get(position)));
                intent.putExtra("pages",String.valueOf(book_pages.get(position)));
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView book_id_txt, book_author_txt, book_title_txt,book_pages_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_id_txt = itemView.findViewById(R.id.book_id_txt);
            book_pages_txt = itemView.findViewById(R.id.book_pages_txt);
            book_author_txt = itemView.findViewById(R.id.book_author_txt);
            book_title_txt = itemView.findViewById(R.id.book_title_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
