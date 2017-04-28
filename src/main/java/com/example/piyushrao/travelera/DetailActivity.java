package com.example.piyushrao.travelera;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Locale;
import java.util.jar.Attributes;

public class DetailActivity extends AppCompatActivity {

    private String name = LoginActivity.personName;
    private Float rating = 0.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        long id_long = intent.getLongExtra(AllActivity.ID_MESSAGE,1);

        setContentView(R.layout.activity_detail);
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
        TextView textView4 = (TextView) findViewById(R.id.textViewDetail4);
        TextView textViewName = (TextView) findViewById(R.id.textViewDetailName);
        EditText editComment = (EditText) findViewById(R.id.editTextComment);
        Button rate = (Button) findViewById(R.id.buttonRate);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar1);

        String string1;
        string1 = "ID: " + cursor1.getLong(0);
        textView1.setText(string1);
        string1 = "Site Name: " + cursor1.getString(1);
        textView2.setText(string1);
        string1 = "Rating: " + cursor1.getFloat(6);
        textView3.setText(string1);

        String sqlQuery2 = "Select * from TourRatings1 where " +
                "personName = '" + name + "' and siteID = " + id_long;
        cursor1 = db.rawQuery(sqlQuery2, null);
        cursor1.moveToFirst();
        if(cursor1.getCount() > 0)
        {
            rating = cursor1.getFloat(3);
            ratingBar.setRating(rating);
            ratingBar.setEnabled(false);
            rate.setText(R.string.add);
            editComment.setVisibility(View.VISIBLE);
            editComment.setHint(R.string.enter_review);
            textViewName.setText(R.string.add_review);
            textView4.setText(R.string.your_rating);
        }
        else
        {
            textViewName.setVisibility(View.INVISIBLE);
            editComment.setVisibility(View.INVISIBLE);
            rate.setVisibility(View.INVISIBLE);
            textView4.setText(R.string.rate_this);
        }
        cursor1.close();
        load_comments();
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                onClickRating();
            }
        });
    }

    public void onClickRating()
    {
        TextView textViewName = (TextView) findViewById(R.id.textViewDetailName);
        Button rate = (Button) findViewById(R.id.buttonRate);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
        TextView textView3 = (TextView) findViewById(R.id.textViewDetail3);
        TextView textView4 = (TextView) findViewById(R.id.textViewDetail4);
        EditText editComment = (EditText) findViewById(R.id.editTextComment);

        SQLiteDatabase db;

        db = openOrCreateDatabase("TestingData.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());
        Cursor cursor2;
        String sqlQuery = "Select * from TourNumerator1 where tableName = 'TourRatings1'";
        cursor2 = db.rawQuery(sqlQuery, null);
        cursor2.moveToFirst();
        long ratingID = cursor2.getLong(2);

        Intent intent = getIntent();
        long id_long = intent.getLongExtra(AllActivity.ID_MESSAGE,1);
        rating = ratingBar.getRating();
        db.execSQL("insert into TourRatings1 values("
                + ratingID + ","
                + id_long + ",'"
                + name + "',"
                + rating + ")"
        );
        ratingID += 1;
        db.execSQL("update TourNumerator1 set nextID = " + ratingID
                + " where tableName = 'TourRatings1'"
        );

        cursor2 = db.rawQuery("select avg(rating) from TourRatings1 where siteID = "
                + id_long, null);
        cursor2.moveToFirst();
        float avgRating = cursor2.getFloat(0);
        cursor2.close();

        db.execSQL("update TourSites1 set rating = " + avgRating
                + " where _id = " + id_long
        );
        String string1 = "Rating: " + avgRating;
        textView3.setText(string1);
        ratingBar.setEnabled(false);
        rate.setText(R.string.add);
        editComment.setVisibility(View.VISIBLE);
        rate.setVisibility(View.VISIBLE);
        editComment.setHint(R.string.enter_review);
        textViewName.setText(R.string.add_review);
        textView4.setText(R.string.thanks);
    }

    public void onClickRate(View view)
    {
        EditText editComment = (EditText) findViewById(R.id.editTextComment);
        Button rate = (Button) findViewById(R.id.buttonRate);

        String comment = editComment.getText().toString();

        SQLiteDatabase db;
        db = openOrCreateDatabase("TestingData.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());

        if(comment.isEmpty() && rate.getText().equals("ADD"))
        {
            editComment.animate();
            editComment.setError("Review Blank");
        }
        else if(rate.getText().equals("ADD"))
        {
            Cursor cursor2;
            String sqlQuery = "Select * from TourNumerator1 where tableName = 'TourComments1'";
            cursor2 = db.rawQuery(sqlQuery, null);
            cursor2.moveToFirst();
            long commentID = cursor2.getLong(2);

            Intent intent = getIntent();
            long id_long = intent.getLongExtra(AllActivity.ID_MESSAGE,1);
            //float rating2 = ratingBar.getRating();
            db.execSQL("insert into TourComments1 values("
                    + commentID + ","
                    + id_long + ",'"
                    + name + "',"
                    + rating + ",'"
                    + comment + "')"
            );
            commentID += 1;
            db.execSQL("update TourNumerator1 set nextID = " + commentID
                    + " where tableName = 'TourComments1'"
            );
            editComment.setText("");
            load_comments();
        }
    }

    public void load_comments()
    {
        SimpleCursorAdapter cur_adapter;
        SQLiteDatabase db;
        Cursor cursor3;

        db = openOrCreateDatabase("TestingData.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());

        Intent intent = getIntent();
        long id_long = intent.getLongExtra(AllActivity.ID_MESSAGE,1);
        String sqlQuery = "SELECT * FROM TourComments1 where siteID = " + id_long;
        cursor3 = db.rawQuery(sqlQuery, null);
        cursor3.moveToFirst();
        String[] fromColumns = {"personName", "comment", "rating"};
        int[] toViews = {R.id.textViewCommentPerson, R.id.textViewCommentText, R.id.textViewCommentRating};

        cur_adapter = new SimpleCursorAdapter(this,
                R.layout.list2_layout, cursor3, fromColumns, toViews, 0);
        final ListView lv = (ListView) findViewById(R.id.list2);
        lv.setAdapter(cur_adapter);
    }
}
