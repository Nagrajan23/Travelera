package com.example.piyushrao.travelera;
//Test Nagrajan Remote
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public Button search;
    public Button nearby;

    public void init(){
        search= (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent search= new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(search);
                    }
                });

        nearby= (Button) findViewById(R.id.nearby);
        nearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nearby= new Intent(MainActivity.this, MapsActivity2.class);
                startActivity(nearby);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
}
