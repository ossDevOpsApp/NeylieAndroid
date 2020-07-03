package com.slt.devops.neylie.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.slt.devops.neylie.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ProgressBar splashProgress;
    int SPLASH_TIME = 2000; //This is 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        String [] split = currentDateandTime.split(":");
        String aa;
        if( Integer.parseInt(split[0]) <12 &&  Integer.parseInt(split[0]) > 5){

            aa= "Good Morning!";
        }else if( Integer.parseInt(split[0]) <17 ){

            aa= "Good Afternoon!";
        }else{
            aa= "Good Evening!";
        }


        TextView txt1 = (TextView)findViewById(R.id.text1);
        txt1.setText(aa);

        //This is additional feature, used to run a progress bar
        splashProgress = findViewById(R.id.splashProgress);
        playProgress();

        //Code to start timer and take action after the timer ends
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do any action here. Now we are moving to next page
                Intent intent = new Intent( MainActivity.this, LoginActivity.class);
                startActivity(intent);

                //This 'finish()' is for exiting the app when back button pressed from Home page which is ActivityHome
                finish();

            }
        }, SPLASH_TIME);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void playProgress() {
        ObjectAnimator.ofInt(splashProgress, "progress", 100)
                .setDuration(5000)
                .start();
    }
}