package lab.wasikrafal.shoot;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Rafał on 31.05.2017.
 */

public class GameActivity extends Activity implements SensorEventListener, View.OnClickListener
{
    private SensorManager manager;
    private BubbleView bubbleView;
    private Sensor accel;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bubbleView = new BubbleView(this);
        setContentView(bubbleView);
        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accel = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(this, accel,
                SensorManager.SENSOR_DELAY_GAME);
        bubbleView.setOnClickListener(this);
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
    }
}