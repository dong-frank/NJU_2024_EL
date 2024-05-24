package com.example.wheretogo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DB_MainActivity extends AppCompatActivity{
    // Declarations for interface of MainActivity
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    // To get the info of db and display
    DB_MyDatabaseHelper myDB;
    ArrayList<String> book_id,book_title,book_author,book_pages;

    // QUESTION:
    DB_CustomAdapter DBCustomAdapter;

    // QUESTION: constructor???
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_main);

        // initializing buttons
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        // clicking event
        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Activity activity = DB_MainActivity.this;
                Intent intent = new Intent(DB_MainActivity.this, DB_AddActivity.class);
                activity.startActivityForResult(intent,1);
            }
        });

        myDB = new DB_MyDatabaseHelper(DB_MainActivity.this);
        book_id = new ArrayList<>();
        book_author = new ArrayList<>();
        book_title = new ArrayList<>();
        book_pages = new ArrayList<>();


        storeDataInArrays();

        DBCustomAdapter = new DB_CustomAdapter(DB_MainActivity.this,this, book_id,book_title,book_author,book_pages);
        recyclerView.setAdapter(DBCustomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DB_MainActivity.this));
    }

    // to refresh after update_button ;
    // Cooperate with requestCode... Don't quite get it...
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            recreate();
        }
    }

    // cooperate with the local ArrayList so that we can display info
    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No Data.",Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }
        }
    }

}