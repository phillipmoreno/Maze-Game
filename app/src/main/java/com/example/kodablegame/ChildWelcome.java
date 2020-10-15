package com.example.kodablegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.kodablegame.R.drawable.glow_effect;

public class ChildWelcome extends AppCompatActivity {

    MediaPlayer mp1;
    MediaPlayer mp2;
    TextView welcome;
    Button easy;
    Button hard;

    ImageView char1;
    ImageView char2;
    ImageView char3;

    boolean soundOn, characterSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_welcome);
        welcome = findViewById(R.id.childWelcome);
        mp1 = MediaPlayer.create(this,R.raw.announcer);
        mp2 = MediaPlayer.create(this,R.raw.choose_character);

        easy = findViewById(R.id.buttonEasy);
        hard = findViewById(R.id.buttonHard);
        char1 = findViewById(R.id.char1);
        char2 = findViewById(R.id.char2);
        char3 = findViewById(R.id.char3);

        SharedPreferences sp = getSharedPreferences("UserInformation", Context.MODE_PRIVATE);

        String firstName = sp.getString("childFirstName", "");

        welcome.setText("Welcome, " + firstName + "!");
        mp1.start();
        mp2.start();
        mp2.setLooping(true);
    }

    //****************************************************
    // Method: startGameEasy
    //
    // Purpose: onClick event that allows the user
    // to begin the game activity on beginner difficulty.
    //****************************************************
    public void startGameEasy(View view){
        if(characterSelected == true) {
            SharedPreferences sp = getSharedPreferences("Kodable", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Difficulty", "Beginner");
            editor.putInt("MaxPoints", 110);
            editor.apply();
            mp1.stop();
            mp2.stop();
            Intent i = new Intent(this, Stage1_Easy.class);
            startActivity(i);
            finish();
        }else{
            Toast.makeText(ChildWelcome.this, R.string.chooseCharacter, Toast.LENGTH_SHORT).show();
        }
    }

    //****************************************************
    // Method: startGameHard
    //
    // Purpose: onClick event that allows the user
    // to begin the game activity on experienced difficulty.
    //****************************************************
    public void startGameHard(View view){
        if(characterSelected == true) {
            SharedPreferences sp = getSharedPreferences("Kodable", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Difficulty", "Experienced");
            editor.putInt("MaxPoints", 150);
            editor.apply();
            mp1.stop();
            mp2.stop();
            Intent i = new Intent(this, Stage1_Hard.class);
            startActivity(i);
            finish();
        }else{
            Toast.makeText(ChildWelcome.this, R.string.chooseCharacter, Toast.LENGTH_SHORT).show();
        }
    }

    //****************************************************
    // Method: chooseCharacter1
    //
    // Purpose: onClick event that allows the user
    // to pick a certain fuzz character and use them
    // throughout the game.
    //****************************************************
    public void chooseCharacter1(View view){
        SharedPreferences sp = getSharedPreferences("Kodable",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Fuzz", "kodable_fuzz1");
        editor.apply();
        MediaPlayer mp = MediaPlayer.create(this,R.raw.game_sound_effect_1);
        mp.start();
        char1.setBackgroundResource(glow_effect);
        char2.setBackground(null);
        char3.setBackground(null);
        characterSelected = true;
    }

    //****************************************************
    // Method: chooseCharacter2
    //
    // Purpose: onClick event that allows the user
    // to pick a certain fuzz character and use them
    // throughout the game.
    //****************************************************
    public void chooseCharacter2(View view){
        SharedPreferences sp = getSharedPreferences("Kodable",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Fuzz", "kodable_fuzz2");
        editor.apply();
        MediaPlayer mp = MediaPlayer.create(this,R.raw.game_sound_effect_1);
        mp.start();
        char2.setBackgroundResource(glow_effect);
        char1.setBackground(null);
        char3.setBackground(null);
        characterSelected = true;
    }

    //****************************************************
    // Method: chooseCharacter3
    //
    // Purpose: onClick event that allows the user
    // to pick a certain fuzz character and use them
    // throughout the game.
    //****************************************************
    public void chooseCharacter3(View view){
        SharedPreferences sp = getSharedPreferences("Kodable",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Fuzz", "kodable_fuzz3");
        editor.apply();
        MediaPlayer mp = MediaPlayer.create(this,R.raw.game_sound_effect_1);
        mp.start();
        char3.setBackgroundResource(glow_effect);
        char1.setBackground(null);
        char2.setBackground(null);
        characterSelected = true;
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(ChildWelcome.this, R.string.cantGoBack, Toast.LENGTH_SHORT).show();
    }

    //****************************************************
    // Method: logOut
    //
    // Purpose: Button onClick event that allows the user
    // to exit the current activity and log out of their
    // account.
    //****************************************************
    public void logOut(View view){
        mp1.stop();
        mp2.stop();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
        Toast.makeText(ChildWelcome.this, R.string.logout, Toast.LENGTH_SHORT).show();
    }

    //****************************************************
    // Method: toggleMusic
    //
    // Purpose: onClick event that allows the user to turn
    // the music on or off for the current activity.
    //****************************************************
    public void toggleMusic(View view){
        if(soundOn == false){

            mp2.pause();
            soundOn = true;
        }else {
            mp2.start();
            soundOn = false;
        }
    }
}

