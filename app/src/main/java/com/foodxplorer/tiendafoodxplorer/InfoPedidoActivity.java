package com.foodxplorer.tiendafoodxplorer;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.foodxplorer.tiendafoodxplorer.adapters.AdaptadorProducto;
import com.foodxplorer.tiendafoodxplorer.helper.Settings;
import com.foodxplorer.tiendafoodxplorer.model.Direccion;
import com.foodxplorer.tiendafoodxplorer.model.LineasPedido;
import com.foodxplorer.tiendafoodxplorer.model.Pedido;
import com.foodxplorer.tiendafoodxplorer.model.Producto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.foodxplorer.tiendafoodxplorer.helper.Settings.LOGTAG;

public class InfoPedidoActivity extends AppCompatActivity implements View.OnClickListener {
    private Pedido pedido;
    private TextView textViewNombreCliente, textViewDireccion, textViewImporte;
    private Button btnListoRepartir;
    private final int idEstado = 2;
    private ListView listView;
    private ArrayAdapter<String> adaptador;
    private AdaptadorProducto adapProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pedido);
        List<String> lista = new ArrayList();
        lista.add("Barbacoa");
        lista.add("4 Quesos");
        //List<String> listaEj = new ArrayList();
//        listaEj.add("Por hacer");
//        listaEj.add("Listo para entregar");
//        listaEj.add("En reparto");
//        listaEj.add("Entregado");
        listView = (ListView) findViewById(R.id.listViewInfoPedido);
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listView.setAdapter(adaptador);
        Intent i = getIntent();
        pedido = (Pedido) i.getSerializableExtra("pedido");
        Toast.makeText(this, pedido.toString(), Toast.LENGTH_SHORT).show();
        textViewNombreCliente = (TextView) findViewById(R.id.textViewNombreCliente);
        textViewNombreCliente.setText(pedido.getCorreo());
        textViewDireccion = (TextView) findViewById(R.id.textViewDireccion);
        textViewImporte = (TextView) findViewById(R.id.textViewImporte);
        btnListoRepartir = (Button) findViewById(R.id.btnEntregado);
        btnListoRepartir.setOnClickListener(this);
        new TareaWSRecuperarDireccion().execute();
        new TareaWSRecuperarLineasPedido().execute();
        new TareaWSRecuperarProductosPedido().execute();
    }

    @Override
    public void onClick(View view) {
        new TareaWSActualizarEstado().execute(idEstado, pedido.getIdPedido());
        btnListoRepartir.setEnabled(false);
    }

    class TareaWSRecuperarDireccion extends AsyncTask<Object, Void, Boolean> {
        JSONObject direccionJSON;
        Direccion direccionObject;

        @Override
        protected Boolean doInBackground(Object... params) {
            try {
                direccionJSON = readJsonFromUrl(Settings.DIRECCIO_SERVIDOR + Settings.PATH + "direccion/obtener2/" + pedido.getIdDireccion());
            } catch (java.io.FileNotFoundException ex) {
                Log.e(LOGTAG, "Error al obtener la direccion");
            } catch (java.io.IOException ex) {
                Log.e(LOGTAG, "Temps d'espera esgotat al iniciar la conexio amb la BBDD externa:");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }


        private String readAll(Reader rd) throws IOException {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            return sb.toString();
        }

        private JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
            InputStream is = new URL(url).openStream();
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                JSONObject json = new JSONObject(jsonText);
                return json;
            } finally {
                is.close();
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                try {
                    if (!rellenarObjeto()) {
                        Toast.makeText(InfoPedidoActivity.this, "NO EXISTE ESE NUMERO DE PEDIDO", Toast.LENGTH_SHORT).show();
                    } else {
                        textViewDireccion.setText(direccionObject.getPiso() + "\n" + direccionObject.getCalle() + "\n" + direccionObject.getPoblacion());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        private boolean rellenarObjeto() throws JSONException {
            boolean estado;
            if (direccionJSON != null) {
                direccionObject = new Direccion(direccionJSON.getLong("idDireccion"), direccionJSON.getString("calle"), direccionJSON.getString("piso"),
                        direccionJSON.getString("poblacion"), direccionJSON.getString("codPostal"));
                estado = true;
            } else {
                estado = false;
            }
            return estado;
        }
    }

    class TareaWSRecuperarLineasPedido extends AsyncTask<Object, Void, Boolean> {
        JSONArray lineasPedidoJSON;
        int importeTotal;
        ArrayList<LineasPedido> listaLineasPedido;

        @Override
        protected Boolean doInBackground(Object... params) {
            BufferedReader reader;
            URL url = null;
            try {
                url = new URL(Settings.DIRECCIO_SERVIDOR + Settings.PATH + "pedido/obtenerDetalles/" + pedido.getIdPedido());
                reader = getBufferedReader(url);
                lineasPedidoJSON = new JSONArray(reader.readLine());

            } catch (java.io.FileNotFoundException ex) {
                Log.e(LOGTAG, "Error al obtener la direccion");
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
                        Toast.makeText(InfoPedidoActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    } else {
                        for (LineasPedido linea : listaLineasPedido) {
                            importeTotal += linea.getPrecio();
                        }
                        textViewImporte.setText(String.valueOf(importeTotal) + "€");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        private boolean rellenarArray() throws JSONException {
            boolean estado;
            listaLineasPedido = new ArrayList<>();
            if (lineasPedidoJSON.length() > 0) {
                for (int i = 0; i < lineasPedidoJSON.length(); i++) {
                    JSONObject jsonobject = lineasPedidoJSON.getJSONObject(i);
                    LineasPedido lineasPedido = new LineasPedido(jsonobject.getLong("idPedido"), jsonobject.getLong("idProducto"), jsonobject.getInt("cantidad"),
                            jsonobject.getDouble("precio"), jsonobject.getInt("iva"));
                    listaLineasPedido.add(lineasPedido);
                }
                estado = true;
            } else {
                estado = false;
            }
            return estado;
        }
    }

    class TareaWSActualizarEstado extends AsyncTask<Object, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Object... params) {
            boolean insertadoEnDBexterna = true;
            OutputStreamWriter osw;
            try {
                URL url = new URL(Settings.DIRECCIO_SERVIDOR + Settings.PATH + "actualizarEstado");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setReadTimeout(1000 /*milliseconds*/);
                conn.setConnectTimeout(500);
                conn.setRequestProperty("Content-Type", "application/json");
                osw = new OutputStreamWriter(conn.getOutputStream());
                osw.write(getStringJSON(params));
                osw.flush();
                osw.close();
                System.err.println(conn.getResponseMessage());
            } catch (java.io.IOException ex) {
                Log.e(LOGTAG, "Temps d'espera esgotat al iniciar la conexio amb la BBDD extena");
                insertadoEnDBexterna = false;
            } catch (org.json.JSONException ex) {
                Log.e(LOGTAG, "Error en la transformacio de l'objecte JSON: " + ex);
                insertadoEnDBexterna = false;
            }
            return insertadoEnDBexterna;
        }

        private String getStringJSON(Object... params) throws JSONException, UnsupportedEncodingException {
            JSONObject dato = new JSONObject();
            dato.put("idEstado", params[0]);
            dato.put("idPedido", params[1]);
            Log.d(LOGTAG, "El estado que se insertara es:" + dato.toString());
            return String.valueOf(dato);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

        }
    }

    class TareaWSRecuperarProductosPedido extends AsyncTask<Object, Void, Boolean> {
        JSONArray productosJSON;
        ArrayList<Producto> listaProductos;

        @Override
        protected Boolean doInBackground(Object... params) {
            BufferedReader reader;
            URL url;
            try {
                url = new URL(Settings.DIRECCIO_SERVIDOR + Settings.PATH + "obtenerProductosPorIdPedido/" + pedido.getIdPedido());
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
                        Toast.makeText(InfoPedidoActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    } else {
                        adapProducto = new AdaptadorProducto(InfoPedidoActivity.this, listaProductos);
                        listView.setAdapter(adapProducto);
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
