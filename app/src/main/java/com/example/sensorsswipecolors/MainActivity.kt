package com.example.sensorsswipecolors

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.getSystemService
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt
import kotlin.random.Random

class MainActivity : AppCompatActivity(),SensorEventListener {

    lateinit var sensorManager: SensorManager
    lateinit var proxSensor:Sensor
    lateinit var accelSensor:Sensor

    val colors=arrayOf(Color.RED,Color.BLUE,Color.GRAY,Color.GREEN,Color.YELLOW,Color.MAGENTA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager=getSystemService<SensorManager>()!!
        proxSensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        accelSensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    }

    override fun onSensorChanged(event: SensorEvent?) {
//        if(event!!.values[0]>0){
//            flProxSensor.setBackgroundColor(colors[Random.nextInt(6)])
//        }

//        Log.d("Accel",
//        """
//            ----
//            ax=${event!!.values[0]}
//            ay=${event!!.values[1]}
//            az=${event!!.values[2]}
//            ----
//        """.trimIndent())
        var bgColor=Color.rgb(
            accel2Color(event!!.values[0]),
            accel2Color(event!!.values[1]),
            accel2Color(event!!.values[2]),
        )
        flAccSensor.setBackgroundColor(bgColor)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //nothing
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,accelSensor,1000)
    }

    override fun onPause() {
        sensorManager.unregisterListener(this,accelSensor)

        super.onPause()
    }

    private fun accel2Color(accel:Float)=((accel+12)/24*255).roundToInt()

}