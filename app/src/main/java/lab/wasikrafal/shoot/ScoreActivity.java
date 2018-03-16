package lab.wasikrafal.shoot;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class ScoreActivity extends Activity implements View.OnClickListener
{

    String score;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Button b = (Button) findViewById(R.id.but_end);
        b.setOnClickListener(this);
        setTime ();
    }

    private void setTime()
    {
        Bundle bundle = getIntent().getExtras();
        long best = bundle.getLong(getString(R.string.best_string));
        long curr = bundle.getLong(getString(R.string.curr_string));
        TextView tv_curr = (TextView) findViewById(R.id.tv_curr);
        TextView tv_best = (TextView) findViewById(R.id.tv_best);

        score = String.format(Locale.getDefault(), "%d.%d",
                TimeUnit.MILLISECONDS.toSeconds(curr),
                curr - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(curr)));
        score = getString(R.string.tm) + score + getString(R.string.sec);
        tv_curr.setText(score);

        if (curr < best || best == 0)
            tv_best.setText(R.string.nr);
        else
            tv_best.setText("");
    }

    private void useCal()
    {
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis() +60*60*1000)
                .putExtra(CalendarContract.Events.TITLE, "Score")
                .putExtra(CalendarContract.Events.DESCRIPTION, score);
        startActivity(intent);
    }

    public void onClick(View v)
    {
        useCal();
        finish();
    }
}
