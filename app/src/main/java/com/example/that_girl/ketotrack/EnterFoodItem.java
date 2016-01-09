package com.example.that_girl.ketotrack;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.LinkedList;

public class EnterFoodItem extends AppCompatActivity {
    //Todo: Add food to current day; figure out how to organize days in app as well
    //Todo: Input validation
    LinkedList <Day> days;
    LinkedList <FoodItem> food;
    AppSettings settings;
    TextView textView;
    File inputFile;
    File inputFile1;
    File inputFile2;
    String FILENAME;
    String FILENAME1;
    String FILENAME2;
    public void newDay(View view){
        days.get(settings.getIndex()).setEndDate(new Date());
        days.add(new Day());
        days.get(settings.getIndex()).setNotCurrentDay();
        settings.incIndex();
        Context context = getApplicationContext();
        CharSequence text = settings.getIndex()+"New Day Created for " + days.get(settings.getIndex()).getDate().toString();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        try {
            //Save updated settings
            File outputFile = new File(getFilesDir(), FILENAME2);
            FileOutputStream fos = new FileOutputStream(outputFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(settings);
            oos.close();
            //save days list
            File outputFile1 = new File(getFilesDir(), FILENAME1);
            FileOutputStream fos1 = new FileOutputStream(outputFile1);
            ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
            oos1.writeObject(days);
            oos1.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //TODO: Clear edit text fields after food item has been entered
    //TODO: display error message if no name is entered
    public void addFood(View view){
        if(inputFile.exists()) {
            try {
                //initialize local copy of days list
                FileInputStream fis1 = new FileInputStream(inputFile1);
                ObjectInputStream ois1 = new ObjectInputStream(fis1);
                days = (LinkedList) ois1.readObject();
                ois1.close();

                //pull data from fields
                EditText field = (EditText)findViewById(R.id.foodName);
                EditText field1 = (EditText)findViewById(R.id.foodFat);
                EditText field2 = (EditText)findViewById(R.id.foodProtein);
                EditText field3 = (EditText)findViewById(R.id.foodCarbs);
                EditText field4 = (EditText)findViewById(R.id.description);

                //Create new food item from data
                FoodItem f = new FoodItem(field.getText().toString(),
                        Double.parseDouble(field1.getText().toString()),
                        Double.parseDouble(field2.getText().toString()),
                        Double.parseDouble(field3.getText().toString()),
                        field4.getText().toString());

                //Add to list
                food.add(f);
                //add food to array in current day
                days.get(settings.getIndex()).addFood(f);

                /*Context context = getApplicationContext();
                CharSequence text = days.get(settings.getIndex()).toString();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();*/

                //save days list
                File outputFile1 = new File(getFilesDir(), FILENAME1);
                FileOutputStream fos1 = new FileOutputStream(outputFile1);
                ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
                oos1.writeObject(days);
                oos1.close();

                //Save updated list
                File outputFile = new File (getFilesDir(),FILENAME);
                FileOutputStream fos = new FileOutputStream(outputFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(food);
                oos.close();

                //Display food-added-to-list toast
                Context context = getApplicationContext();
                CharSequence text = "Food Added and saved to Food list";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    //Return to Main menu
    public void backToMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        EnterFoodItem.this.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_food_item);
        textView  = (TextView) findViewById(R.id.textView);

        FILENAME = getString(R.string.food_filename);
        FILENAME1 = getString(R.string.days_filename);
        FILENAME2 = getString(R.string.settings_filename);

        inputFile = new File (getFilesDir(),FILENAME);
        inputFile1 = new File (getFilesDir(),FILENAME1);
        inputFile2 = new File (getFilesDir(),FILENAME2);
        try {
            //initialize local copy of food list
            FileInputStream fis = new FileInputStream(inputFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            food = (LinkedList) ois.readObject();
            ois.close();

            //initialize local copy of days list
            FileInputStream fis1 = new FileInputStream(inputFile1);
            ObjectInputStream ois1 = new ObjectInputStream(fis1);
            days = (LinkedList) ois1.readObject();
            ois1.close();

            //initialize local copy of settings file
            FileInputStream fis2 = new FileInputStream(inputFile2);
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            settings = (AppSettings) ois2.readObject();
            ois2.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        textView.setText(days.get(settings.getIndex()).getDate().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enter_food_item, menu);
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
