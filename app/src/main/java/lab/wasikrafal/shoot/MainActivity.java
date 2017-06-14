package lab.wasikrafal.shoot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    long bestTime=0;
    boolean isData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button) findViewById(R.id.buttonStart);
        b.setOnClickListener(this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        load();
    }


    public void onClick(View v)
    {
        Intent i = new Intent(this, GameActivity.class);
        Bundle extras = new Bundle();
        extras.putLong(getString(R.string.best_string), bestTime);
        i.putExtras(extras);
        startActivityForResult(i, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                long time = data.getLongExtra(getString(R.string.res_string), 0);
                checkTime(time);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_clear).setEnabled(isData);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_clear)
        {
            clear();
            return true;
        }

        if (id == R.id.action_exit)
        {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        invalidateOptionsMenu();
    }

    private void save (String score, long time)
    {
        isData = true;
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.high_score), score);
        editor.putLong(getString(R.string.time), time);
        editor.commit();
    }

    private void load ()
    {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        if(sharedPref.contains(getString(R.string.high_score)))
        {
            String score = sharedPref.getString(getString(R.string.high_score),"");
            bestTime = sharedPref.getLong(getString(R.string.time), 0);
            TextView t = (TextView) findViewById(R.id.time);
            t.setText(score);
            isData = true;
        }
    }

    private void clear ()
    {
        isData=false;
        bestTime = 0;
        TextView t = (TextView) findViewById(R.id.time);
        t.setText(R.string.no_time);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
        invalidateOptionsMenu();
    }
}
