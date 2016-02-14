package com.example.that_girl.ketotrack;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.io.ObjectInputStream;
import java.util.LinkedList;

public class ViewDays extends AppCompatActivity {

    String DAYSFILE,USERFILE;
    TextView textView;
    File inputFile,inputFile1;
    LinkedList<Day> days;
    ListView listView;
    ArrayAdapter adapter;
    UserProfile user;

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Intent intent = new Intent(this, ViewOtherDay.class);
        intent.putExtra("dayIndex", adapter.getItem(info.position).toString());

        ViewDays.this.startActivity(intent);

        return true;
    }
    //Return to Main menu
    public void backToMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        ViewDays.this.startActivity(intent);
    }

    public void thisWorked(String s){
        Context context = getApplicationContext();
        CharSequence text = s;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_days);
        textView = (TextView) findViewById(R.id.textView);

        DAYSFILE = getString(R.string.days_filename);
        USERFILE = getString(R.string.user_filename);
        inputFile = new File (getFilesDir(),DAYSFILE);
        inputFile1 = new File (getFilesDir(),USERFILE);
        final Intent intent = new Intent(this, ViewOtherDay.class);

        try{
            inputFile = new File(getFilesDir(),DAYSFILE);
            FileInputStream fis = new FileInputStream(inputFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            days = (LinkedList) ois.readObject();
            ois.close();

            listView = (ListView) findViewById(R.id.listView1);
            adapter = new ArrayAdapter(this,R.layout.item_list_appearance,days);
            listView.setAdapter(adapter);


            FileInputStream fis1 = new FileInputStream(inputFile1);
            ObjectInputStream ois1 = new ObjectInputStream(fis1);
            user = (UserProfile) ois1.readObject();
            ois1.close();
            thisWorked(user.toString());

            textView.setText(user.toString());


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
                {
                    intent.putExtra("dayIndex", Integer.toString(position));
                    ViewDays.this.startActivity(intent);
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_days, menu);
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
