package com.foodxplorer.tiendafoodxplorer;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.foodxplorer.tiendafoodxplorer.helper.Settings;
import com.foodxplorer.tiendafoodxplorer.model.InfoPedidoRepartidorActivity;
import com.foodxplorer.tiendafoodxplorer.model.Pedido;
import com.foodxplorer.tiendafoodxplorer.model.Producto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.foodxplorer.tiendafoodxplorer.helper.Settings.LOGTAG;

public class RepartidorActivity extends ExpandableListActivity implements ExpandableListView.OnGroupClickListener {
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();
    private ExpandableListView expandableList;
    private ArrayList<Pedido> listaPedidos;
    private ArrayList<Producto> listaProductos;
    private int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new TareaWSRecuperarPedidosParaRepartir().execute();
        // Create ArrayList to hold parent Items and Child Items
        // Create Expandable List and set it's properties
        expandableList = getExpandableListView();
        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);
        // Set the Items of Parent
        // Set The Child Data
        // Create the Adapter

        expandableList.setOnChildClickListener(this);
        expandableList.setOnGroupClickListener(this);
    }

    // method to add parent Items
    public void setGroupParents() {
        if (listaPedidos != null) {
            TareaWSRecuperarProductosPedido tareaProductos = new TareaWSRecuperarProductosPedido();
            for (int i = 0; i < listaPedidos.size(); i++) {
                parentItems.add("Pedido " + (i + 1));
            }
//            for (int i = 0; i < listaPedidos.size(); i++) {
//
//            }
            tareaProductos.setIndice(cont);
            tareaProductos.execute();
            System.out.println("ENTRO");
        } else {
            System.out.println("NO ENTRO");
        }

    }


    // method to set child data of each parent
    public void setChildData() {
        ArrayList<String> child = new ArrayList();
        for (Producto p : listaProductos) {
            child.add(p.getNombre());
        }
        childItems.add(child);
        cont++;
        System.out.println(cont);
        if (cont >= listaPedidos.size()) {
            MyExpandableAdapter adapter = new MyExpandableAdapter(parentItems, childItems);
            adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), RepartidorActivity.this);
            // Set the Adapter to expandableList
            expandableList.setAdapter(adapter);
            for (int i = 0; i < adapter.getGroupCount(); i++)
                expandableList.expandGroup(i);
        } else {
            TareaWSRecuperarProductosPedido tareaProductos = new TareaWSRecuperarProductosPedido();
            tareaProductos.setIndice(cont);
            tareaProductos.execute();
        }
    }


    @Override
    public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
        Intent in = new Intent(this, InfoPedidoRepartidorActivity.class);
        in.putExtra("pedido", listaPedidos.get(i));
        startActivity(in);
        return true;
    }

    class TareaWSRecuperarPedidosParaRepartir extends AsyncTask<Object, Void, Boolean> {
        JSONArray listadoPedidosJSON;

        @Override
        protected Boolean doInBackground(Object... params) {
            BufferedReader reader;
            URL url;
            try {
                url = new URL(Settings.DIRECCIO_SERVIDOR + "ServcioFoodXPlorer/webresources/generic/obtenerPedidosParaRepartir");
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
                    if (rellenarArray()) {
                        System.out.println("ARRAY RELLENADO");
                        setGroupParents();
                    } else {
                        Toast.makeText(RepartidorActivity.this, "NO HAY PEDIDOS", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        private boolean rellenarArray() throws JSONException {
            boolean estado;
            if (listadoPedidosJSON.length() > 0) {
                listaPedidos = new ArrayList();

                for (int i = 0; i < listadoPedidosJSON.length(); i++) {
                    JSONObject jsonobject = listadoPedidosJSON.getJSONObject(i);
                    Pedido pedido = new Pedido(jsonobject.getLong("idPedido"), jsonobject.getString("fechaSalida"),
                            jsonobject.getLong("idDireccion"), jsonobject.getLong("idEstado"), jsonobject.getString("correo"));
                    listaPedidos.add(pedido);
                }
                estado = true;
            } else {
                estado = false;
            }
            return estado;
        }
    }

    class TareaWSRecuperarProductosPedido extends AsyncTask<Object, Void, Boolean> {
        JSONArray productosJSON;
        int indice;

        public void setIndice(int indice) {
            this.indice = indice;
        }

        @Override
        protected Boolean doInBackground(Object... params) {
            BufferedReader reader;
            URL url;
            try {
                System.out.println(cont);
                url = new URL(Settings.DIRECCIO_SERVIDOR + "ServcioFoodXPlorer/webresources/generic/obtenerProductosPorIdPedido/" + listaPedidos.get(indice).getIdPedido());
                reader = getBufferedReader(url);
                productosJSON = new JSONArray(reader.readLine());
            } catch (java.io.FileNotFoundException ex) {
                Log.e(LOGTAG, "Error al obtener los productos");
            } catch (java.io.IOException ex) {
                Log.e(LOGTAG, "Temps d'espera esgotat al iniciar la conexio amb la BBDD externa:");
            } catch (JSONException e) {
                e.printStackTrace();
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
                    if (!rellenarArray()) {
                        System.out.println("ERROR");
                    } else {
                        if (cont <= listaPedidos.size()) {
                            setChildData();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        private boolean rellenarArray() throws JSONException {
            boolean estado;
            listaProductos = new ArrayList<>();
            if (productosJSON.length() > 0) {
                for (int i = 0; i < productosJSON.length(); i++) {
                    JSONObject jsonobject = productosJSON.getJSONObject(i);
                    Producto p = new Producto(jsonobject.getInt("idProducto"),
                            jsonobject.getString("nombre"), jsonobject.getString("descripcion"),
                            jsonobject.getDouble("precio"), jsonobject.getInt("iva"),
                            jsonobject.getInt("ofertaDescuento"), jsonobject.getInt("activo"),
                            jsonobject.getInt("idTipoProducto"),
                            jsonobject.getString("urlImagen"));
                    listaProductos.add(p);
                }
                estado = true;
            } else {
                estado = false;
            }
            return estado;
        }
    }

}

