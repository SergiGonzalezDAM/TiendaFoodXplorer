package com.foodxplorer.tiendafoodxplorer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnRepartidor, btnCocinero;

    //TODO investigar como hacer para que se ponga por defecto expandido
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRepartidor = (Button) findViewById(R.id.btnRepartidor);
        btnRepartidor.setOnClickListener(this);
        btnCocinero = (Button) findViewById(R.id.btnCocinero);
        btnCocinero.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (R.id.btnCocinero == view.getId()) {
            Intent i = new Intent(this, CocineroActivity.class);
            startActivity(i);
        } else {
            Intent i = new Intent(this, RepartidorActivity.class);
            startActivity(i);
        }
    }
}
