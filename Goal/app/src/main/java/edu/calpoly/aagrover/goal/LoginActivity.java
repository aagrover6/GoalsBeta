package edu.calpoly.aagrover.goal;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LoginActivity extends Activity {

    ObjectAnimator translate = null;

    Button translateButton;
    Button button2;
    Button button3;

    TextView tvFirst;
    TextView tvSecond;
    TextView tvThird;

    Button done;

    private int count = 0;
    private int button1Flag = 0;
    private int button2Flag = 0;
    private int button3Flag = 0;
    private int translateFirst = 0;
    private int translateSecond = 0;
    private int translateThird = 0;

    private int first = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        translateButton = (Button) findViewById(R.id.theButton);
        button2 = (Button) findViewById(R.id.theButton2);
        button3 = (Button) findViewById(R.id.theButton3);

        done = (Button) findViewById(R.id.buttonDone);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int density = metrics.densityDpi;

        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.SCALE_X, -1);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 2);

        ObjectAnimator scaleAnimation1 = ObjectAnimator.ofPropertyValuesHolder(translateButton, pvhX, pvhY);
        scaleAnimation1.setRepeatCount(1);
        scaleAnimation1.setRepeatMode(ValueAnimator.REVERSE);

        ObjectAnimator translate2 = ObjectAnimator.ofPropertyValuesHolder(button2, pvhX, pvhY);
        translate2.setRepeatCount(1);
        translate2.setRepeatMode(ValueAnimator.REVERSE);

        ObjectAnimator translate3 = ObjectAnimator.ofPropertyValuesHolder(button3, pvhX, pvhY);
        translate3.setRepeatCount(1);
        translate3.setRepeatMode(ValueAnimator.REVERSE);

        setupAnimation(translateButton, scaleAnimation1, R.animator.scale, 1);
        setupAnimation(button2, translate2, R.animator.scale, 2);
        setupAnimation(button3, translate3, R.animator.scale, 3);

        done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (translateThird == 1) {
                    translate = ObjectAnimator.ofFloat(tvFirst, View.TRANSLATION_X, 1200);
                    translate.start();
                }

                Intent goalsIntent = new Intent(LoginActivity.this, GoalsActivity.class);
                LoginActivity.this.startActivity(goalsIntent);
            }
        });

        // Send notification to user daily.
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        Date curDate = new Date();
        String date = dateFormat.format(curDate);

        String[] content = date.split("/");
        int year = Integer.parseInt(content[2]);
        int day = Integer.parseInt(content[1]);
        int month = Integer.parseInt(content[0]);

        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.YEAR, year);

        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM, Calendar.PM);

        Intent intent = new Intent(LoginActivity.this, Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(LoginActivity.this, 0, intent, 0);
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
    }

    private void setupAnimation(final View view, final Animator animation, final int animationID, final int which) {
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                animation.start();
                count++;

                Log.w("which is ", "" + which);

                if (which == 1 && count == 1 && button1Flag == 0) {

                    tvFirst = (TextView) findViewById(R.id.textAfterFirst);
                    tvFirst.setText("Simply press 'Start New Goal' to create a goal.");

                    done.setBackgroundResource(R.drawable.thirtythree_percent);

                    button1Flag = 1;
                    translateFirst = 1;
                }
                else if (which == 2 && count == 2 && button2Flag == 0) {

                    translate = ObjectAnimator.ofFloat(tvFirst, View.TRANSLATION_X, 1200);
                    translate.start();

                    tvFirst.setText("A countdown for your goal will start.");

                    translate.setRepeatCount(1);
                    translate.setRepeatMode(ValueAnimator.REVERSE);

                    done.setBackgroundResource(R.drawable.sixtysix_percent);

                    button2Flag = 1;
                    translateSecond = 1;
                }
                else if (which == 3 && count == 3 && button3Flag == 0) {

                    translate = ObjectAnimator.ofFloat(tvFirst, View.TRANSLATION_X, 1200);
                    translate.start();

                    tvFirst.setText("You'll be shown your progress, as well.");

                    translate.setRepeatCount(1);
                    translate.setRepeatMode(ValueAnimator.REVERSE);

                    done.setBackgroundResource(R.drawable.hundred_percent);

                    button3Flag = 1;
                    translateThird = 1;
                }
                else {
                    if (button1Flag == 1) {
                        count = 1;
                    }
                    if (button2Flag == 1) {
                        count = 2;
                    }
                    if (button3Flag == 1) {
                        count = 3;
                    }
                    if (button1Flag == 0) {
                        count = 0;
                    }
                }

                done.setText("");
            }
        });
    }
}
