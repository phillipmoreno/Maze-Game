package com.example.kodablegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationChild extends AppCompatActivity {

    // TextView object is declared
    TextView firstNameTV;

    // TextView object is declared
    TextView lastNameTV;

    // TextView object is declared
    TextView datOfBirthTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_child);

        // firstNameTV is linked to resource ID in xml activity file
        firstNameTV = findViewById(R.id.childFirstName);

        // lastNameTV is linked to resource ID in xml activity file
        lastNameTV = findViewById(R.id.childLastName);

        // dateOfBirthTV is linked to resource ID in xml activity file
        datOfBirthTV = findViewById(R.id.childDateOfBirth);
    }

    //****************************************************
    // Method: validateFirstName
    //
    // Purpose: Method used to validate the first name
    // input provided by the user. The user's first name
    // must meet a few requirements to return a true
    // value.
    //****************************************************
    public boolean validateFirstName(){
        if(firstNameTV.getText().toString().equals("")){
            firstNameTV.setError("Missing first name field");
            return false;
        }else if(firstNameTV.getText().toString().length() <= 3 && firstNameTV.getText().toString().length() > 0) {
            firstNameTV.setError("First name must be at least 3 characters long");
            return false;
        }else if(firstNameTV.getText().toString().length() > 30) {
            firstNameTV.setError("First name must be no longer than 30 characters");
            return false;
        }else {
            firstNameTV.setError(null);
            return true;
        }
    }

    //****************************************************
    // Method: validateLastName
    //
    // Purpose: Method used to validate the last name
    // input provided by the user. The user's last name
    // must meet requirement to return a true
    // value.
    //****************************************************
    public boolean validateLastName(){
        if(lastNameTV.getText().toString().equals("")){
            lastNameTV.setError("Missing last name field");
            return false;
        }else{
            lastNameTV.setError(null);
            return true;
        }
    }

    //****************************************************
    // Method: validateDOB
    //
    // Purpose: Method used to validate the date of birth
    // input provided by the user. The user's date of
    // birth must meet requirement to return a true value.
    //****************************************************
    public boolean validateDOB(){
        if(datOfBirthTV.getText().toString().equals("")){
            datOfBirthTV.setError("Missing date of birth field");
            return false;
        }else{
            datOfBirthTV.setError(null);
            return true;
        }
    }

    public void completeRegistration(View view){
        // First name validation method is called
        validateFirstName();
        // Last name validation method is called
        validateLastName();
        // Date of birth validation method is called
        validateDOB();

        // All field validations must be met for registration to be completed
        if(validateFirstName() == true  && validateLastName() == true && validateDOB() == true){
            // SharedPreferences object is created and file UserInformation is created as well
            SharedPreferences sp = getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            // getText is used to write the user's first name in the shared preferences file
            editor.putString("childFirstName", firstNameTV.getText().toString());
            // getText is used to write the user's last name in the shared preferences file
            editor.putString("childLastName", lastNameTV.getText().toString());
            // getText is used to write the user's date of birth in the shared preferences file
            editor.putString("childDateOfBirth", datOfBirthTV.getText().toString());
            editor.apply();

            // Message is displayed to notify the user that the Registration was a success
            Toast.makeText(RegistrationChild.this, R.string.regSuccess, Toast.LENGTH_SHORT).show();

            // User is brought back to login page once registration is successful
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
