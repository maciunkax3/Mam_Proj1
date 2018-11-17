package com.example.artur.projekt1;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorService implements SensorEventListener {
    private SensorManager mSensorManager;
    private float currentDegree;

    public SensorService(SensorManager mSensorManager) {
        this.mSensorManager = mSensorManager;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        // get the angle around the z-axis rotated
        currentDegree = event.values[0];

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public float getCurrentDegree(){
        return currentDegree;
    }
}
