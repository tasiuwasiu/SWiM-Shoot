package lab.wasikrafal.shoot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    long bestTime=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button) findViewById(R.id.buttonStart);
        b.setOnClickListener(this);
        load();
    }


    public void onClick(View v)
    {
        Intent i = new Intent(this, GameActivity.class);
        Bundle extras = new Bundle();
        extras.putLong("best", bestTime);
        i.putExtras(extras);
        startActivityForResult(i, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                long time = data.getLongExtra("result", 0);
                checkTime(time);
            }
        }
    }

    private void checkTime(long time)
    {
        if (time<bestTime || bestTime == 0)
            {
                setTime(time);
            }
    }

    private void setTime (long time)
    {
        bestTime=time;
        String score = String.format(Locale.getDefault(),"%d.%d",
                TimeUnit.MILLISECONDS.toSeconds(time),
                time - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(time))
        );
        score = getString(R.string.bestTime) + score + getString(R.string.sec);
        TextView t = (TextView) findViewById(R.id.time);
        t.setText(score);
        save(score, time);
    }

    private void save (String score, long time)
    {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.high_score), score);
        editor.putLong("time", time);
        editor.commit();
    }

    private void load ()
    {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        if(sharedPref.contains(getString(R.string.high_score)))
        {
            String score = sharedPref.getString(getString(R.string.high_score),"");
            bestTime = sharedPref.getLong("time", 0);
            TextView t = (TextView) findViewById(R.id.time);
            t.setText(score);
        }
    }
}
