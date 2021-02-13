package com.hemin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAccount extends AppCompatActivity {
    Button create;
    TextView name,age;
    DB_helper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_activity);
        create=findViewById(R.id.create);
        name=findViewById(R.id.Name);
        age=findViewById(R.id.Age);
        db = new DB_helper(this);
    }
    public void addContact(View view)
    {
        if(db.insertContact(name.getText().toString(), age.getText().toString())){
            Toast.makeText(getApplicationContext(), "You Created your account successfully",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Creating account FAILED",
                    Toast.LENGTH_SHORT).show();
        }
        this.finish();

    }
}
