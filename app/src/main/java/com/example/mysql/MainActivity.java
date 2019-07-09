package com.example.mysql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSave, btnSearch, btnSelect, btnDelete, btnUpdate;
    SQLiteDatabase sqLiteDatabase;
    EditText editSearch, editName, editEmail, editAge;
    String name1, mail, age, searchme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLiteDatabase = openOrCreateDatabase("MY DB",
                Context.MODE_PRIVATE, null);
        ((SQLiteDatabase) sqLiteDatabase).execSQL("CREATE TABLE IF NOT EXISTS students(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(255),Search VARCHAR(255),Age VARCHAR(255),Email VARCHAR(255))");
        setContentView(R.layout.activity_main);
        btnSave = findViewById(R.id.btnSave);
        btnSearch = findViewById(R.id.btnSearch);
        btnSelect = findViewById(R.id.btnSelect);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        editSearch = findViewById(R.id.editSearch);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editAge = findViewById(R.id.editAge);
        btnSave.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.home) {
            name1 = editName.getText().toString().trim();
            mail = editEmail.getText().toString().trim();
            age = editAge.getText().toString().trim();
            if (name1.equals("") || mail.equals("") || age.equals("")) {
                Toast.makeText(this, "fields cant be empty", Toast.LENGTH_SHORT).show();
                return;
            } else {
                sqLiteDatabase.execSQL("Insert into" + " students( Name,Email,Age)VALUES('" + editName + "','" + editEmail + "','" + editAge + "');");
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();


            }
        } else if (view.getId() == R.id.btnSelect) {
            Cursor c = sqLiteDatabase.rawQuery("select * from students", null);
            if (c.getCount() == 0) {
                Toast.makeText(this, "empty database", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuffer buffer = new StringBuffer();
            while (c.moveToNext()) {
                buffer.append("student name" + c.getString(1) + "\n");
                buffer.append("student name" + c.getString(2) + "\n");
                buffer.append("student name" + c.getString(3) + "\n");

            }
            Toast.makeText(this, buffer.toString(), Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btnSearch) {
            searchme = editSearch.getText().toString().trim();
            if (searchme.equals("")) {
                Toast.makeText(this, "enter student name first", Toast.LENGTH_SHORT).show();
                return;

            }
            Cursor c = sqLiteDatabase.rawQuery("select * from students where name='" + searchme + "'", null);
            if (c.moveToFirst()) {
                editName.setText(c.getString(1));
                editEmail.setText(c.getString(2));
                editAge.setText(c.getString(3));
            } else {
                Toast.makeText(this, "student not found", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.btnUpdate) {
            name1 = editName.getText().toString().trim();
            mail = editEmail.getText().toString().trim();
            age = editAge.getText().toString().trim();
            searchme = editSearch.getText().toString().trim();
            if (searchme.equals("")) {
                Toast.makeText(this, "enter student name to update", Toast.LENGTH_SHORT).show();
                return;
            }
            Cursor cursorupdate = sqLiteDatabase.rawQuery
                    ("select*from students where name='" + searchme + "'", null);
            if (cursorupdate.moveToFirst()) {
                if (name1.equals("") || mail.equals("") || age.equals("")) {
                    Toast.makeText(this, "fields cant be empty", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    sqLiteDatabase.execSQL("update students setname1='" + name1 + "',+Email='" + mail + "',age ='" + age + "'");
                Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
            } else if (view.getId() == R.id.btnDelete) {
                searchme = editSearch.getText().toString().trim();
                if (searchme.equals("")) {
                    Toast.makeText(this, "enter student name first", Toast.LENGTH_SHORT).show();
                    return;

                }
                Cursor c = sqLiteDatabase.rawQuery("select * from students where name='" + searchme + "'", null);
                if (c.moveToFirst()) {
                    sqLiteDatabase.execSQL("Delete from students where name='"+searchme +"'");

                } else {
                    Toast.makeText(this, "student deleted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}


