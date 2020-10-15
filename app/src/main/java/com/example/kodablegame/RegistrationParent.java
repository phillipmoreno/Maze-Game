package com.example.kodablegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegistrationParent extends AppCompatActivity {

    // TextView object is declared
    TextView firstNameTV;

    // TextView object is declared
    TextView lastNameTV;

    // TextView object is declared
    TextView datOfBirthTV;

    // TextView object is declared
    TextView emailTV;

    // TextView object is declared
    TextView passwordTV;

    // Password pattern is created using Regular Expressions to be used in password validation
    // Method
    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //1 digit minimum
                    "(?=.*[a-z])" +         //1 lowercase letter minimum
                    "(?=.*[A-Z])" +         //1 lowercase letter maximum
                    "(?=.*[@#$%^&+=])" +    //1 special character minimum
                    "(?=\\S+$)" +           //no spaces allowed
                    ".{7,}" +               //7 character minimum
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_parent);

        // firstNameTV is linked to resource ID in xml activity file
        firstNameTV = findViewById(R.id.parentFirstName);

        // lastNameTV is linked to resource ID in xml activity file
        lastNameTV = findViewById(R.id.lastName);

        // dateOfBirthTV is linked to resource ID in xml activity file
        datOfBirthTV = findViewById(R.id.parentDateOfBirth);

        // emailTV is linked to resource ID in xml activity file
        emailTV = findViewById(R.id.parentEmail);

        // passwordTV is linked to resource ID in xml activity file
        passwordTV = findViewById(R.id.parentPassword);
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


    //****************************************************
    // Method: validateEmail
    //
    // Purpose: Method used to validate the email input
    // provided by the user. The email must meet several
    // requirements to return a true value.
    //****************************************************
    public boolean validateEmail(){
        String email = emailTV.getText().toString().trim();

        if(email.isEmpty()){
            emailTV.setError("Missing email field");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailTV.setError("Please enter a valid email address");
            return false;
        }else{
            emailTV.setError(null);
            return true;
        }
    }

    //****************************************************
    // Method: validatePassword
    //
    // Purpose: Method used to validate the password input
    // provided by the user. Password must meet several
    // requirements to return a true value.
    //****************************************************
    public boolean validatePassword(){
        String password = passwordTV.getText().toString().trim();

        if(password.isEmpty()){
            passwordTV.setError("Missing password field");
            return false;
        }else if(!PASSWORD_PATTERN.matcher(password).matches()){
            passwordTV.setError("Password must: " +
                    "\n- Be at least seven characters long" +
                    "\n- Have at least one capital letter" +
                    "\n- Have at least one lowercase letter" +
                    "\n- Have at least one number" +
                    "\n- Have at least one special character");
            return false;
        }else{
            passwordTV.setError(null);
            return true;
        }
    }


    public void nextPage(View view){
        // First name validation method is called
        validateFirstName();
        // Last name validation method is called
        validateLastName();
        // Date of birth validation method is called
        validateDOB();
        // User email validation method is called
        validateEmail();
        // User password validation method is called
        validatePassword();

        // All field validations must be met for registration to be completed
        if(validateFirstName() == true && validatePassword() == true && validateEmail() == true && validateLastName() == true && validateDOB() == true){
            // SharedPreferences object is created and file UserInformation is created as well
            SharedPreferences sp = getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            // getText is used to write the user's first name in the shared preferences file
            editor.putString("parentFirstName", firstNameTV.getText().toString());
            // getText is used to write the user's last name in the shared preferences file
            editor.putString("parentLastName", lastNameTV.getText().toString());
            // getText is used to write the user's date of birth in the shared preferences file
            editor.putString("parentDateOfBirth", datOfBirthTV.getText().toString());
            // getText is used to write the user's email address in the shared preferences file
            editor.putString("parentEmailAddress", emailTV.getText().toString());
            // getText is used to write the user's password in the shared preferences file
            editor.putString("parentPassword", passwordTV.getText().toString());
            // Changes made to shared preferences is applied
            editor.apply();

            // Message is displayed to notify the user that the Registration was a success
            Toast.makeText(RegistrationParent.this, R.string.regSuccess, Toast.LENGTH_SHORT).show();

            // User is brought back to login page once registration is successful
            Intent intent = new Intent(this, RegistrationChild.class);
            startActivity(intent);
        }
    }
}
