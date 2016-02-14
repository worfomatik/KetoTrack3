package com.example.that_girl.ketotrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.LinkedList;

public class ViewOtherDay extends AppCompatActivity {
    LinkedList<Day> days;
    File daysInputFile;
    String DAYSFILE;
    TextView textView;
    int index;
    Intent intent;
    ArrayAdapter adapter;
    ListView listView;

    public void backToMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        ViewOtherDay.this.startActivity(intent);
    }
    public void otherDays(View view){
        Intent intent = new Intent(this, ViewDays.class);
        ViewOtherDay.this.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_other_day);
        DAYSFILE = getString(R.string.days_filename);

        try{
            daysInputFile = new File(getFilesDir(),DAYSFILE);
            FileInputStream fis = new FileInputStream(daysInputFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            days = (LinkedList) ois.readObject();
            ois.close();
            String id = getIntent().getStringExtra("dayIndex");
            index = Integer.parseInt(id);

            textView = (TextView) findViewById(R.id.textView);

            textView.setText(days.get(index).toString());
            listView = (ListView) findViewById(R.id.listView1);
            adapter = new ArrayAdapter(this,R.layout.item_list_appearance,days.get(index).getFoodList());
            listView.setAdapter(adapter);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_other_day, menu);
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
}
