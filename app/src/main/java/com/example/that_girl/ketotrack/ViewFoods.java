package com.example.that_girl.ketotrack;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.LinkedList;

public class ViewFoods extends AppCompatActivity {
    FileInputStream fis;
    ObjectInputStream ois;
    LinkedList food;
    ListView listView;
    ArrayAdapter adapter;
    String FILENAME;

    //TODO: when you click on a food it should ask if you want to edit or delete it
    //TODO: if you wanna edit, then the (currently non-existent) edit food activity starts
    //TODO: switch from declaring menu here to using an XML file
    //TODO: alphabetize foods oncreate
    //TODO: add food button needs a new activity for adding food without adding to day and routes back to view foods
    //TODO: clean up the code here theres a whole bunch of unused crap now
    //Delete food from food list and save to file
    public void deleteFood(String item){
        //TODO: Add some error prevention stuff. Maybe a message if for some unlikely reason the item isnt found
        //TODO: In that case make it so it only saves if something actually gets deleted
        //If item matches something from the list, remove it
        for(int i=0;i<food.size();i++){
            if(food.get(i).toString().equals(item)){
                food.remove(i);
                break;
            }
        }
        //Save new list to file
        try{
            File outputFile = new File (getFilesDir(),FILENAME);
            FileOutputStream fos = new FileOutputStream(outputFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(food);
            oos.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    //TODO: Delete this function when this file is all good
    //Toast generator to check how things work
    public void thisWorks(String s){
        Context context = getApplicationContext();
        CharSequence text = s;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    //Go to add food activity
    public void addFood(View view){
        Intent intent = new Intent(this, AddToFoodList.class);
        ViewFoods.this.startActivity(intent);
    }

    //Return to Main menu
    public void backToMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        ViewFoods.this.startActivity(intent);
    }
    public void editFood(String item){
        Intent intent = new Intent(this, EditFood.class);
        for(int i=0;i<food.size(); i++) {
            if (food.get(i).toString().equals(item)){
                intent.putExtra("foodDesc", food.get(i).toString());
            }
        }
        ViewFoods.this.startActivity(intent);
    }
    //Context menu to edit or delete item from the food list
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Edit Item");
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");

    }
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        //adapter.getItem(info.position).toString() is what I'm using to find the food item in the linkedlist in the adapter
        if(item.getTitle()=="Edit"){
            editFood(adapter.getItem(info.position).toString());
        }
        else if(item.getTitle()=="Delete"){
            try{
                deleteFood(adapter.getItem(info.position).toString());
                adapter.remove(info.position);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            //thisWorks(Integer.toString(info.position));

        }
        else {
            return false;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_foods);
        FILENAME = getString(R.string.food_filename);

        File inputFile = new File(getFilesDir(),FILENAME);

        try{
            fis = new FileInputStream(inputFile);
            ois = new ObjectInputStream(fis);
            food = (LinkedList) ois.readObject();
            ois.close();
            listView = (ListView) findViewById(R.id.listView);
            adapter = new ArrayAdapter<LinkedList>(this,R.layout.item_list_appearance,food);
            listView.setAdapter(adapter);
            registerForContextMenu(listView);
            Collections.sort(food);
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_foods, menu);
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
