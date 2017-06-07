package lab.wasikrafal.shoot;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    long time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button) findViewById(R.id.buttonStart);
        b.setOnClickListener(this);

    }


    public void onClick(View v)
    {
        //Intent play = new Intent(this, GameActivity.class);
        //startActivity(play);

        Intent i = new Intent(this, GameActivity.class);
        startActivityForResult(i, 1);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                time = data.getLongExtra("result", 0);
                TextView t = (TextView) findViewById(R.id.time);
                t.setText(
                        String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(time),
                        TimeUnit.MILLISECONDS.toSeconds(time) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
                        ));
            }

            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

    }
}
