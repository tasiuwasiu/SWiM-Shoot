package lab.wasikrafal.shoot;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


/**
 * Created by Rafał on 11.06.2017.
 */

public class ScoreActivity extends Activity implements View.OnClickListener
{

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

        String score = String.format(Locale.getDefault(),"%d.%d",
                TimeUnit.MILLISECONDS.toSeconds(curr),
                curr - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(curr)));
        score = getString(R.string.tm) + score + getString(R.string.sec);
        tv_curr.setText(score);

        if (curr<best || best==0)
            tv_best.setText(R.string.nr);
        else
            tv_best.setText("");
    }

    public void onClick(View v)
    {
        finish();
    }
}
