package lab.wasikrafal.shoot;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

/**
 * Created by Rafał on 31.05.2017.
 */

public class GameActivity extends Activity implements SensorEventListener
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

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // don't do anything; we don't care
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
}