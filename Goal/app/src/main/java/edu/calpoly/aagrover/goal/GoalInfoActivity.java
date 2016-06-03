package edu.calpoly.aagrover.goal;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class GoalInfoActivity extends AppCompatActivity {

    private TextView dailyMotivation;
    private TextView checkUserProgress;
    private TextView useBribery;

    private float percentage = 0;
    private int days;

    private String[] quotes = new String[] {
            "\"Good, better, best. Never let it rest. 'Til your good is better and your better is best.\" - St. Jerome",
            "\"The way to get started is to quit talking and begin doing.\" - Walt Disney",
            "\"In order to succeed, we must first believe that we can.\" - Nikos Kazantzakis",
            "\"I'd rather attempt to do something great and fail than to attempt to do nothing and succeed.\" - Robert H. Schuller",
            "\"What you do today can improve all your tomorrows.\" - Ralph Marston",
            "\"Failure will never overtake me if my determination to succeed is strong enough.\" - Og Mandino",
            "\"You are never too old to set another goal or to dream a new dream.\" - C. S. Lewis",
            "\"It does not matter how slowly you go as long as you do not stop.\" - Confucius",
            "\"Always do your best. What you plant now, you will harvest later.\" - Og Mandino",
            "\"Accept the challenges so that you can feel the exhilaration of victory.\"  - George S. Patton",
            "\"You can't cross the sea merely by standing and staring at the water.\" - Rabindranath Tagore",
            "\"Keep your eyes on the stars, and your feet on the ground.\" - Theodore Roosevelt",
            "\"A creative man is motivated by the desire to achieve, not by the desire to beat others.\" - Ayn Rand",
            "\"If you can dream it, you can do it.\" - Walt Disney",
            "\"With the new day comes new strength and new thoughts.\" - Eleanor Roosevelt",
            "\"Our greatest weakness lies in giving up. The most certain way to succeed is always to try just one more time.\" - Thomas A. Edison",
            "\"It always seems impossible until its done.\" - Nelson Mandela",
            "\"Problems are not stop signs, they are guidelines.\" - Robert H. Schuller",
            "\"The secret of getting ahead is getting started.\" - Mark Twain",
            "\"Aim for the moon. If you miss, you may hit a star.\" - W. Clement Stone",
            "\"Don't watch the clock; do what it does. Keep going.\" - Sam Levenson",
            "\"Start where you are. Use what you have. Do what you can.\" - Arthur Ashe",
            "\"Optimism is the faith that leads to achievement. Nothing can be done without hope and confidence.\" - Helen Keller",
            "\"There is only one corner of the universe you can be certain of improving, and that's your own self.\" - Aldous Huxley",
            "\"What you get by achieving your goals is not as important as what you become by achieving your goals.\" - Zig Ziglar",
            "\"Ever tried. Ever failed. No matter. Try Again. Fail again. Fail better.\" - Samuel Beckett",
            "\"Setting goals is the first step in turning the invisible into the visible.\" - Tony Robbins",
            "\"Perseverance is failing 19 times and succeeding the 20th.\" - Julie Andrews",
            "\"Press forward. Do not stop, do not linger in your journey, but strive for the mark set before you.\" - George Whitefield",
            "\"By failing to prepare, you are preparing to fail.\" - Benjamin Franklin",
            "\"You just can't beat the person who never gives up.\" - Babe Ruth",
            "\"Things do not happen. Things are made to happen.\" - John F. Kennedy",
            "\"A goal is a dream with a deadline.\" - Napoleon Hill",
            "\"If you're going through hell, keep going.\" - Winston Churchill",
            "\"The harder the conflict, the more glorious the triumph.\" - Thomas Paine",
            "\"Pursue one great decisive aim with force and determination.\" - Carl von Clausewitz",
            "\"One finds limits by pushing them.\" - Herbert Simon",
            "\"If you fell down yesterday, stand up today.\" - H. G. Wells",
            "\"Don't give up. Don't lose hope. Don't sell out.\" - Christopher Reeve"
    };

    private String[] rewards = new String[] {
            "Celebrate success with a snooze.",
            "Visit someplace special.",
            "Soak it all up - maybe at the beach?",
            "Write and remember this moment.",
            "Indulge in a movie marathon.",
            "Refresh your wardrobe.",
            "Get new gear.",
            "Download a kindle book.",
            "Download a song.",
            "Do nothing.",
            "Do nothing.",
            "Do nothing.",
            "Do nothing.",
            "Enjoy peace and quiet.",
            "Lay down for a nap.",
            "Sip coffee or tea.",
            "Play a game.",
            "Watch your favorite tv show.",
            "Work a crossword or sudoku puzzle.",
            "Go bowling.",
            "Go to a spa.",
            "Go mini golfing.",
            "Put money towards a vacation.",
            "Play a video game."
    };

    private String randomQuote = quotes[(int) (Math.random() * quotes.length)];
    private String randomBribe = rewards[(int) (Math.random() * rewards.length)];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_info);

        setTitle("The Details");

        this.dailyMotivation = (TextView)findViewById(R.id.tvProgress);
        this.checkUserProgress = (TextView)findViewById(R.id.checkUserProgress);
        this.useBribery = (TextView)findViewById(R.id.briberyText);

        final ProgressWheel pwOne = (ProgressWheel) findViewById(R.id.progressBarTwo);

        TextView tvGoal = (TextView) findViewById(R.id.tvGoal);
        TextView tvDate = (TextView) findViewById(R.id.tvDate);

        if(getIntent().hasExtra("goal") && getIntent().hasExtra("date")) {
            String theGoal = getIntent().getStringExtra("goal");
            String theDate = getIntent().getStringExtra("date");
            String startDate = getIntent().getStringExtra("curDate");

            tvGoal.setText(theGoal);
            tvDate.setText(theDate);

            // Get completed date to track progress.
            String[] content = theDate.split("/");
            int year = Integer.parseInt(content[2]);
            int day = Integer.parseInt(content[1]);
            int month = Integer.parseInt(content[0]);

            Calendar c = new GregorianCalendar(year, month-1, day);
            long millis = c.getTimeInMillis();
            long difference = millis - Calendar.getInstance().getTime().getTime();

            // Get start date to use for comparison.
            String[] content1 = startDate.split("/");
            year = Integer.parseInt(content1[0]);
            day = Integer.parseInt(content1[2]);
            month = Integer.parseInt(content1[1]);

            c = new GregorianCalendar(year, month-1, day);
            int totalDaysToCompleteGoal = (int) ((millis - c.getTimeInMillis()) / DateUtils.DAY_IN_MILLIS);

            countDownConfig(difference, totalDaysToCompleteGoal, pwOne);
        }

        // Giving user daily motivation.
        // Pop Up Window details.
        dailyMotivation.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {

                LayoutInflater layoutInflater
                        = (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.activity_motivation_pop_up, null);

                final PopupWindow popupWindow = new PopupWindow(
                        popupView, 1300, ActionBar.LayoutParams.WRAP_CONTENT);

                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                TextView insertQuote = (TextView)popupView.findViewById(R.id.insertQuote);
                insertQuote.setText(randomQuote);

                Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }});

                popupWindow.showAsDropDown(dailyMotivation, 50, 30);

            }
        });

        // Checking user progress information.
        // Pop Up Window details.
        checkUserProgress.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                String userStats;

                LayoutInflater layoutInflater
                        = (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.stats_pop_up, null);

                final PopupWindow popupWindow = new PopupWindow(
                        popupView, 1300, ActionBar.LayoutParams.WRAP_CONTENT);

                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                TextView insertQuote = (TextView)popupView.findViewById(R.id.showProgress);

                if (days == 1) {
                    userStats = "Your progress is at " + String.format("%.1f", (percentage*100)) + "% with " + days + " day to go!";
                }
                else {
                    userStats = "Your progress is at " + String.format("%.1f", (percentage*100)) + "% with " + days + " days to go!";
                }
                insertQuote.setText(userStats);

                Button btnDismiss = (Button)popupView.findViewById(R.id.check_done);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }});

                popupWindow.showAsDropDown(checkUserProgress, 50, 30);

            }
        });

        // Get user to bribe themselves to reach their goals.
        // Pop Up Window details.
        useBribery.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {

                LayoutInflater layoutInflater
                        = (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.bribe_pop_up, null);

                final PopupWindow popupWindow = new PopupWindow(
                        popupView, 1300, ActionBar.LayoutParams.WRAP_CONTENT);

                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                TextView showBribe = (TextView)popupView.findViewById(R.id.showBribe);
                showBribe.setText(randomBribe);

                Button btnDismiss = (Button)popupView.findViewById(R.id.bribe_done);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }});

                popupWindow.showAsDropDown(useBribery, 50, 30);

            }
        });

    }

    public void countDownConfig(long difference, int totalDaysToComplete, final ProgressWheel pwOne) {

        final TextView daysCount = (TextView) findViewById(R.id.tvDays);
        final TextView hoursCount = (TextView) findViewById(R.id.tvHours);
        final TextView minCount = (TextView) findViewById(R.id.tvMin);
        final TextView secCount = (TextView) findViewById(R.id.tvSec);

        final long total = (long)totalDaysToComplete;

        CountDownTimer cdt = new CountDownTimer(difference, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                days = 0;
                int hours = 0;
                int minutes = 0;
                int seconds = 0;
                int progress = 0;
                String sDate = "0";

                if (millisUntilFinished > DateUtils.DAY_IN_MILLIS) {
                    days = (int) (millisUntilFinished / DateUtils.DAY_IN_MILLIS);
                    sDate = "" + days;

                    float day = (float)days + 1;
                    float x = (day / (float)total) * 360;
                    progress = (360 - (int)x);

                    int p = (int)total - (int)day;

                    percentage = p / (float)total;
                    pwOne.setProgress(progress);
                    pwOne.setText("" + String.format("%.1f", (percentage*100)) + " %");

                    GoalsActivity.percentage = String.valueOf(percentage) + "%";
                }

                millisUntilFinished -= (days * DateUtils.DAY_IN_MILLIS);

                if (millisUntilFinished > DateUtils.HOUR_IN_MILLIS) {
                    hours = (int) (millisUntilFinished / DateUtils.HOUR_IN_MILLIS);
                    if (progress == 0) {
                        pwOne.setProgress(0);
                        pwOne.setText("0 %");
                    }
                }

                millisUntilFinished -= (hours * DateUtils.HOUR_IN_MILLIS);

                if (millisUntilFinished > DateUtils.MINUTE_IN_MILLIS) {
                    minutes = (int) (millisUntilFinished / DateUtils.MINUTE_IN_MILLIS);
                }

                millisUntilFinished -= (minutes * DateUtils.MINUTE_IN_MILLIS);

                if (millisUntilFinished > DateUtils.SECOND_IN_MILLIS) {
                    seconds = (int) (millisUntilFinished / DateUtils.SECOND_IN_MILLIS);
                }

                String d = sDate;
                daysCount.setText(d);
                hoursCount.setText("" + String.format("%02d",hours));
                minCount.setText("" + String.format("%02d",minutes));
                secCount.setText("" + String.format("%02d",seconds));
            }

            @Override
            public void onFinish() {
                daysCount.setText("0");
                hoursCount.setText("0");
                minCount.setText("0");
                secCount.setText("0");

                pwOne.setProgress(360);
                pwOne.setText("100.0 %");

                TextView finished = (TextView) findViewById(R.id.tvProgress);
                finished.setText("Congrats! You completed your goal!");
            }
        };

        cdt.start();
    }
}
