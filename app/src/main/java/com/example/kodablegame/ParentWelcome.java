package com.example.kodablegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ParentWelcome extends AppCompatActivity {

    TextView welcome, status, scoreHistory1, scoreHistory2, scoreHistory3, scoreHistory4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_welcome);

        welcome = findViewById(R.id.parentWelcome);
        status = findViewById(R.id.childStatus);
        scoreHistory1 = findViewById(R.id.childScore1);
        scoreHistory2 = findViewById(R.id.childScore2);
        scoreHistory3 = findViewById(R.id.childScore3);
        scoreHistory4 = findViewById(R.id.childScore4);

        SharedPreferences sp = getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
        SharedPreferences sp2 = getSharedPreferences("Kodable", Context.MODE_PRIVATE);

        String parentFirstName = sp.getString("parentFirstName", "");
        String childFirstName = sp.getString("childFirstName", "");

        // Parent is greeted and the name of their child is displayed along with their scores
        welcome.setText("Welcome, " + parentFirstName + "!");
        status.setText("Here are " + childFirstName + "'s recent Kodable scores");

        // Updated scores are retrieved from a Shared Preferences file
        String score1 = sp2.getString("score1", "");
        String score2 = sp2.getString("score2", "");
        String score3 = sp2.getString("score3", "");
        String score4 = sp2.getString("score4", "");

        // Textviews are set to display up to three previously saved scores
        scoreHistory1.setText(score1);
        scoreHistory2.setText(score2);
        scoreHistory3.setText(score3);
        scoreHistory4.setText(score4);

    }

    //****************************************************
    // Method: logOut
    //
    // Purpose: Button onClick event that allows the user
    // to exit the current activity and log out of their
    // account.
    //****************************************************
    public void logOut(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
        Toast.makeText(ParentWelcome.this, R.string.logout, Toast.LENGTH_SHORT).show();
    }
}
