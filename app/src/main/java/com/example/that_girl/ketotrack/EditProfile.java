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

public class EditProfile extends AppCompatActivity {
    //Go to profile activity
    public void backToProfile(){
        Intent intent = new Intent(this, Profile.class);
        EditProfile.this.startActivity(intent);
    }
    //Pull data from fields, save/update file, and route back to profile
    //TODO : Add some input validation for the fields so the profile doesn't display all retarded even if the user is acting like it
    public void saveData(View view){
        String fName,lName;
        double f,p,c;
        try {
            //Pull data from the fields
            EditText field = (EditText)findViewById(R.id.firstName);
            fName = field.getText().toString();
            field = (EditText)findViewById(R.id.lastName);
            lName = field.getText().toString();
            field = (EditText)findViewById(R.id.fat);
            f = Double.parseDouble(field.getText().toString());
            field = (EditText)findViewById(R.id.protein);
            p = Double.parseDouble(field.getText().toString());
            field = (EditText)findViewById(R.id.carbs);
            c = Double.parseDouble(field.getText().toString());

            //create profile object to save
            UserProfile newProfile = new UserProfile(fName,lName,f,p,c);

            //Open the profile file
            String FILENAME = getString(R.string.user_filename);

            File outputFile = new File (getFilesDir(),FILENAME);
            FileOutputStream fos = new FileOutputStream(outputFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(newProfile);
            oos.close();

            //Display message-saved toast
            Context context = getApplicationContext();
            CharSequence text = "Profile Saved.";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            //Go back to profile activity
            backToProfile();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
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
