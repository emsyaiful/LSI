package com.example.uul.lembagasensorindonesia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.view.View;
import android.widget.Button;

public class Main extends AppPermission {

    private static final int REQUEST_PERMISSION = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestAppPermission(new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS},R.string.androidpermission,REQUEST_PERMISSION);

        Button button = (Button) findViewById(R.id.lanjut);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main.this, Home.class));
            }
        });

    }

    @Override
    public void onPermissionGranted(int requestCode) {

    }
}
