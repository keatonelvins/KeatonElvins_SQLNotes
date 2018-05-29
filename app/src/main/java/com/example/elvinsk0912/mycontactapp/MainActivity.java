package com.example.elvinsk0912.mycontactapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
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

    public void viewData(View view){
        Cursor res = myDb.getAllData();
        Log.d("MyContactApp", "MainActivity:  viewData: received cursor");

        if(res.getCount() == 0){
            showMessage("Error", "No data found in the database");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            //Append res column 0,1,2,3 to the buffer - see StringBuffer and Cursor API
            //Delimit each of the "appends" with the line feed "\n"
            for(int i = 0; i < 4; i++){
                buffer.append(res.getString(i));
                buffer.append("\n");
            }
            buffer.append("\n");
        }
        Log.d("MyContactApp", "MainActivity:  buffer assembled");

        showMessage("Data", buffer.toString());
    }

    private void showMessage(String title, String message) {
        Log.d("MyContactApp", "MainActivity:  showMessage: assembling AlertDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public static final String EXTRA_MESSAGE = "com.example.elvinsk0912.mycontactapp_p2.MESSAGE";
    public void searchRecord(View view){
        Log.d("MyContactApp", "MainActivity:  launching my search activity");
        android.content.Intent intent = new android.content.Intent(this, SearchActivity.class);



        Cursor res = myDb.getAllData();
        Log.d("MyContactApp", "MainActivity:  searchRecord: received cursor");

        if(res.getCount() == 0){
            showMessage("Error", "No data found in the database");
            return;
        }

        StringBuffer buffer = new StringBuffer();

        while (res.moveToNext()){
            //Append res column 0,1,2,3 to the buffer - see StringBuffer and Cursor API
            //Delimit each of the "appends" with the line feed "\n"
           if(res.getString(1).equals(editName.getText().toString())) {
               buffer.append(res.getString(1) + "\n");
               buffer.append(res.getString(2) + "\n");
               buffer.append(res.getString(3) + "\n");
           }
        }



        intent.putExtra(EXTRA_MESSAGE, buffer.toString());
        startActivity(intent);
    }
}
