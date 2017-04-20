package com.example.piyushrao.travelera;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        long id_long = intent.getLongExtra(AllActivity.ID_MESSAGE,1l);

        SQLiteDatabase db;
        Cursor cursor1;

        db = openOrCreateDatabase("TestingData.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());
        String sqlQuery = "Select * from TourSites1 where _id = " + id_long;
        cursor1 = db.rawQuery(sqlQuery, null);
        cursor1.moveToFirst();

        TextView textView1 = (TextView) findViewById(R.id.textViewDetail1);
        TextView textView2 = (TextView) findViewById(R.id.textViewDetail2);
        TextView textView3 = (TextView) findViewById(R.id.textViewDetail3);

        String string1;
        string1 = "ID: " + cursor1.getLong(0);
        textView1.setText(string1);
        string1 = "Site Name: " + cursor1.getString(1);
        textView2.setText(string1);
        string1 = "Rating: " + cursor1.getFloat(6);
        textView3.setText(string1);
        cursor1.close();
    }
}
