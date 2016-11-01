package com.example.uul.lembagasensorindonesia;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;

import static android.content.Context.SENSOR_SERVICE;
import static android.hardware.Sensor.TYPE_ACCELEROMETER;

/**
 * Created by Setiyo on 10/6/2016.
 */

public class Sms extends BroadcastReceiver implements SensorEventListener
{
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    private String nomor = "";
    private String msg;
    private SensorManager SM;
    private Sensor mySensor;
    private String msgs ="";
    private String hasil;
    private Float cobax, cobay, cobaz;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String coba = intent.getAction();

        if(coba.equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();

            if (bundle != null) {
                //---retrieve the SMS message received---
                Object[] pdus = (Object[]) bundle.get("pdus");

                final SmsMessage[] msgs = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    this.nomor = msgs[i].getOriginatingAddress();

                }
                Log.d("coba", "onReceive: "+this.nomor+" "+this.msgs);


                SM = (SensorManager) context.getSystemService(SENSOR_SERVICE);
                if (SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null){
                    mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                    SM.registerListener(this,mySensor,SensorManager.SENSOR_DELAY_GAME);
                }


                // Accelerometer Sensor
                mySensor = SM.getDefaultSensor(TYPE_ACCELEROMETER);
            }

        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor mySensor = event.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            cobax = event.values[0];
            cobay = event.values[1];
            cobaz = event.values[2];
            if (cobax > 20 || cobay > 20 || cobaz > 20) {
                hasil = "NaikMotor";
            } else {
                hasil = "tidaknaikmotor";
            }
            if (hasil == "NaikMotor") {
                msg = "Maaf, nomor yang anda tuju sedang dalam perjalanan";
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(nomor, null, msg, null, null);
            }
            SM.unregisterListener(this);
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
