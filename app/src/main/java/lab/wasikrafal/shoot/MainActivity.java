package lab.wasikrafal.shoot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

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
        Intent play = new Intent(this, GameActivity.class);
        startActivity(play);
    }
}
