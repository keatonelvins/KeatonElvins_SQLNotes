package com.example.elvinsk0912.mycontactapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName;
    EditText editPhone;
    EditText editAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = findViewById(R.id.editText_name);
        editPhone = findViewById(R.id.editText_phone);
        editAddress = findViewById(R.id.editText_address);
        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp", "MainActivity:  instantiated database");
    }

    public void addData(View view){
        Log.d("MyContactApp", "MainActivity:  add-contact button press");

        boolean isInserted = myDb.insertData(editName.getText().toString(), editPhone.getText().toString(), editAddress.getText().toString());

        if(isInserted)
            Toast.makeText(this, "Success, contact inserted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Failure, contact not inserted", Toast.LENGTH_LONG).show();
    }
}
