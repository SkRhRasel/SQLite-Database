package com.example.rasel.sqlitedatabase;

import android.content.ContentUris;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName,editSurname,editMarks,editTextId;
    Button btnAddData;
    Button btnViewAll;
    Button btnUpdate;
    Button btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = (EditText)findViewById(R.id.editText_name);
        editSurname = (EditText)findViewById(R.id.editText_surname);
        editMarks = (EditText)findViewById(R.id.editText_marks);
        editTextId = (EditText)findViewById(R.id.editText_Id);

        btnAddData = (Button)findViewById(R.id.button_add);
        btnViewAll = (Button)findViewById(R.id.button_viewAll);
        btnUpdate = (Button)findViewById(R.id.button_update);
        btnDelete = (Button)findViewById(R.id.button_delete);

        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }

    public  void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString() );
                        if(isInserted == true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll(){
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0){
                    // after Creating  show Message method wors
                    showMessage("Error","Nothing Found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Id: "+ res.getString(0)+ "\n");
                    buffer.append("Name: "+ res.getString(1)+ "\n");
                    buffer.append("Surname: "+ res.getString(2)+ "\n");
                    buffer.append("Marks: "+ res.getString(3)+ "\n\n");
                }
                // show all data
                showMessage("Data", buffer.toString());
            }
        });
    }

    public void showMessage(String title,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    private void UpdateData() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myDb.updateData(editTextId.getText().toString(),
                        editName.getText().toString(),
                        editSurname.getText().toString(),
                        editMarks.getText().toString() );

                if (isUpdated == true){
                    Toast.makeText(MainActivity.this, "Data is Updated", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data is  not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    private void DeleteData() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                if (deletedRows >0){
                    Toast.makeText(MainActivity.this, "Data is Deleted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data is  not Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }






}
