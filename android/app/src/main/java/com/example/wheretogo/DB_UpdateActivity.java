package com.example.wheretogo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DB_UpdateActivity extends AppCompatActivity {
    EditText name_input2, city_input2, PID_input2;
    EditText address_input2,intro_input2;
    Button update_button,delete_button;

    String id, name, city, address, PID, intro; // used to save and display. And to update db

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_update);

        name_input2 = findViewById(R.id.name_input2);
        city_input2 = findViewById(R.id.city_input2);

        address_input2 = findViewById(R.id.address_input2);
        PID_input2 = findViewById(R.id.PID_input2);
        intro_input2 = findViewById(R.id.intro_input2);

        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        // call getAndSet..() first
        getAndSetIntentData();


        update_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // called after calling getAndSetIntentData()
                DB_MyDatabaseHelper myDB = new DB_MyDatabaseHelper((DB_UpdateActivity.this));

                name = name_input2.getText().toString().trim();
                city = city_input2.getText().toString().trim();
                address = address_input2.getText().toString().trim();
                PID = PID_input2.getText().toString().trim();
                intro = intro_input2.getText().toString().trim();
                myDB.updateData(id, name, city, address, PID, intro );
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });


    }

    // 从上一个Activity获取数据并设置到对应控件中进行显示
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name")
                && getIntent().hasExtra("city") && getIntent().hasExtra("address")
                && getIntent().hasExtra("PID")&& getIntent().hasExtra("intro")) {

            // Getting data
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            city = getIntent().getStringExtra("city");
            address = getIntent().getStringExtra("address");
            PID = getIntent().getStringExtra("PID");
            intro = getIntent().getStringExtra("intro");

            //Setting intent data
            name_input2.setText(name);
            city_input2.setText(city);
            address_input2.setText(address);
            PID_input2.setText(PID);
            intro_input2.setText(intro);

        }else{
            Toast.makeText(this, "没有数据哦", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除" + name + "?");
        builder.setMessage("您确定要删除"+ name +"吗?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DB_MyDatabaseHelper myDB = new DB_MyDatabaseHelper(DB_UpdateActivity.this);

                int current_at = myDB.getCurrentAt();
                int current_row_id = myDB.getRow_IdByCurrentAt(current_at);
                int target_row_id = Integer.parseInt(id);

                if(current_row_id == 0){
                    myDB.UpdateTourStatus("1",current_at, myDB.getTotalSitesNumber()-1);
                }
                else if(target_row_id <= current_row_id){
                    myDB.UpdateTourStatus("1",current_at-1, myDB.getTotalSitesNumber()-1);
                }else
                {
                    myDB.UpdateTourStatus("1",current_at, myDB.getTotalSitesNumber()-1);
                }
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}
