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

public class Stage1_Easy extends AppCompatActivity {

    boolean soundOn, isDropped1, isDropped2, isDropped3;
    Button arrow1, arrow2, arrow3, arrowDrop1, arrowDrop2, arrowDrop3, startButton;
    Dialog popup, pause, stageComplete;
    ImageView fuzzBall;
    int stageScore = 0;
    MediaPlayer mp;
    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage1_easy);

        arrow1 = findViewById(R.id.stage1easyarrow1);
        arrow2 = findViewById(R.id.stage1easyarrow2);
        arrow3 = findViewById(R.id.stage1easyarrow3);

        arrowDrop1 = findViewById(R.id.stage1easydrop1);
        arrowDrop2 = findViewById(R.id.stage1easydrop2);
        arrowDrop3 = findViewById(R.id.stage1easydrop3);

        startButton = findViewById(R.id.button);
        fuzzBall = findViewById(R.id.fuzzCharacter);

        arrow1.setOnLongClickListener(longClickListener);
        arrow2.setOnLongClickListener(longClickListener);
        arrow3.setOnLongClickListener(longClickListener);

        arrowDrop1.setOnDragListener(odl);
        arrowDrop2.setOnDragListener(odl);
        arrowDrop3.setOnDragListener(odl);

        popup = new Dialog(this);
        pause = new Dialog(this);
        stageComplete = new Dialog(this);

        popup.setContentView(R.layout.stage_easy_popup);
        pause.setContentView(R.layout.pause);
        stageComplete.setContentView(R.layout.stage_complete);
        score = stageComplete.findViewById(R.id.scoreCompleteTV);

        popup.setCancelable(false);
        pause.setCancelable(false);
        stageComplete.setCancelable(false);

        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pause.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        stageComplete.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Initial Objective dialog is displayed
        popup.show();
        SharedPreferences sp = getSharedPreferences("Kodable", Context.MODE_PRIVATE);

        String fuzz = sp.getString("Fuzz", "");

        // Character selected in ChildWelcome Activity is set.
        fuzzBall.setImageResource(getImageId(this, fuzz));
        mp = MediaPlayer.create(this, R.raw.green_hill_zone);
        mp.start();
        mp.setLooping(true);
    }

    //****************************************************
    // Method: startMotion
    //
    // Purpose: This function correctly sets up the object
    // animation path using x and y coordinates as well as
    // enabling a rotate animation.
    //****************************************************
    public void startMotion(){
        ObjectAnimator direction1 = ObjectAnimator.ofFloat(fuzzBall, "translationX", convertDpToPx(this,271f));
        direction1.setDuration(2000);

        ObjectAnimator rotate1 = ObjectAnimator.ofFloat(fuzzBall, "rotation", 0f, 720f);
        rotate1.setDuration(2000);

        ObjectAnimator direction2 = ObjectAnimator.ofFloat(fuzzBall, "translationY", convertDpToPx(this,245f));
        direction2.setDuration(2000);

        ObjectAnimator direction3 = ObjectAnimator.ofFloat(fuzzBall, "translationX", convertDpToPx(this,630f));
        direction3.setDuration(2000);


        AnimatorSet as = new AnimatorSet();
        AnimatorSet motion1 = new AnimatorSet();
        AnimatorSet motion2 = new AnimatorSet();
        AnimatorSet motion3 = new AnimatorSet();

        motion1.playTogether(direction1, rotate1);
        motion2.playTogether(direction2, rotate1);
        motion3.playTogether(direction3, rotate1);

        as.playSequentially(motion1, motion2, motion3);
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
            }
        });

    }

    //****************************************************
    // Method: buttonClick
    //
    // Purpose: onClick event that allows the user to
    // commence the animation if it meets all the required
    // conditions. This method also adjusts the score to
    // prevent a negative number.
    //****************************************************
    public void buttonClick(View view){
        if(isDropped1 == true && isDropped2 == true && isDropped3 == true){
            startMotion();
            if(stageScore < 0){
                stageScore = 0;
            }
            startButton.setEnabled(false);
            score.setText("Woohoo! you scored " + stageScore +"/30 points");
            SharedPreferences sp = getSharedPreferences("Kodable",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("Stage1Score", stageScore);
            editor.apply();
        }else{
            Toast.makeText(Stage1_Easy.this, R.string.moveUnfinished, Toast.LENGTH_SHORT).show();
        }
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    @Override
    public void onBackPressed() {
        pause.show();
    }

    //****************************************************
    // Method: closePopUp
    //
    // Purpose: onClick event that allows the user to exit
    // the initial objective dialog that is only shown on
    // stage 1.
    //****************************************************
    public void closePopUp(View view){
        popup.dismiss();
    }

    //****************************************************
    // Method: returnToMenu
    //
    // Purpose: onClick event that allows the user to return
    // to the ChildWelcome activity where the user can choose
    // a different difficulty or choose a different character.
    //****************************************************
    public void returnToMenu(View view){
        Intent i = new Intent(this, ChildWelcome.class);
        startActivity(i);
        mp.stop();
        pause.dismiss();
        stageComplete.dismiss();
        finish();
    }

    //****************************************************
    // Method: resumeGame
    //
    // Purpose: onClick event that allows the user to exit
    // the pause game dialog when they decide to resume
    // playing.
    //****************************************************
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

    //****************************************************
    // Method: replayStage
    //
    // Purpose: onClick event that allows the user to replay
    // the current stage activity on both the stageComplete
    // dialog and the pause dialog.
    //****************************************************
    public void replayStage(View view){
        mp.stop();
        Intent intent = getIntent();
        pause.dismiss();
        stageComplete.dismiss();
        finish();
        startActivity(intent);
    }

    //****************************************************
    // Method: nextLevel
    //
    // Purpose: onClick event that allows the user to
    // advance to the next level once the stage is completed.
    //****************************************************
    public void nextLevel(View view){
        Intent i = new Intent(this, Stage2_Easy.class);
        startActivity(i);
        stageComplete.dismiss();
        mp.stop();
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
                    if((v.getId() == R.id.stage1easyarrow2 || v.getId() == R.id.stage1easyarrow3) && view.getId() == R.id.stage1easydrop1){
                        view.setBackgroundResource(R.drawable.dropbox_forward);
                        v.setVisibility(View.GONE);
                        isDropped1 = true;
                        stageScore += 10;
                        Toast.makeText(Stage1_Easy.this, R.string.moveCorrect, Toast.LENGTH_SHORT).show();
                        arrowDrop1.setOnDragListener(null);
                    }else if(v.getId() == R.id.stage1easyarrow1 && view.getId() == R.id.stage1easydrop2){
                        view.setBackgroundResource(R.drawable.dropbox_down);
                        v.setVisibility(View.GONE);
                        isDropped2 = true;
                        stageScore += 10;
                        Toast.makeText(Stage1_Easy.this, R.string.moveCorrect, Toast.LENGTH_SHORT).show();
                        arrowDrop2.setOnDragListener(null);
                    }else if((v.getId() == R.id.stage1easyarrow2 || v.getId() == R.id.stage1easyarrow3) && view.getId() == R.id.stage1easydrop3) {
                        view.setBackgroundResource(R.drawable.dropbox_forward);
                        v.setVisibility(View.GONE);
                        isDropped3 = true;
                        stageScore += 10;
                        Toast.makeText(Stage1_Easy.this, R.string.moveCorrect, Toast.LENGTH_SHORT).show();
                        arrowDrop3.setOnDragListener(null);
                    }else{
                        Toast.makeText(Stage1_Easy.this, R.string.moveIncorrect, Toast.LENGTH_SHORT).show();
                        stageScore -= 10;
                    }
                case DragEvent.ACTION_DRAG_ENDED:
                    default:
                        break;
            }
            return true;
        }
    };

    //****************************************************
    // Method: convertDpToPx
    //
    // Purpose: Converts density independent pixels into
    // pixels. Used in the startMotion method to correctly
    // scale the object path animation on any device.
    //****************************************************
    public float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
