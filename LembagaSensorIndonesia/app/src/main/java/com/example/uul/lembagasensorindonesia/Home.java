package com.example.uul.lembagasensorindonesia;

import android.app.Activity;
import android.os.Bundle;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class Home extends Activity implements SensorEventListener{

    private TextView xText, yText, zText;
    private Sensor mySensor;
    private SensorManager SM;
    private CSVWriter writer;
    String external = Environment.getExternalStorageDirectory().getAbsolutePath();
    File dataSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Create our Sensor Manager
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        // Accelerometer Sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void onStartClick(View view) {
        if (view.getId() == R.id.button1) {
            SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
            xText = (TextView)findViewById(R.id.xText);
            yText = (TextView)findViewById(R.id.yText);
            zText = (TextView)findViewById(R.id.zText);
        }
    }

    public void onStopClick(View view) {
        if (view.getId() == R.id.button3) {
            SM.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        xText.setText(String.valueOf(event.values[0]));
        yText.setText(String.valueOf(event.values[1]));
        zText.setText(String.valueOf(event.values[2]));
        String saveTo = external+"/dataSensor.csv";
        File nama = new File(saveTo);
        SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String currDate = date.format(new Date());
        String res = String.valueOf(currDate+"; "+event.values[0]+"; "+event.values[1]+"; "+event.values[2]);
        try {
            writer = new CSVWriter(new FileWriter(nama, true), ';');
            String[] save = res.split(";");
            writer.writeNext(save);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}