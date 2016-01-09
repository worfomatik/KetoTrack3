package com.example.that_girl.ketotrack;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class AddToFoodList extends AppCompatActivity {
    public void addFood(View view){

        String FILENAME = getString(R.string.food_filename);
        File inputFile = new File (getFilesDir(),FILENAME);
        String fargenflavor = "wtf.";

        if(inputFile.exists()) {
            try {

                FileInputStream fis = new FileInputStream(inputFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                LinkedList food = (LinkedList) ois.readObject();
                ois.close();

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
        Intent intent = new Intent(this, ViewFoods.class);
        AddToFoodList.this.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_food_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_to_food_list, menu);
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
