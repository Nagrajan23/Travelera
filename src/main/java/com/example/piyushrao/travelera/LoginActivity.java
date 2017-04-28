package com.example.piyushrao.travelera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    public Button enter_name;
    public static final String NAME_MESSAGE = "com.example.NAME_MESSAGE";
    public static String personName;

    public void login(){
        enter_name= (Button) findViewById(R.id.enter_name_login);
        enter_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEnter();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText editName1 = (EditText) findViewById(R.id.editName);
        editName1.setInputType(InputType.TYPE_CLASS_TEXT);
        editName1.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event)
            {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    // Handle pressing "Enter" key here
                    onClickEnter();

                    handled = true;
                }
                return handled;
            }
        });
        login();
    }

    public void onClickEnter()
    {
        EditText editName1 = (EditText) findViewById(R.id.editName);
        String name = editName1.getText().toString();
        if (name.isEmpty())
        {
            editName1.setError("Name Blank");
        }
        else
        {
            personName = name;
            Intent login = new Intent(LoginActivity.this, MainActivity.class);
            login.putExtra(NAME_MESSAGE, name);
            startActivity(login);
        }
    }
}
