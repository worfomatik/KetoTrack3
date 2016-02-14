package com.example.that_girl.ketotrack;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.LinkedList;


//TODO:     Apparently the application is using the main thread too much
//TODO:     So make sure to push all the work to other threads
//TODO:     apparently the app shouldn't be making android skip > 100 frames
//TODO:     One more button to show the activity displaying total progress. Probably put it into a table for now. Graphs will probably end up being a 2.0 feature
//TODO:     add 'warning' functionality when foods entered for the day start hitting close to the daily values
//TODO:     Add link somewhere to the macro calculator online
//TODO:     For testing, set up something so people can easily enter bugs into a db

public class MainActivity extends AppCompatActivity {
    public void viewOtherDay(View view){
        Intent intent = new Intent(this, ViewDays.class);
        MainActivity.this.startActivity(intent);
    }
    public void viewCurrentDay(View view){
        Intent intent = new Intent(this, ViewCurrentDay.class);
        MainActivity.this.startActivity(intent);
    }
    public void viewFoods(View view){
        Intent intent = new Intent(this, ViewFoods.class);
        MainActivity.this.startActivity(intent);
    }
    public void enterFood(View view){
        Intent intent = new Intent(this, EnterFoodItem.class);
        MainActivity.this.startActivity(intent);
    }
    public void profileView(View view){
        Intent intent = new Intent(this, Profile.class);
        MainActivity.this.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //First time use, if food items file/list doesn't exist, make it!
        String FILENAME = getString(R.string.food_filename);
        String FILENAME2 = getString(R.string.days_filename);
        String FILENAME3 = getString(R.string.settings_filename);

        File outputFile = new File (getFilesDir(),FILENAME);
        File outputFile1 = new File (getFilesDir(),FILENAME2);
        File outputFile2 = new File (getFilesDir(),FILENAME3);

        //TODO:  Split this up so that all files don't get rewritten if just one is missing
        //TODO:  but definitely wait until you're done modifying the underlying base
        //TODO:  data types
        if(!outputFile.exists() || !outputFile1.exists() || !outputFile2.exists()){
            LinkedList<FoodItem> food = new LinkedList<FoodItem>();
            LinkedList<Day> days = new LinkedList<Day>();
            AppSettings settings = new AppSettings();
            try{
                FileOutputStream fos = new FileOutputStream(outputFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(food);
                oos.close();

                FileOutputStream fos1 = new FileOutputStream(outputFile1);
                ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
                Day today = new Day();
                days.add(today);
                oos1.writeObject(days);
                oos1.close();

                FileOutputStream fos2 = new FileOutputStream(outputFile2);
                ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
                settings.setCurrentDate(days.get(0).getDate());
                settings.setIndex(0);
                oos2.writeObject(settings);
                oos2.close();

                Context context = getApplicationContext();
                CharSequence text = "First time use, files created and saved!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
