package com.example.uul.lembagasensorindonesia;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jjoe64.graphview.GraphView;
import com.opencsv.CSVWriter;

import java.io.File;


public class Home extends Activity implements SensorEventListener {

    private TextView xText, yText, zText, statusnya;
    private Sensor mySensor;
    private SensorManager SM;
    private CSVWriter writer;
    String external = Environment.getExternalStorageDirectory().getAbsolutePath();
    File dataSensor;
    GraphView graph;
    Sms pesan;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Create our Sensor Manager
        SM = ( SensorManager ) getSystemService(SENSOR_SERVICE);
        pesan = new Sms();
        ;
        // Accelerometer Sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onStartClick(View view) {
        if (view.getId() == R.id.button1) {
            SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
            xText = ( TextView ) findViewById(R.id.xText);
            yText = ( TextView ) findViewById(R.id.yText);
            zText = ( TextView ) findViewById(R.id.zText);
            statusnya = ( TextView ) findViewById(R.id.statusnya);
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
        /*String saveTo = external + "/dataSensor.csv";
        File nama = new File(saveTo);
        SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String currDate = date.format(new Date());*/
        /*String res = String.valueOf(currDate + "; " + event.values[0] + "; " + event.values[1] + "; " + event.values[2]);*/
        /*try {*/
            /*writer = new CSVWriter(new FileWriter(nama, true), ';');
            String[] save = res.split(";");
            writer.writeNext(save);
            writer.close();*/
            if (event.values[0] > 5 || event.values[1] > 5 || event.values[2] > 5)
            {
                statusnya.setText("Sedang Naik Motor");
            }

        /*} catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Home Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}