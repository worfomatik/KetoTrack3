package com.example.that_girl.ketotrack;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;
import java.util.LinkedList;

import static com.example.that_girl.ketotrack.R.*;

public class Progress extends AppCompatActivity {

    LinkedList<Day> days;

    String DAYSFILE,USERFILE;
    File inputFile,inputFile1;
    UserProfile user;

    //TODO: wherever user profile data is used make sure the user has entered profile info and display message to do so if it isnt there

    public void backToMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        Progress.this.startActivity(intent);
    }
    public void makeTables(){
        TableLayout tableLayout = (TableLayout)findViewById(id.tableLayout);

        for(int i=0;i<days.size();i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setBackgroundResource(drawable.tablerowstyle);

            TableRow.LayoutParams dateParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dateParams.topMargin = 2;
            dateParams.rightMargin = 2;

            TableRow.LayoutParams carbParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            carbParams.topMargin = 2;
            carbParams.rightMargin = 2;

            TableRow.LayoutParams overC = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            overC.topMargin = 2;
            overC.rightMargin = 2;

            TableRow.LayoutParams fatParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            fatParams.topMargin = 2;
            fatParams.rightMargin = 2;

            TableRow.LayoutParams overF = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            overF.topMargin = 2;
            overF.rightMargin = 2;

            TableRow.LayoutParams proteinParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            proteinParams.topMargin = 2;
            proteinParams.rightMargin = 2;

            TableRow.LayoutParams overP = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            overP.topMargin = 2;
            overP.rightMargin = 2;

            TextView date = new TextView(this);
            TextView carbs = new TextView(this);
            TextView overc = new TextView(this);
            TextView fat = new TextView(this);
            TextView overf = new TextView(this);
            TextView protein = new TextView(this);
            TextView overp = new TextView(this);

            date.setLayoutParams(dateParams);
            carbs.setLayoutParams(carbParams);
            overc.setLayoutParams(overC);
            fat.setLayoutParams(fatParams);
            overf.setLayoutParams(overF);
            protein.setLayoutParams(proteinParams);
            overp.setLayoutParams(overP);

            date.setText(days.get(i).getDateString());

            carbs.setText(Double.toString(days.get(i).getCarbs()));



            fat.setText(Double.toString(days.get(i).getFat()));

            protein.setText(Double.toString(days.get(i).getProtein()));

            if(user.getCarbs()-days.get(i).getCarbs()>=0){
                overc.setTextAppearance(getApplicationContext(), style.TextViewUnder);
                overc.setText(Double.toString(user.getCarbs()-days.get(i).getCarbs()));
            }
            else{
                overc.setTextAppearance(getApplicationContext(), style.TextViewOver);
                overc.setText(Double.toString(user.getCarbs() - days.get(i).getCarbs()));
            }
            if(user.getFat()-days.get(i).getFat()>=0){
                overf.setTextAppearance(getApplicationContext(), style.TextViewUnder);
                overf.setText(Double.toString(user.getFat()-days.get(i).getFat()));
            }
            else{
                overf.setTextAppearance(getApplicationContext(), style.TextViewOver);
                overf.setText(Double.toString(user.getFat()-days.get(i).getFat()));
            }
            if(user.getProtein()-days.get(i).getProtein()>=0){
                overp.setTextAppearance(getApplicationContext(), style.TextViewUnder);
                overp.setText(Double.toString(user.getProtein()-days.get(i).getProtein()));
            }
            else{
                overp.setTextAppearance(getApplicationContext(), style.TextViewOver);
                overp.setText(Double.toString(user.getProtein()-days.get(i).getProtein()));
            }
            tableRow.addView(date);
            tableRow.addView(carbs);
            tableRow.addView(overc);
            tableRow.addView(fat);
            tableRow.addView(overf);
            tableRow.addView(protein);
            tableRow.addView(overp);

            tableLayout.addView(tableRow);


        }
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
        setContentView(layout.activity_progress);

        DAYSFILE = getString(string.days_filename);
        USERFILE = getString(string.user_filename);
        inputFile = new File(getFilesDir(),DAYSFILE);
        inputFile1 = new File(getFilesDir(),USERFILE);

        try{

            inputFile = new File(getFilesDir(),DAYSFILE);
            FileInputStream fis = new FileInputStream(inputFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            days = (LinkedList) ois.readObject();
            ois.close();
            thisWorked("made it through days init");
            inputFile1 = new File(getFilesDir(),USERFILE);
            FileInputStream fis1 = new FileInputStream(inputFile1);
            ObjectInputStream ois1 = new ObjectInputStream(fis1);
            user = (UserProfile) ois1.readObject();
            ois1.close();

            makeTables();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_progress, menu);
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
