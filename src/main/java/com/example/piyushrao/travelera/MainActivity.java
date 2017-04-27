package com.example.piyushrao.travelera;
//Test Nagrajan Remote
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public Button search;
    public Button nearby;

    public void init(){

        //Create Database
        SQLiteDatabase db;
        db = openOrCreateDatabase("TestingData.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());

        final String SQL_CREATE_ENTRIES =
                "CREATE TABLE IF NOT EXISTS " + "TourSites1" + " (" +
                        "_id" + " INTEGER PRIMARY KEY," +
                        "siteName" + " TEXT," +
                        "category" + " TEXT," +
                        "category2" + " TEXT," +
                        "latitude" + " DOUBLE," +
                        "longitude" + " DOUBLE," +
                        "rating" + " FLOAT)";
        db.execSQL(SQL_CREATE_ENTRIES);

        final String SQL_CREATE_RATINGS =
                "CREATE TABLE IF NOT EXISTS " + "TourRatings1" + " (" +
                        "_id" + " INTEGER PRIMARY KEY," +
                        "siteID" + " INTEGER," +
                        "personName" + " TEXT," +
                        "rating" + " FLOAT)";
        db.execSQL(SQL_CREATE_RATINGS);

        final String SQL_CREATE_COMMENTS =
                "CREATE TABLE IF NOT EXISTS " + "TourComments1" + " (" +
                        "_id" + " INTEGER PRIMARY KEY," +
                        "siteID" + " INTEGER," +
                        "personName" + " TEXT," +
                        "rating" + " FLOAT," +
                        "comment" + " TEXT)";
        db.execSQL(SQL_CREATE_COMMENTS);

        final String SQL_CREATE_NUMERATOR =
                "CREATE TABLE IF NOT EXISTS " + "TourNumerator1" + " (" +
                        "_id" + " INTEGER PRIMARY KEY," +
                        "tableName" + " TEXT," +
                        "nextID" + " INTEGER)";
        db.execSQL(SQL_CREATE_NUMERATOR);
        //Create initial Records
        Cursor cursor1 = db.rawQuery("SELECT * FROM TourSites1", null);
        if(cursor1.getCount() == 0)
        {
            db.execSQL("insert into TourSites1 values(1201,'Statue of Liberty','manMade',' ',40.689246, -74.044499,0)");
            db.execSQL("insert into TourSites1 values(1202,'Central Park','park',' ',40.782872, -73.965357,0)");
            db.execSQL("insert into TourSites1 values(1203,'Times Square','manMade',' ',40.758911, -73.985142,0)");
            db.execSQL("insert into TourSites1 values(1204,'Yankee Stadium','manMade',' ',40.829735, -73.926144,0)");
            db.execSQL("insert into TourSites1 values(1205,'One World Observatory','manMade',' ',40.713344, -74.013389,0)");
            db.execSQL("insert into TourSites1 values(1206,'Central Park Zoo','wildlife','park',40.768441,-73.970542,0)");
            db.execSQL("insert into TourSites1 values(1207,'Empire State Building','manMade',' ',40.748457,-73.985702,0)");
            db.execSQL("insert into TourSites1 values(1208,'Trinity Church','religious',' ',40.708065,-74.012185,0)");
            db.execSQL("insert into TourSites1 values(1209,'Battery Park','park','waterfront',40.702486,-74.016770,0)");
            db.execSQL("insert into TourSites1 values(1210,'Rockefeller Center','manMade',' ',40.758807,-73.978803,0)");
            db.execSQL("insert into TourSites1 values(1211,'Teardrop Park','park','waterfront',40.717068,-74.015532,0)");
            db.execSQL("insert into TourSites1 values(1212,'Charging Bull','manMade',' ',40.705523,-74.013457,0)");
            db.execSQL("insert into TourSites1 values(1213,'American Museum of Natural History','manMade','museum',40.781328,-73.973988,0)");

            db.execSQL("insert into TourNumerator1 values(1,'TourSites1',1214)");
            db.execSQL("insert into TourNumerator1 values(2,'TourRatings1',1)");
            db.execSQL("insert into TourNumerator1 values(3,'TourComments1',1)");
        }

        cursor1.close();
        db.close();

        search= (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent search= new Intent(MainActivity.this, AllActivity.class);
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
