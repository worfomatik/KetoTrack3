package com.example.that_girl.ketotrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

//TODO: populate the edit profile fields with current profile data if it exists

public class Profile extends AppCompatActivity {
    public void backToMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        Profile.this.startActivity(intent);
    }
    public void editProfile(View view){
        Intent intent = new Intent(this, EditProfile.class);
        Profile.this.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        UserProfile userProfile2=new UserProfile();
        TextView textView = (TextView) findViewById(R.id.textView2);
        String FILENAME = getString(R.string.user_filename);
        File outputFile = new File (getFilesDir(),FILENAME);

        if(outputFile.exists()) {
            try {

                FileInputStream fis = new FileInputStream(outputFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                UserProfile u1 = (UserProfile) ois.readObject();
                userProfile2.setfName(u1.getfName());
                userProfile2.setlName(u1.getlName());
                userProfile2.setFat(u1.getFat());

                userProfile2.setProtein(u1.getProtein());
                userProfile2.setCarbs(u1.getCarbs());


                ois.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            textView.setText(userProfile2.toString());
        }
        else{
            textView.setText("No Profile Data Found, press Edit Profile!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
