package com.example.wheretogo;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

/**
 * 鼠鼠去过哪里界面
 */
public class DB_MainActivity extends BaseActivity{
    // Declarations for interface of MainActivity

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, DB_MainActivity.class);
        intent.putExtra("param1", data1);
        intent.putExtra("param2", data2);
        context.startActivity(intent);
    }
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    // To get the info of db and display
    DB_MyDatabaseHelper myDB;
    ArrayList<String> site_id, site_name, site_city, site_address;
    ArrayList<String> site_PID,site_intro;

    DB_CustomAdapter DBCustomAdapter;
    

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_main);
        // initializing buttons
        com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigationView = findViewById(com.example.wheretogo.R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.map);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                Toast.makeText(this, "返回主界面", Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else if (itemId == R.id.map) {
                Toast.makeText(this, "随便走走吧", Toast.LENGTH_SHORT).show();
                GameMapActivity.Companion.actionStart(DB_MainActivity.this, false, "data2");
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else if (itemId == R.id.add) {
                Toast.makeText(this, "鼠鼠去过哪儿", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
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
        // if you want to check the function of the two getSelectedData, cancel the Annotation of next line;
        //myDB.getSelectedDataTest();// plz ignore this, this is for test usage;
        site_id = new ArrayList<>();
        site_city = new ArrayList<>();
        site_name = new ArrayList<>();
        site_address = new ArrayList<>();
        site_PID = new ArrayList<>();
        site_intro = new ArrayList<>();


        storeDataInArrays();

        DBCustomAdapter = new DB_CustomAdapter(DB_MainActivity.this,this, site_id, site_name, site_city, site_address,site_PID,site_intro);
        recyclerView.setAdapter(DBCustomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DB_MainActivity.this));
    }

    // to refresh after update_button ;
    // Cooperate with requestCode... Don't quite get it...
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                recreate();
            }
        }
    }

    // cooperate with the local ArrayList so that we can display info
    void storeDataInArrays(){

        Cursor cursor = myDB.readAllData();

        if(cursor.getCount() == 0){
            Toast.makeText(this,"无数据哦~",Toast.LENGTH_SHORT).show();
        }else{

            while(cursor.moveToNext()){
                site_id.add(cursor.getString(0));
                site_name.add(cursor.getString(1));
                site_city.add(cursor.getString(2));
                site_address.add(cursor.getString(3));
                site_PID.add(cursor.getString(4));
                site_intro.add(cursor.getString(5));
            }
        }
    }

    protected void onDestroy(){
        super.onDestroy();
        myDB.close();
    }
    protected void onPause(){
        super.onPause();
        myDB.close();
    }
    protected void onResume(){
        super.onResume();
        com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigationView = findViewById(com.example.wheretogo.R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.add);
        myDB = new DB_MyDatabaseHelper(DB_MainActivity.this);
    }
}
