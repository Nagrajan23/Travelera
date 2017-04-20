package com.example.piyushrao.travelera;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Locale;

public class AllActivity extends AppCompatActivity {

    public static final String ID_MESSAGE = "com.example.ID";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout1);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_all, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            int view_number = getArguments().getInt(ARG_SECTION_NUMBER);
            //Connect Database
            SimpleCursorAdapter cur_adapter;
            SQLiteDatabase db;
            Cursor cursor1;

            db = getActivity().openOrCreateDatabase("TestingData.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            db.setVersion(1);
            db.setLocale(Locale.getDefault());
            String sqlQuery = " ";
            switch(view_number)
            {
                case 1: sqlQuery = " "; break;
                case 2: sqlQuery = "where category = 'waterfront' OR category2 = 'waterfront'"; break;
                case 3: sqlQuery = "where category = 'park' OR category2 = 'park'"; break;
                case 4: sqlQuery = "where category = 'religious' OR category2 = 'religious'"; break;
                case 5: sqlQuery = "where category = 'museum' OR category2 = 'museum'"; break;
                case 6: sqlQuery = "where category = 'mountain' OR category2 = 'mountain'"; break;
                case 7: sqlQuery = "where category = 'wildlife' OR category2 = 'wildlife'"; break;
                case 8: sqlQuery = "where category = 'manMade' OR category2 = 'manMade'"; break;
            }
            sqlQuery = "SELECT * FROM TourSites1 " + sqlQuery;
            cursor1 = db.rawQuery(sqlQuery, null);

            cursor1.moveToFirst();
            String[] fromColumns = {"siteName", "rating"};
            int[] toViews = {R.id.textViewList1, R.id.textViewList2};

            cur_adapter = new SimpleCursorAdapter(this.getContext(),
                    R.layout.list1_layout, cursor1, fromColumns, toViews, 0);
            final ListView lv = (ListView) rootView.findViewById(R.id.list1);
            lv.setAdapter(cur_adapter);
            lv.setClickable(true);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object o = lv.getItemAtPosition(position);
                    Intent intent = new Intent(getContext(),DetailActivity.class);
                    intent.putExtra(ID_MESSAGE, id);
                    startActivity(intent);
                }
            });

            //cursor1.close();

            String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                    "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                    "Linux", "OS/2", "xx", "yy", "zz", "bb", "cc", "dd" };
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),
                    android.R.layout.simple_list_item_1, values);

            //lv.setAdapter(adapter);

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 8;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "All";
                case 1:
                    return "Waterfronts";
                case 2:
                    return "Parks";
                case 3:
                    return "Religious";
                case 4:
                    return "Museums";
                case 5:
                    return "Mountains";
                case 6:
                    return "Wildlife";
                case 7:
                    return "Man Made";
            }
            return null;
        }
    }
}
