package com.foodxplorer.tiendafoodxplorer;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.foodxplorer.tiendafoodxplorer.helper.Settings;
import com.foodxplorer.tiendafoodxplorer.model.Pedido;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.foodxplorer.tiendafoodxplorer.helper.Settings.LOGTAG;

public class CocineroActivity extends ExpandableListActivity {
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();
    private ArrayList<Pedido> listaPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TareaWSRecuperarPedidosPorCocinar tarea = new TareaWSRecuperarPedidosPorCocinar();
        tarea.execute();
        // Create ArrayList to hold parent Items and Child Items
        // Create Expandable List and set it's properties
        ExpandableListView expandableList = getExpandableListView();
        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);
        // Set the Items of Parent

        // Set The Child Data
        setChildData();
        // Create the Adapter
        MyExpandableAdapter adapter = new MyExpandableAdapter(parentItems, childItems);
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        // Set the Adapter to expandableList
        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(this);

    }

    // method to add parent Items
    public void setGroupParents() {
        if (listaPedidos != null) {
            for (int i = 0; i < listaPedidos.size(); i++) {
                parentItems.add("Pedido " + i+1);
            }
            System.out.println("ENTRO");
        } else {
            System.out.println("NO ENTRO");
        }

    }

    // method to set child data of each parent
    public void setChildData() {

        // Add Child Items for Fruits
        ArrayList<String> child = new ArrayList<String>();
        child.add("Margarita");
        child.add("Barbacoa");
        child.add("4 Quesos");

        childItems.add(child);

        // Add Child Items for Flowers
        child = new ArrayList<String>();
        child.add("Jamón y Queso");

        childItems.add(child);

        // Add Child Items for Animals
        child = new ArrayList<String>();
        child.add("Barbacoa");
        child.add("Tropical");

        childItems.add(child);

        // Add Child Items for Birds
        child = new ArrayList<String>();
        child.add("5 Quesos");
        child.add("Capricho Ibérico");

        childItems.add(child);
    }

    class TareaWSRecuperarPedidosPorCocinar extends AsyncTask<Object, Void, Boolean> {
        JSONArray listadoPedidosJSON;


        @Override
        protected Boolean doInBackground(Object... params) {
            BufferedReader reader;
            URL url;
            try {
                url = new URL(Settings.DIRECCIO_SERVIDOR + "ServcioFoodXPlorer/webresources/generic/obtenerPedidosPorCocinar");
                reader = getBufferedReader(url);
                listadoPedidosJSON = new JSONArray(reader.readLine());
            } catch (java.io.FileNotFoundException ex) {
                Log.e(LOGTAG, "Error");
            } catch (java.io.IOException ex) {
                Log.e(LOGTAG, "Temps d'espera esgotat al iniciar la conexio amb la BBDD externa:");
            } catch (org.json.JSONException ex) {
                Log.e(LOGTAG, "Error en la transformacio de l'objecte JSON: " + ex);
            }
            return true;
        }

        private BufferedReader getBufferedReader(URL url) throws java.io.IOException {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // conn.setReadTimeout(10000 /*milliseconds*/);
            // conn.setConnectTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/json");
            return new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                try {
                    rellenarArray();
                    System.out.println("ARRAY RELLENADO");
                    setGroupParents();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        private void rellenarArray() throws JSONException {
            listaPedidos = new ArrayList();
            for (int i = 0; i < listadoPedidosJSON.length(); i++) {
                JSONObject jsonobject = listadoPedidosJSON.getJSONObject(i);
                Pedido pedido = new Pedido(jsonobject.getLong("idPedido"), jsonobject.getString("fechaSalida"),
                        jsonobject.getLong("idDireccion"), jsonobject.getLong("idEstado"));
                listaPedidos.add(pedido);
            }
        }
    }

}
