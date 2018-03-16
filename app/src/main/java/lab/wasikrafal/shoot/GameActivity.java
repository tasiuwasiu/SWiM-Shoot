package lab.wasikrafal.shoot;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;


public class GameActivity extends Activity implements SensorEventListener, View.OnClickListener
{
    private SensorManager manager;
    private BubbleView bubbleView;
    private Sensor accel;
    final Handler handler = new Handler();
    long start;
    long stop;
    long bestTime;
    int lives =3;
    Timer timer;

    Intent score;
    AudioAttributes attrs = new AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build();
    SoundPool sp = new SoundPool.Builder()
            .setMaxStreams(10)
            .setAudioAttributes(attrs)
            .build();
    int soundIds[] = new int[2];
    MediaPlayer mp;

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
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        loadSound();
        mp = MediaPlayer.create(this, R.raw.music);
        mp.setLooping(true);
        mp.start();
        enemyFunction();
    }

    private void loadSound ()
    {
        soundIds[0] = sp.load(this, R.raw.shoot, 1);
        soundIds[1] = sp.load(this, R.raw.finish, 1);
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
        sp.play(soundIds[0], 1, 1, 2, 0, 1.0f );
        if (bubbleView.isDone()) {
            bubbleView.setOnClickListener(null);
            stop=System.currentTimeMillis();
            bestTime = getIntent().getExtras().getLong(getString(R.string.best_string), 0);
            final long currentTime=stop-start;

            timer.cancel();
            timer.purge();

            score = new Intent(this, ScoreActivity.class);
            Bundle extras = new Bundle();
            extras.putLong(getString(R.string.best_string), bestTime);
            extras.putLong(getString(R.string.curr_string), currentTime);
            score.putExtras(extras);
            mp.stop();
            mp.release();
            sp.play(soundIds[1], 1, 1, 3, 0, 1.0f );

            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {

                    sp.release();
                    startActivity(score);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(getString(R.string.res_string), currentTime);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }, 5000);
        }
    }

    private void onGameOver()
    {
        final Intent gameover = new Intent(this, GameOverActivity.class);

        mp.stop();
            mp.release();
                startActivity(gameover);
                Intent returnIntent = new Intent();
                returnIntent.putExtra(getString(R.string.res_string), Long.MAX_VALUE);
                setResult(Activity.RESULT_OK,returnIntent);
                timer.cancel();
                timer.purge();
                finish();
    }

    private void enemyFunction()
    {
        timer =new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                bubbleView.moveEnemy();
                if(bubbleView.checkHit())
                {
                    lives--;
                    bubbleView.resetBubble();
                    if (lives < 1) {
                        onGameOver();
                    }
                }
            }
        };
        timer.schedule(task,0,33);
    }


}