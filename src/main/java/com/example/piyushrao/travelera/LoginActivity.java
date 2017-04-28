package com.example.piyushrao.travelera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    public Button enter_name;
    public static final String NAME_MESSAGE = "com.example.name";

    public void login(){
        enter_name= (Button) findViewById(R.id.enter_name_login);
        enter_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editName = (EditText) findViewById(R.id.editName);
                String name = editName.getText().toString();
                if (name.isEmpty())
                {
                    editName.setError("Name Blank");

                }
                else
                {
                    Intent name_intent = new Intent(LoginActivity.this, LoginActivity.class);
                    name_intent.putExtra(NAME_MESSAGE,name);
                    Intent login = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(login);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login();
    }


}
