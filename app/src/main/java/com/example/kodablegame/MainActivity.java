package com.example.kodablegame;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView enterEmail;
    TextView enterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // enterEmail is linked to resource ID in xml activity file
        enterEmail = findViewById(R.id.enterEmail);

        // enterPassword is linked to resource ID in xml activity file
        enterPassword = findViewById(R.id.enterPassword);
    }

    //****************************************************
    // Method: verifyLoginChild
    //
    // Purpose: To verify whether the user has an account
    // or not by validating the user email input and user
    // password input. Since kids tend to not remember
    // user names or passwords, they share the same account
    // credentials as their parent. This event is fired
    // when you tap on "Play Now" and launches the Child
    // Welcome activity.
    //****************************************************
    public void verifyLoginChild(View view){
        SharedPreferences sp = getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
        //SharedPreferences sp2 = getSharedPreferences("HighScores", Context.MODE_PRIVATE);

        String email = sp.getString("parentEmailAddress", "");
        String pw = sp.getString("parentPassword", "");

        if(email.equals(enterEmail.getText().toString()) && pw.equals(enterPassword.getText().toString())){
            Toast.makeText(MainActivity.this, R.string.correct, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, ChildWelcome.class);
            startActivity(i);
            finish();
        }else{
            Toast.makeText(MainActivity.this, R.string.incorrect, Toast.LENGTH_SHORT).show();
        }
    }


    //****************************************************
    // Method: verifyLoginParent
    //
    // Purpose: To verify whether the user has an account
    // or not by validating the user email input and user
    // password input. This event is fired when you tap on
    // "Parent Login" and launches the Parent Welcome
    // activity.
    //****************************************************
    public void verifyLoginParent(View view){
        SharedPreferences sp = getSharedPreferences("UserInformation", Context.MODE_PRIVATE);

        String email = sp.getString("parentEmailAddress", "");
        String pw = sp.getString("parentPassword", "");

        if(email.equals(enterEmail.getText().toString()) && pw.equals(enterPassword.getText().toString())){
            Toast.makeText(MainActivity.this, R.string.correct, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, ParentWelcome.class);
            startActivity(i);
            finish();
        }else{
            Toast.makeText(MainActivity.this, R.string.incorrect, Toast.LENGTH_SHORT).show();
        }
    }

    //****************************************************
    // Method: openRegistration
    //
    // Purpose: Contains an Intent used to take user to the
    // Registration Activity class.
    //****************************************************
    public void openRegistration(View view){
        Intent intent = new Intent(this, RegistrationParent.class);
        startActivity(intent);
    }
}
