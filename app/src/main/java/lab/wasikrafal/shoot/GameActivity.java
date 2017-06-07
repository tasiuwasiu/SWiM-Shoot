package lab.wasikrafal.shoot;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

/**
 * Created by Rafał on 31.05.2017.
 */

public class GameActivity extends Activity implements SensorEventListener, View.OnClickListener
{
    private SensorManager manager;
    private BubbleView bubbleView;
    private Sensor accel;
    final Handler handler = new Handler();
    long start;
    long stop;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bubbleView = new BubbleView(this);
        setContentView(bubbleView);
        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accel = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(this, accel,
                SensorManager.SENSOR_DELAY_GAME);
        bubbleView.setOnClickListener(this);
        start=System.currentTimeMillis();
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    public void onSensorChanged(SensorEvent event)
    {
        bubbleView.move(event.values[0], event.values[1]);
        bubbleView.invalidate();
    }
    protected void onResume() {
        super.onResume();
        manager.registerListener(this, accel,
                SensorManager.SENSOR_DELAY_GAME);
    }
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }

    public void onClick (View v)
    {
        bubbleView.isIn();
        if (bubbleView.isDone()) {
            stop=System.currentTimeMillis();

            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", (stop-start));
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }, 2000);
        }
    }
}