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

public class EditFood extends AppCompatActivity {


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


    //TODO:  I thought I needed settings to be loaded in this file, but i dont...
    //TODO:  Probably delete it later cuz its redundant but im leaving it here
    //TODO:  in case i make any more changes where i need it

    public void thisWorks(String s){
        Context context = getApplicationContext();
        CharSequence text = s;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

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


                food.remove(index);

                theFood.setName(field.getText().toString());
                theFood.setFat(Double.parseDouble(field1.getText().toString()));
                theFood.setProtein(Double.parseDouble(field2.getText().toString()));
                theFood.setCarbs(Double.parseDouble(field3.getText().toString()));
                theFood.setDescription(field4.getText().toString());

                food.add(theFood);

                //Save updated list
                File outputFile = new File (getFilesDir(),FILENAME);
                FileOutputStream fos = new FileOutputStream(outputFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(food);
                oos.close();

                //Update all instances of this stored in the days lists
                updateInstancesInDays();

                //Display food-added-to-list toast
                Context context = getApplicationContext();
                CharSequence text = "Updated and Saved.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Intent intent = new Intent(this, ViewFoods.class);
            EditFood.this.startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);
        try{
            FILENAME = getString(R.string.food_filename);
            FILENAME1 = getString(R.string.days_filename);
            FILENAME2 = getString(R.string.settings_filename);

            inputFile = new File(getFilesDir(),FILENAME);
            inputFile1 = new File(getFilesDir(),FILENAME1);
            inputFile2 = new File(getFilesDir(),FILENAME2);

            //Get the toString passed from the food list
            fis = new FileInputStream(inputFile);
            ois = new ObjectInputStream(fis);
            food = (LinkedList) ois.readObject();
            ois.close();


            intent = getIntent();
            desc = intent.getExtras().getString("foodDesc");

            //initialize local copy of the foodlist

            for(int i=0;i<food.size();i++){

                if(food.get(i).toString().equals(desc)){
                    index=i;

                }
            }
            thisWorks("Went through the loop");
            //save local copy of the food to be edited
            theFood = (FoodItem) food.get(index);

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

            inputFile2 = new File(getFilesDir(),FILENAME2);
            FileInputStream fis2 = new FileInputStream(inputFile2);
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            settings = (AppSettings) ois2.readObject();
            ois2.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    //TODO: doesn't account for multiple instances of the same item...
    //TODO: caused an issue during testing, but maybe once the foodlist is implemeneted
    //TODO: into EnterFoodItem this issue wont happen
    public void updateInstancesInDays(){
        try{
            fis1 = new FileInputStream(inputFile1);
            ois1 = new ObjectInputStream(fis1);
            days = (LinkedList) ois1.readObject();
            ois1.close();

            LinkedList <FoodItem> tempFoodList;
            for(int i=0;i<days.size();i++){
                tempFoodList = days.get(i).getFoodList();
                for(int j=0;j<tempFoodList.size();j++){
                    if(tempFoodList.get(j).toString().equals(desc)){

                        //pull data from fields
                        EditText field = (EditText)findViewById(R.id.foodName);
                        EditText field1 = (EditText)findViewById(R.id.foodFat);
                        EditText field2 = (EditText)findViewById(R.id.foodProtein);
                        EditText field3 = (EditText)findViewById(R.id.foodCarbs);
                        EditText field4 = (EditText)findViewById(R.id.description);


                        days.get(i).deleteFood(j);

                        theFood.setName(field.getText().toString());
                        theFood.setFat(Double.parseDouble(field1.getText().toString()));
                        theFood.setProtein(Double.parseDouble(field2.getText().toString()));
                        theFood.setCarbs(Double.parseDouble(field3.getText().toString()));
                        theFood.setDescription(field4.getText().toString());

                        days.get(i).addFood(theFood);

                    }
                }
            }

            //Save updated list
            File outputFile = new File (getFilesDir(),FILENAME1);
            FileOutputStream fos1 = new FileOutputStream(outputFile);
            ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
            oos1.writeObject(days);
            oos1.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_food, menu);
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
