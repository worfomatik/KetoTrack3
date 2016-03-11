package com.example.that_girl.ketotrack;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.LinkedList;

public class ViewCurrentDay extends AppCompatActivity {
    TextView textView;
    AppSettings settings;
    LinkedList <Day> days;
    String FILENAME;
    String FILENAME1;
    File inputFile;
    File inputFile1;
    ListView listView;
    ArrayAdapter adapter1;

    public void backToMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        ViewCurrentDay.this.startActivity(intent);
    }

    public void editFood(String item){
        Intent intent = new Intent(this, EditFoodCurrentDay.class);
        for(int i=0;i<days.get(settings.getIndex()).getFoodList().size(); i++) {
            if (days.get(settings.getIndex()).getFoodList().get(i).toString().equals(item)){
                intent.putExtra("foodDesc", days.get(settings.getIndex()).getFoodList().get(i).toString());
            }
        }
        ViewCurrentDay.this.startActivity(intent);
    }
    //Delete food from food list and save to file
    public void deleteFood(String item){
        //TODO: Add some error prevention stuff. Maybe a message if for some unlikely reason the item isnt found
        //TODO: In that case make it so it only saves if something actually gets deleted
        //If item matches something from the list in the current day, remove it
        for(int i=0;i<days.get(settings.getIndex()).getFoodList().size();i++){
            if(days.get(settings.getIndex()).getFoodList().get(i).toString().equals(item)) {
                days.get(settings.getIndex()).deleteFood(i);
                break;
            }
        }
        //Update Days list on file
        try{
            File outputFile = new File (getFilesDir(),FILENAME);
            FileOutputStream fos = new FileOutputStream(outputFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(days);
            oos.close();

            textView.setText(days.get(settings.getIndex()).toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }
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
            editFood(adapter1.getItem(info.position).toString());
        }
        else if(item.getTitle()=="Delete"){
           deleteFood(adapter1.getItem(info.position).toString());
            adapter1.remove(info.position);
        }
        else {
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_current_day);
        textView  = (TextView) findViewById(R.id.textView);

        FILENAME = getString(R.string.days_filename);
        FILENAME1 = getString(R.string.settings_filename);

        inputFile = new File (getFilesDir(),FILENAME);
        inputFile1 = new File (getFilesDir(),FILENAME1);
        try{
            inputFile = new File(getFilesDir(),FILENAME);
            FileInputStream fis = new FileInputStream(inputFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            days = (LinkedList) ois.readObject();
            ois.close();

            inputFile1 = new File(getFilesDir(),FILENAME1);
            FileInputStream fis1 = new FileInputStream(inputFile1);
            ObjectInputStream ois1 = new ObjectInputStream(fis1);
            settings = (AppSettings) ois1.readObject();
            ois1.close();

            LinkedList <FoodItem> f = days.get(settings.getIndex()).getFoodList();
            listView = (ListView) findViewById(R.id.listView1);
            adapter1 = new ArrayAdapter(this,R.layout.item_list_appearance,f);
            listView.setAdapter(adapter1);
            listView.setBackgroundResource(R.drawable.scrollstyle);
            registerForContextMenu(listView);
            //Collections.sort(food);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        textView.setText(days.get(settings.getIndex()).toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_current_day, menu);
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
