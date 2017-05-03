package com.foodxplorer.tiendafoodxplorer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    //TODO investigar como hacer para que se ponga por defecto expandido
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = new Intent(this,CocineroActivity.class);
        startActivity(i);
    }
}
