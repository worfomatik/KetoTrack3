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
import java.util.LinkedList;

public class EditFoodCurrentDay extends AppCompatActivity {
    Intent intent;
    String desc;
    LinkedList food;
    LinkedList <Day> days;
    String FILENAME;
    String FILENAME1;
    String FILENAME2;
    File inputFile,inputFile1,inputFile2;
    FileInputStream fis;
    ObjectInputStream ois;
    FileInputStream fis1;
    ObjectInputStream ois1;
    FoodItem theFood;
    AppSettings settings;
    int index;
    int dayIndex;
    EditText editText;

    public void updateFood(View view){

        File inputFile = new File (getFilesDir(),FILENAME);

        if(inputFile.exists()) {
            try {

                //pull data from fields
                EditText field = (EditText)findViewById(R.id.foodName);
                EditText field1 = (EditText)findViewById(R.id.foodFat);
                EditText field2 = (EditText)findViewById(R.id.foodProtein);
                EditText field3 = (EditText)findViewById(R.id.foodCarbs);
                EditText field4 = (EditText)findViewById(R.id.description);


                days.get(settings.getIndex()).getFoodList().remove(index);

                theFood.setName(field.getText().toString());
                theFood.setFat(Double.parseDouble(field1.getText().toString()));
                theFood.setProtein(Double.parseDouble(field2.getText().toString()));
                theFood.setCarbs(Double.parseDouble(field3.getText().toString()));
                theFood.setDescription(field4.getText().toString());

                days.get(settings.getIndex()).getFoodList().add(theFood);

                //Save updated list
                File outputFile = new File (getFilesDir(),FILENAME1);
                FileOutputStream fos = new FileOutputStream(outputFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(days);
                oos.close();

                //Update all instances of this stored in the days lists
                //updateInstancesInDays();

                //Display food-added-to-list toast
                Context context = getApplicationContext();
                CharSequence text = "Updated and Saved.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Intent intent = new Intent(this, ViewCurrentDay.class);
            EditFoodCurrentDay.this.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food_current_day);
        try{
            FILENAME = getString(R.string.food_filename);
            FILENAME1 = getString(R.string.days_filename);
            FILENAME2 = getString(R.string.settings_filename);

            inputFile = new File(getFilesDir(),FILENAME);
            inputFile1 = new File(getFilesDir(),FILENAME1);
            inputFile2 = new File(getFilesDir(),FILENAME2);

            inputFile2 = new File(getFilesDir(),FILENAME2);
            FileInputStream fis2 = new FileInputStream(inputFile2);
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            settings = (AppSettings) ois2.readObject();
            ois2.close();

            //Get the toString passed from the food list
            fis = new FileInputStream(inputFile1);
            ois = new ObjectInputStream(fis);
            days = (LinkedList) ois.readObject();
            ois.close();


            intent = getIntent();
            desc = intent.getExtras().getString("foodDesc");

            //initialize local copy of the foodlist

            for(int i=0;i<days.get(settings.getIndex()).getFoodList().size();i++){

                if(days.get(settings.getIndex()).getFoodList().get(i).toString().equals(desc)){
                    index=i;

                }
            }
            //thisWorks("Went through the loop");
            //save local copy of the food to be edited
            theFood = (FoodItem) days.get(settings.getIndex()).getFoodList().get(index);

            //Set fields to current food info
            editText = (EditText)findViewById(R.id.foodName);
            editText.setText(theFood.getName(), TextView.BufferType.EDITABLE);
            editText = (EditText)findViewById(R.id.foodFat);
            editText.setText(Double.toString(theFood.getFat()), TextView.BufferType.EDITABLE);
            editText = (EditText)findViewById(R.id.foodProtein);
            editText.setText(Double.toString(theFood.getProtein()), TextView.BufferType.EDITABLE);
            editText = (EditText)findViewById(R.id.foodCarbs);
            editText.setText(Double.toString(theFood.getCarbs()), TextView.BufferType.EDITABLE);
            editText = (EditText)findViewById(R.id.description);
            editText.setText(theFood.getDescription(), TextView.BufferType.EDITABLE);


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_food_current_day, menu);
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
