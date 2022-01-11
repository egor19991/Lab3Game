package com.example.lab3game;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;


import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements SensorEventListener{ //extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long lastUpdate;

    AnimatedView animatedView = null;
    ShapeDrawable mDrawable = new ShapeDrawable();
    public static int x;
    public static int y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastUpdate = System.currentTimeMillis();

        animatedView = new AnimatedView(this);
        setContentView(animatedView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener((SensorEventListener) this, accelerometer,
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener((SensorEventListener) this);
    }


    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (x < 1050 && y < 1500 && x >= 0 && y >= 0) {
                x -= (int) event.values[0];
                y += (int) event.values[1];
            } else {
                if (x >= 1050) x--;
                if (y >= 1500) y--;
                if (x < 0) x++;
                if (y < 0) y++;
            }
        }
    }

    public class AnimatedView extends androidx.appcompat.widget.AppCompatImageView {

        static final int width = 50;
        static final int height = 50;

        public AnimatedView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub

            mDrawable = new ShapeDrawable(new OvalShape());
            mDrawable.getPaint().setColor(0xffffAC23);
            mDrawable.setBounds(x, y, x + width, y + height);

        }

        @Override
        protected void onDraw(Canvas canvas) {
            mDrawable.setBounds(x, y, x + width, y + height);
            mDrawable.draw(canvas);
            invalidate();
        }
    }
}