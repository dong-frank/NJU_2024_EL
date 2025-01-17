package com.example.wheretogo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * 增加地点界面
 */
public class DB_AddActivity extends BaseActivity {
    EditText name_input, city_input, address_input;
    EditText PID_input, intro_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_add);


        name_input = findViewById(R.id.name_input);
        city_input = findViewById(R.id.city_input);
        address_input = findViewById(R.id.address_input);
        PID_input = findViewById(R.id.PID_input);
        intro_input = findViewById(R.id.intro_input);

        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                DB_MyDatabaseHelper myDB = new DB_MyDatabaseHelper(DB_AddActivity.this);
                myDB.addSite(
                        name_input.getText().toString().trim(),
                        city_input.getText().toString().trim(),
                        address_input.getText().toString().trim(),
                        PID_input.getText().toString().trim(),
                        intro_input.getText().toString().trim());
                myDB.UpdateTourStatus("1", myDB.getCurrentAt(), myDB.getTotalSitesNumber()+1);
                finish();
            }
        });
    }
}