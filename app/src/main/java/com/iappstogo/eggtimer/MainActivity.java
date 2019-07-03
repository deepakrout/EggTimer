package com.iappstogo.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    SeekBar timerSeekBar;
    boolean counterIsActive = false;
    Button goButton;
    CountDownTimer countDownTimer;

    public void btnClick(View btnView) {
        Log.i("Button Pressed", "Nice!");

        if (counterIsActive) {
            resetTimer();
        } else {

            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            goButton.setText("STOP");

            countDownTimer =
                    new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                        @Override
                        public void onTick(long l) {
                            //Log.i("Button Pressed", Integer.toString((int)l));
                            updateTimer((int) l / 1000);
                        }

                        @Override
                        public void onFinish() {
                            Log.i("Timer", "Timer Done");
                            MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.firetruckair);
                            mPlayer.start();
                            resetTimer();
                        }
                    }.start();
        }
    }

    private void resetTimer() {
        timerTextView.setText("00:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("GO!");
        counterIsActive = false;
    }


    public void updateTimer(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String secondString = Integer.toString(seconds);

        if (seconds <= 9) {
            secondString = "0" + secondString;
        }

        String minuteString = Integer.toString(minutes);

        if (minutes <= 9) {
            minuteString = "0" + minuteString;
        }

        timerTextView.setText(minuteString + ":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.countDonntextView);
        goButton = findViewById(R.id.goButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
