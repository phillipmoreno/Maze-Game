package com.example.kodablegame;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Stage3_Hard extends AppCompatActivity {

    boolean soundOn, isDropped1, isDropped2, isDropped3, isDropped4, isDropped5;
    Button arrow1, arrow2, arrow3, arrow4, arrow5, arrowDrop1, arrowDrop2, arrowDrop3, arrowDrop4, arrowDrop5, startButton;
    Dialog pause, stageComplete;
    ImageView fuzzBall;
    int stageScore = 0;
    MediaPlayer mp;
    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage3_hard);

        arrow1 = findViewById(R.id.stage3hardarrow1);
        arrow2 = findViewById(R.id.stage3hardarrow2);
        arrow3 = findViewById(R.id.stage3hardarrow3);
        arrow4 = findViewById(R.id.stage3hardarrow4);
        arrow5 = findViewById(R.id.stage3hardarrow5);

        arrowDrop1 = findViewById(R.id.stage3harddrop1);
        arrowDrop2 = findViewById(R.id.stage3harddrop2);
        arrowDrop3 = findViewById(R.id.stage3harddrop3);
        arrowDrop4 = findViewById(R.id.stage3harddrop4);
        arrowDrop5 = findViewById(R.id.stage3harddrop5);

        startButton = findViewById(R.id.button18);
        fuzzBall = findViewById(R.id.fuzzballStage3Hard);

        arrow1.setOnLongClickListener(longClickListener);
        arrow2.setOnLongClickListener(longClickListener);
        arrow3.setOnLongClickListener(longClickListener);
        arrow4.setOnLongClickListener(longClickListener);
        arrow5.setOnLongClickListener(longClickListener);

        arrowDrop1.setOnDragListener(odl);
        arrowDrop2.setOnDragListener(odl);
        arrowDrop3.setOnDragListener(odl);
        arrowDrop4.setOnDragListener(odl);
        arrowDrop5.setOnDragListener(odl);

        pause = new Dialog(this);
        stageComplete = new Dialog(this);

        pause.setContentView(R.layout.pause);
        stageComplete.setContentView(R.layout.all_stages_complete);
        score = stageComplete.findViewById(R.id.FinalScoreTV1);

        pause.setCancelable(false);
        stageComplete.setCancelable(false);

        pause.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        stageComplete.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        SharedPreferences sp = getSharedPreferences("Kodable", Context.MODE_PRIVATE);

        String fuzz = sp.getString("Fuzz", "");

        fuzzBall.setImageResource(getImageId(this, fuzz));
        mp = MediaPlayer.create(this, R.raw.budokai3_opening);
        mp.start();
        mp.setLooping(true);
    }

    public void startMotion(){
        ObjectAnimator direction1 = ObjectAnimator.ofFloat(fuzzBall, "translationY", convertDpToPx(this, 100f));
        direction1.setDuration(2000);

        ObjectAnimator rotate1 = ObjectAnimator.ofFloat(fuzzBall, "rotation", 0, 720f);
        rotate1.setDuration(2000);

        ObjectAnimator direction2 = ObjectAnimator.ofFloat(fuzzBall, "translationX", convertDpToPx(this, -180f));
        direction2.setDuration(2000);

        ObjectAnimator direction3 = ObjectAnimator.ofFloat(fuzzBall, "translationY", convertDpToPx(this,240f));
        direction3.setDuration(2000);

        ObjectAnimator direction4 = ObjectAnimator.ofFloat(fuzzBall, "translationX", convertDpToPx(this, 184f));
        direction4.setDuration(2000);

        ObjectAnimator direction5 = ObjectAnimator.ofFloat(fuzzBall, "translationY", convertDpToPx(this,340f));
        direction5.setDuration(2000);


        AnimatorSet as = new AnimatorSet();
        AnimatorSet motion1 = new AnimatorSet();
        AnimatorSet motion2 = new AnimatorSet();
        AnimatorSet motion3 = new AnimatorSet();
        AnimatorSet motion4 = new AnimatorSet();
        AnimatorSet motion5 = new AnimatorSet();

        motion1.playTogether(direction1, rotate1);
        motion2.playTogether(direction2, rotate1);
        motion3.playTogether(direction3, rotate1);
        motion4.playTogether(direction4, rotate1);
        motion5.playTogether(direction5, rotate1);

        as.playSequentially(motion1, motion2, motion3, motion4, motion5);
        as.start();
        as.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                stageComplete.show();

                SharedPreferences sp2 = getSharedPreferences("Kodable", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp2.edit();

                String difficulty = sp2.getString("Difficulty", "");
                int stage1Score = sp2.getInt("Stage1Score", 0);
                int stage2Score = sp2.getInt("Stage2Score", 0);
                int stage3Score = sp2.getInt("Stage3Score", 0);
                int maxScore = sp2.getInt("MaxPoints",0);

                int total = stage1Score + stage2Score + stage3Score;
                String totalString = Integer.toString(total);
                String maxScoreString = Integer.toString(maxScore);

                String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
                // StringBuilders used to hold the string containing the user's first name, date, and score
                StringBuilder sb1 = new StringBuilder("1. Difficulty: " + difficulty + "\n     " + "Score: " + totalString + "/" + maxScoreString + "\n     " + "Date: " + currentDate);
                StringBuilder sb2 = new StringBuilder("2. Difficulty: " + difficulty + "\n     " + "Score: " + totalString + "/" + maxScoreString + "\n     " + "Date: " + currentDate);
                StringBuilder sb3 = new StringBuilder("3. Difficulty: " + difficulty + "\n     " + "Score: " + totalString + "/" + maxScoreString + "\n     " + "Date: " + currentDate);
                StringBuilder sb4 = new StringBuilder("4. Difficulty: " + difficulty + "\n     " + "Score: " + totalString + "/" + maxScoreString + "\n     " + "Date: " + currentDate);

                int counter = sp2.getInt("counter", 0);

                editor.putInt("counter", ++counter);

                if(counter == 1){
                    editor.putString("score1", sb1.toString());
                }
                if(counter == 2){
                    editor.putString("score2", sb2.toString());
                }
                if(counter == 3){
                    editor.putString("score3", sb3.toString());
                }
                if(counter == 4){
                    editor.putString("score4", sb4.toString());
                    editor.putInt("counter", 0);
                }

                editor.apply();
            }
        });

    }

    public void buttonClick(View view){
        if(isDropped1 == true && isDropped2 == true && isDropped3 == true && isDropped4 == true && isDropped5 == true){
            startMotion();
            if(stageScore < 0){
                stageScore = 0;
            }
            startButton.setEnabled(false);
            score.setText("Woohoo! you scored " + stageScore +"/50 points");
            SharedPreferences sp = getSharedPreferences("Kodable",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("Stage3Score", stageScore);
            editor.apply();
        }else{
            Toast.makeText(Stage3_Hard.this, R.string.moveUnfinished, Toast.LENGTH_SHORT).show();
        }
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    @Override
    public void onBackPressed() {
        pause.show();
    }

    public void resumeGame(View view){
        pause.dismiss();
    }

    public void pauseGame(View view){
        pause.show();
    }

    public void toggleMusic(View view){
        if(soundOn == false){

            mp.pause();
            soundOn = true;
        }else {
            mp.start();
            soundOn = false;
        }
    }

    public void returnToMenu(View view){
        mp.stop();
        pause.dismiss();
        stageComplete.dismiss();
        Intent i = new Intent(this, ChildWelcome.class);
        startActivity(i);
        finish();
    }

    public void replayStage(View view){
        mp.stop();
        pause.dismiss();
        stageComplete.dismiss();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void logoutOfGame(View view){
        mp.stop();
        stageComplete.dismiss();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("","");
            View.DragShadowBuilder dsb = new View.DragShadowBuilder(v);
            v.startDrag(data, dsb, v, 0);
            return true;
        }
    };

    View.OnDragListener odl = new View.OnDragListener() {
        @Override
        public boolean onDrag(View view, DragEvent event) {
            View v = (View)event.getLocalState();
            switch(event.getAction()){
                case DragEvent.ACTION_DROP:
                    if((v.getId() == R.id.stage3hardarrow2 || v.getId() == R.id.stage3hardarrow3 || v.getId() == R.id.stage3hardarrow4) && view.getId() == R.id.stage3harddrop1){
                        view.setBackgroundResource(R.drawable.dropbox_down);
                        v.setVisibility(View.GONE);
                        isDropped1 = true;
                        stageScore += 10;
                        Toast.makeText(Stage3_Hard.this, R.string.moveCorrect, Toast.LENGTH_SHORT).show();
                        arrowDrop1.setOnDragListener(null);
                    }else if((v.getId() == R.id.stage3hardarrow5) && view.getId() == R.id.stage3harddrop2){
                        view.setBackgroundResource(R.drawable.dropbox_backwards);
                        v.setVisibility(View.GONE);
                        isDropped2 = true;
                        stageScore += 10;
                        Toast.makeText(Stage3_Hard.this, R.string.moveCorrect, Toast.LENGTH_SHORT).show();
                        arrowDrop2.setOnDragListener(null);
                    }else if((v.getId() == R.id.stage3hardarrow2 || v.getId() == R.id.stage3hardarrow3 || v.getId() == R.id.stage3hardarrow4) && view.getId() == R.id.stage3harddrop3) {
                        view.setBackgroundResource(R.drawable.dropbox_down);
                        v.setVisibility(View.GONE);
                        isDropped3 = true;
                        stageScore += 10;
                        Toast.makeText(Stage3_Hard.this, R.string.moveCorrect, Toast.LENGTH_SHORT).show();
                        arrowDrop3.setOnDragListener(null);
                    }else if((v.getId() == R.id.stage3hardarrow1) && view.getId() == R.id.stage3harddrop4) {
                        view.setBackgroundResource(R.drawable.dropbox_forward);
                        v.setVisibility(View.GONE);
                        isDropped4 = true;
                        stageScore += 10;
                        Toast.makeText(Stage3_Hard.this, R.string.moveCorrect, Toast.LENGTH_SHORT).show();
                        arrowDrop4.setOnDragListener(null);
                    }else if((v.getId() == R.id.stage3hardarrow2 || v.getId() == R.id.stage3hardarrow3 || v.getId() == R.id.stage3hardarrow4) && view.getId() == R.id.stage3harddrop5) {
                        view.setBackgroundResource(R.drawable.dropbox_down);
                        v.setVisibility(View.GONE);
                        isDropped5 = true;
                        stageScore += 10;
                        Toast.makeText(Stage3_Hard.this, R.string.moveCorrect, Toast.LENGTH_SHORT).show();
                        arrowDrop5.setOnDragListener(null);
                    }else{
                        Toast.makeText(Stage3_Hard.this, R.string.moveIncorrect, Toast.LENGTH_SHORT).show();
                        stageScore -= 10;
                    }
                case DragEvent.ACTION_DRAG_ENDED:
                default:
                    break;
            }
            return true;
        }
    };

    public float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
