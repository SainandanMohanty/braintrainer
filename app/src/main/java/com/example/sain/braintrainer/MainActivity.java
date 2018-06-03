package com.example.sain.braintrainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    int correct;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correct = 0;
        total = 0;
    }

    public void onClick(View view) {
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

        constraintLayout.getChildAt(0).setVisibility(View.GONE);
        for (int i = 1; i < constraintLayout.getChildCount(); i++) {
            constraintLayout.getChildAt(i).setVisibility(View.VISIBLE);
        }

        initialise();
    }

    public void onClickReset(View view) {
        countDownTimer.cancel();

        TextView score = findViewById(R.id.textViewScore);
        score.setText("0/0");

        TextView status = findViewById(R.id.textViewStatus);
        status.setText("");

        correct = 0;
        total = 0;

        initialise();
    }

    private void initialise() {
        final TextView status = findViewById(R.id.textViewStatus);

        final TextView timer = findViewById(R.id.textViewTimer);
        timer.setTag("1");
        countDownTimer = new CountDownTimer(30100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.format(Locale.getDefault(), "%d", millisUntilFinished / 1000).concat("s"));
            }

            @Override
            public void onFinish() {
                timer.setText("0s");
                timer.setTag("0");
                status.setText("Time's up!!");
            }
        }.start();

        setQuestion();
    }

    private void setQuestion() {
        Random random = new Random();
        int a = random.nextInt(20);
        int b = random.nextInt(20);

        TextView question = findViewById(R.id.textViewQuestion);
        question.setText(String.format(Locale.getDefault(), "%d + %d", a, b));

        TextView option;

        int correctOption = random.nextInt(4);

        for (int i = 0; i < 4; i++) {
            option = findViewById(getResources().getIdentifier(
                    "textViewOption" + Integer.toString(i + 1), "id", getPackageName()));
            if (i == correctOption) {
                option.setText(String.format(Locale.getDefault(), "%d", a + b));
                option.setTag("1");
            } else {
                option.setText(String.format(Locale.getDefault(), "%d", random.nextInt(39)));
                option.setTag("0");
            }
        }
    }

    public void onClickOption(View view) {
        TextView option = (TextView) view;
        TextView status = findViewById(R.id.textViewStatus);
        TextView score = findViewById(R.id.textViewScore);
        TextView timer = findViewById(R.id.textViewTimer);

        if (timer.getTag().equals("1")) {
            if (option.getTag().equals("1")) {
                status.setText("Correct :)");
                setQuestion();

                correct++;
                total++;
                score.setText(String.format(Locale.getDefault(), "%d/%d", correct, total));
            } else {
                status.setText("Incorrect :(");
                setQuestion();

                total++;
                score.setText(String.format(Locale.getDefault(), "%d/%d", correct, total));
            }
        }
    }
}
