package com.example.artur.projekt1

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;
import android.widget.Toast;
import android.R.attr.y
import android.R.attr.x



class CameraActivity : AppCompatActivity() {
    private lateinit var mDrawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        getWindow().getDecorView().setBackgroundColor(Color.RED);
        mDrawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            print(menuItem.itemId)
            // close drawer when item is tapped
            mDrawerLayout.closeDrawers()

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here

            true
        }
        val sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val sensorMagnetic = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        val gravSensor = sm.getDefaultSensor(Sensor.TYPE_GRAVITY)
        val accSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        val obiektObserwatora = SensorObserver()
        if (accSensor != null)
            sm.registerListener(obiektObserwatora, accSensor, SensorManager.SENSOR_DELAY_GAME)
    }

    class SensorObserver: SensorEventListener{
        var gravity =  FloatArray(3)
        var accelerometr = FloatArray(3)
        var last_x: Float = 0F
        var last_y: Float = 0F
        var last_z: Float = 0F
        var lastUpdate: Long = 0
        var lastUpdateShake: Long = 0
        var shake: Boolean = false
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            print("Hello")
        }

        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null) {
                when(event.sensor.type){
                    Sensor.TYPE_GRAVITY -> {
                        gravity = event.values.clone()
                    }
                    Sensor.TYPE_ACCELEROMETER -> {
                        accelerometr = event.values.clone()
                        var ax = R.id.ax as TextView
                        ax.text = accelerometr[0].toString()
                        //ax.setText(java.lang.Float.toString(accelerometr[0]))

                        var ay = R.id.ay as TextView
                        ay.text = accelerometr[1].toString()
//                        ay.setText(java.lang.Float.toString(accelerometr[1]))

                        var az = R.id.az as TextView
                        az.text = accelerometr[2].toString()
//                        az.setText(java.lang.Float.toString(toStringaccelerometr[2]))
                        var curTime = System.currentTimeMillis()
                        if ((curTime - lastUpdate) > 100) {
                            var diffTime = curTime - lastUpdate
                            lastUpdate = curTime
                            var x = accelerometr[0]
                            var y = accelerometr[1]
                            var z = accelerometr[2]
                            var speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000
                            if (speed > 1000) {
                                shake = true;
                                lastUpdateShake = curTime;
                            } else if ((curTime - lastUpdateShake) > 5000) {
                                shake = false;
                            }
                            last_x = x;
                            last_y = y;
                            last_z = z;
                        }
                        var shakeTextView = R.id.shake as TextView
                        if(shake){
                            shakeTextView.text = "Wykryto wstrząs"
                        } else {
                            shakeTextView.text = " "
                        }
                    }
                    Sensor.TYPE_MAGNETIC_FIELD -> print("Magnetic")
                }
            }
        }
    }

}