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

public class Stage1_Hard extends AppCompatActivity {

    boolean soundOn, isDropped1, isDropped2, isDropped3, isDropped4, isDropped5;
    Button arrow1, arrow2, arrow3, arrow4, arrow5, arrowDrop1, arrowDrop2, arrowDrop3, arrowDrop4, arrowDrop5, startButton;
    Dialog popup, pause, stageComplete;
    ImageView fuzzBall;
    int stageScore = 0;
    MediaPlayer mp;
    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage1_hard);

        arrow1 = findViewById(R.id.stage1hardarrow1);
        arrow2 = findViewById(R.id.stage1hardarrow2);
        arrow3 = findViewById(R.id.stage1hardarrow3);
        arrow4 = findViewById(R.id.stage1hardarrow4);
        arrow5 = findViewById(R.id.stage1hardarrow5);

        arrowDrop1 = findViewById(R.id.stage1harddrop1);
        arrowDrop2 = findViewById(R.id.stage1harddrop2);
        arrowDrop3 = findViewById(R.id.stage1harddrop3);
        arrowDrop4 = findViewById(R.id.stage1harddrop4);
        arrowDrop5 = findViewById(R.id.stage1harddrop5);

        startButton = findViewById(R.id.button18);
        fuzzBall = findViewById(R.id.fuzzballStage1Hard);

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

        popup.show();
        SharedPreferences sp = getSharedPreferences("Kodable", Context.MODE_PRIVATE);

        String fuzz = sp.getString("Fuzz", "");

        fuzzBall.setImageResource(getImageId(this, fuzz));
        mp = MediaPlayer.create(this, R.raw.burst_limit_opening);
        mp.start();
        mp.setLooping(true);
    }

    public void startMotion(){
        ObjectAnimator direction1 = ObjectAnimator.ofFloat(fuzzBall, "translationX", convertDpToPx(this, 274f));
        direction1.setDuration(2000);

        ObjectAnimator rotate1 = ObjectAnimator.ofFloat(fuzzBall, "rotation", 0, 720);
        rotate1.setDuration(2000);

        ObjectAnimator direction2 = ObjectAnimator.ofFloat(fuzzBall, "translationY", convertDpToPx(this, 100f));
        direction2.setDuration(2000);

        ObjectAnimator direction3 = ObjectAnimator.ofFloat(fuzzBall, "translationX", convertDpToPx(this, 183f));
        direction3.setDuration(2000);

        ObjectAnimator direction4 = ObjectAnimator.ofFloat(fuzzBall, "translationY", convertDpToPx(this, 245f));
        direction4.setDuration(2000);

        ObjectAnimator direction5 = ObjectAnimator.ofFloat(fuzzBall, "translationX", convertDpToPx(this, 635f));
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
            editor.putInt("Stage1Score", stageScore);
            editor.apply();
        }else{
            Toast.makeText(Stage1_Hard.this, R.string.moveUnfinished, Toast.LENGTH_SHORT).show();
        }
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    @Override
    public void onBackPressed() {
        pause.show();
    }

    public void closePopUp(View view){
        popup.dismiss();
    }

    public void returnToMenu(View view){
        Intent i = new Intent(this, ChildWelcome.class);
        startActivity(i);
        mp.stop();
        pause.dismiss();
        stageComplete.dismiss();
        finish();
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

    public void replayStage(View view){
        mp.stop();
        Intent intent = getIntent();
        pause.dismiss();
        stageComplete.dismiss();
        finish();
        startActivity(intent);
    }

    public void nextLevel(View view){
        Intent i = new Intent(this, Stage2_Hard.class);
        startActivity(i);
        stageComplete.dismiss();
        finish();
        mp.stop();
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
                    if((v.getId() == R.id.stage1hardarrow2 || v.getId() == R.id.stage1hardarrow4) && view.getId() == R.id.stage1harddrop1){
                        view.setBackgroundResource(R.drawable.dropbox_forward);
                        v.setVisibility(View.GONE);
                        isDropped1 = true;
                        stageScore += 10;
                        Toast.makeText(Stage1_Hard.this, R.string.moveCorrect, Toast.LENGTH_SHORT).show();
                        arrowDrop1.setOnDragListener(null);
                    }else if((v.getId() == R.id.stage1hardarrow3 || v.getId() == R.id.stage1hardarrow5) && view.getId() == R.id.stage1harddrop2){
                        view.setBackgroundResource(R.drawable.dropbox_down);
                        v.setVisibility(View.GONE);
                        isDropped2 = true;
                        stageScore += 10;
                        Toast.makeText(Stage1_Hard.this, R.string.moveCorrect, Toast.LENGTH_SHORT).show();
                        arrowDrop2.setOnDragListener(null);
                    }else if(v.getId() == R.id.stage1hardarrow1 && view.getId() == R.id.stage1harddrop3) {
                        view.setBackgroundResource(R.drawable.dropbox_backwards);
                        v.setVisibility(View.GONE);
                        isDropped3 = true;
                        stageScore += 10;
                        Toast.makeText(Stage1_Hard.this, R.string.moveCorrect, Toast.LENGTH_SHORT).show();
                        arrowDrop3.setOnDragListener(null);
                    }else if((v.getId() == R.id.stage1hardarrow3 || v.getId() == R.id.stage1hardarrow5) && view.getId() == R.id.stage1harddrop4) {
                        view.setBackgroundResource(R.drawable.dropbox_down);
                        v.setVisibility(View.GONE);
                        isDropped4 = true;
                        stageScore += 10;
                        Toast.makeText(Stage1_Hard.this, R.string.moveCorrect, Toast.LENGTH_SHORT).show();
                        arrowDrop4.setOnDragListener(null);
                    }else if((v.getId() == R.id.stage1hardarrow2 || v.getId() == R.id.stage1hardarrow4) && view.getId() == R.id.stage1harddrop5) {
                        view.setBackgroundResource(R.drawable.dropbox_forward);
                        v.setVisibility(View.GONE);
                        isDropped5 = true;
                        stageScore += 10;
                        Toast.makeText(Stage1_Hard.this, R.string.moveCorrect, Toast.LENGTH_SHORT).show();
                        arrowDrop3.setOnDragListener(null);
                    }else{
                        Toast.makeText(Stage1_Hard.this, R.string.moveIncorrect, Toast.LENGTH_SHORT).show();
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
