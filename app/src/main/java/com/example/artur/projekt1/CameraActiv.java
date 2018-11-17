package com.example.artur.projekt1;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.widget.DrawerLayout;

public class CameraActiv extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor sensorMagnetic = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor gravSensor = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
        Sensor accSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        ObserwarotSensorow obiektObserwatora = new ObserwarotSensorow();

        if (accSensor!= null)
            sm.registerListener(obiektObserwatora,accSensor, SensorManager.SENSOR_DELAY_GAME);


    }

    class ObserwarotSensorow implements SensorEventListener {
        float[] gravity = null;
        float[] acce = null;

        float last_x;
        float last_y;
        float last_z;
        long lastUpdate;
        long lastUpdateShake;
        boolean shake = false;

        @Override
        public void
        onSensorChanged(SensorEvent event) {
            switch (event.sensor.getType()){
                case Sensor.TYPE_GRAVITY:
                    gravity = event.values.clone();  //klonujemy dane z sensora
                    //TODO
                case Sensor.TYPE_ACCELEROMETER:
                    acce = event.values.clone();

                    TextView ax = findViewById(R.id.ax);
                    ax.setText(Float.toString(acce[0]));

                    TextView ay = findViewById(R.id.ay);
                    ay.setText(Float.toString(acce[1]));

                    TextView az = findViewById(R.id.az);
                    az.setText(Float.toString(acce[2]));

                    long curTime = System.currentTimeMillis();
                    if ((curTime - lastUpdate) > 100) {
                        long diffTime = (curTime - lastUpdate);
                        lastUpdate = curTime;

                        float x = acce[0];
                        float y = acce[1];
                        float z = acce[2];

                        float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

                        if (speed > 1000) {
                            shake = true;
                            lastUpdateShake = curTime;
                        }
                        else if((curTime - lastUpdateShake) > 5000){
                            shake = false;
                        }

                        last_x = x;
                        last_y = y;
                        last_z = z;
                    }

                    TextView shakeTextView = findViewById(R.id.shake);
                    if(shake) {
                        shakeTextView.setText("Wykryto wstrząs");
                    }
                    else {
                        shakeTextView.setText(" ");
                    }

                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    //TODO
                    break;
            }
        }
        @Override
        public void
        onAccuracyChanged(Sensor sensor, int i) {
        }
    }
}
