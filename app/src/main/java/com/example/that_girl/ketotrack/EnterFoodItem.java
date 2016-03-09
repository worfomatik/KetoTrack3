package com.example.that_girl.ketotrack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.LinkedList;
import android.widget.ArrayAdapter;

public class EnterFoodItem extends AppCompatActivity {


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
    AutoCompleteTextView field;
    EditText field1,field2,field3,field4;
    AutoCompleteTextView text;
    MultiAutoCompleteTextView text1;
    FoodItem tempFood;
    ArrayAdapter adapter;

    public void autoFillFields(){
        try{
            for(int i=0; i<food.size();i++){
                if(food.get(i).toString().equals(field.getText().toString())){
                    tempFood = food.get(i);
                    field.setText(tempFood.getName());
                    field1.setText(Double.toString(tempFood.getFat()));
                    field2.setText(Double.toString(tempFood.getProtein()));
                    field3.setText(Double.toString(tempFood.getCarbs()));
                    field4.setText(tempFood.getDescription());

                    break;
                }
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        addFood();
    }

    public void thisWorked(String s){
        Context context = getApplicationContext();
        CharSequence text = s;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void newDay(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(EnterFoodItem.this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm New Day...");

        // Setting Dialog Message
        alertDialog.setMessage("Start a new day?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                days.get(settings.getIndex()).setEndDate(new Date());
                days.add(new Day());
                days.get(settings.getIndex()).setNotCurrentDay();
                settings.incIndex();
                Context context = getApplicationContext();
                CharSequence text = "New Day Created for " + days.get(settings.getIndex()).getDate().toString();
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refresh();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();



    }

    /**
     * Reload the activity
     */
    public void refresh(){
        Intent intent = new Intent(this, EnterFoodItem.class);
        EnterFoodItem.this.startActivity(intent);
    }

    //Adds new food to both the current day list and master food list when add button is pressed
    public void addFood(View view){
        //Check if name is empty
        if(field.getText().toString().equals("")){
            thisWorked("Please enter a name.");
        }
        else{
            if(inputFile.exists()) {
                try {
                    //initialize local copy of days list
                    FileInputStream fis1 = new FileInputStream(inputFile1);
                    ObjectInputStream ois1 = new ObjectInputStream(fis1);
                    days = (LinkedList) ois1.readObject();
                    ois1.close();

                    //pull data from fields
                    field = (AutoCompleteTextView)findViewById(R.id.foodName);
                    field1 = (EditText)findViewById(R.id.foodFat);
                    field2 = (EditText)findViewById(R.id.foodProtein);
                    field3 = (EditText)findViewById(R.id.foodCarbs);
                    field4 = (EditText)findViewById(R.id.description);

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

                    //Reset fields
                    field.getText().clear();
                    field1.getText().clear();
                    field2.getText().clear();
                    field3.getText().clear();
                    field4.getText().clear();

                    //Update adapter for predictions
                    adapter.add(f);


                }
                catch(NumberFormatException num){
                    thisWorked("Oops! please enter valid numbers.");
                    num.printStackTrace();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }




    }
    //Adds food to only the current day b/c food already exists in the master food list
    public void addFood(){
        if(inputFile.exists()) {
            try {
                //initialize local copy of days list
                FileInputStream fis1 = new FileInputStream(inputFile1);
                ObjectInputStream ois1 = new ObjectInputStream(fis1);
                days = (LinkedList) ois1.readObject();
                ois1.close();

                //pull data from fields
                field = (AutoCompleteTextView)findViewById(R.id.foodName);
                field1 = (EditText)findViewById(R.id.foodFat);
                field2 = (EditText)findViewById(R.id.foodProtein);
                field3 = (EditText)findViewById(R.id.foodCarbs);
                field4 = (EditText)findViewById(R.id.description);

                //Create new food item from data
                FoodItem f = new FoodItem(field.getText().toString(),
                        Double.parseDouble(field1.getText().toString()),
                        Double.parseDouble(field2.getText().toString()),
                        Double.parseDouble(field3.getText().toString()),
                        field4.getText().toString());
                //add food to array in current day
                days.get(settings.getIndex()).addFood(f);

                //save days list
                File outputFile1 = new File(getFilesDir(), FILENAME1);
                FileOutputStream fos1 = new FileOutputStream(outputFile1);
                ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
                oos1.writeObject(days);
                oos1.close();

                //Display food-added-to-list toast
                Context context = getApplicationContext();
                CharSequence text = "Food Added";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                //Reset fields
                field.getText().clear();
                field1.getText().clear();
                field2.getText().clear();
                field3.getText().clear();
                field4.getText().clear();

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

        //pull data from fields
        field = (AutoCompleteTextView)findViewById(R.id.foodName);
        field1 = (EditText)findViewById(R.id.foodFat);
        field2 = (EditText)findViewById(R.id.foodProtein);
        field3 = (EditText)findViewById(R.id.foodCarbs);
        field4 = (EditText)findViewById(R.id.description);
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

            //initialize autocomplete adapter with food list
            text=(AutoCompleteTextView)findViewById(R.id.foodName);
            adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,food);
            text.setAdapter(adapter);

            //set predictive text to kick in after 3 characters entered
            text.setThreshold(3);

            text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    autoFillFields();
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(days.isEmpty()){
            newDay();
        }
        textView.setText(days.get(settings.getIndex()).getDateString());
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
