package com.example.sensortemperaturahumedad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.net.ProtocolException;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView TemperaturaTV;
    private TextView HumedadTV;
    private SensorManager sensorManager;
    private Sensor TemperaturaSensor;
    private Sensor HumedadSensor;
    private boolean TemperaturaDisponible;
    private boolean HumedadDisponible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TemperaturaTV = findViewById(R.id.Temperatura);
        HumedadTV = findViewById(R.id.Humedad);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        TemperaturaDisponible = false;
        HumedadDisponible = false;

        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            TemperaturaSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            TemperaturaDisponible = true;
        }else{
            TemperaturaTV.setText("El sensor de temperatura no esta disponible");
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null){
            HumedadSensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            HumedadDisponible = true;
        }else{
            HumedadTV.setText("El sensor de Humedad no esta disponible");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        if (sensorEvent.sensor.getType()==Sensor.TYPE_AMBIENT_TEMPERATURE){
            TemperaturaTV.setText(sensorEvent.values[0]+" °C ");
        } else if (sensorEvent.sensor.getType()==Sensor.TYPE_RELATIVE_HUMIDITY) {
            HumedadTV.setText(sensorEvent.values[0]+" % ");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i){
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (TemperaturaDisponible){
            sensorManager.registerListener(this, TemperaturaSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (HumedadDisponible){
            sensorManager.registerListener(this, HumedadSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}