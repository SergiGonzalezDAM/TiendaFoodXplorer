package com.foodxplorer.tiendafoodxplorer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class InfoPedidoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pedido);

        List<String> lista = new ArrayList();
        lista.add("Barbacoa");
        lista.add("4 Quesos");
        List<String> listaEj = new ArrayList();
        listaEj.add("Por hacer");
        listaEj.add("Listo para entregar");
        listaEj.add("En reparto");
        listaEj.add("Entregado");

        ListView listView =(ListView) findViewById(R.id.listViewInfoPedido);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerEstado);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        ArrayAdapter<String> adaptadorEj = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaEj);

        listView.setAdapter(adaptador);
        spinner.setAdapter(adaptadorEj);

    }
}
