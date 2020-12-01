package com.alejandro.androidcrud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> listaNombres = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private Button button_aceptarCrearUsuario, button_cancelarCrearUsuario;
    private FloatingActionButton button_insertFlotante;
    private EditText nombreInsertado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listViewNombres = findViewById(R.id.nombresListView);

        listaNombres = new ArrayList<>();
        listaNombres.add("Antonio Domínguez Ávila");
        listaNombres.add("Esther Darias Fernández");
        listaNombres.add("Javier Beltrán Maduro");
        listaNombres.add("Pedro Estévez Moreno");
        listaNombres.add("Ana Simón González");

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1, listaNombres);

        listViewNombres.setAdapter(adapter);
        listViewNombres.setTextFilterEnabled(true);
        registerForContextMenu(listViewNombres);



        button_insertFlotante = findViewById(R.id.buttonInsertarFlotante);
        button_insertFlotante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertUser();
            }
        });
    }

    public void insertUser(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_create, null);

        nombreInsertado = view.findViewById(R.id.nuevaPersona);
        button_aceptarCrearUsuario = view.findViewById(R.id.button_aceptar_insertar_user);
        button_aceptarCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaNombres.add(nombreInsertado.getText().toString());
                adapter.notifyDataSetChanged();
                dialogBuilder.dismiss();
                Toast.makeText(getApplicationContext(), "Agregado " + nombreInsertado.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        button_cancelarCrearUsuario = view.findViewById(R.id.button_cancelarInsertarUsuario);
        button_cancelarCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(view);
        dialogBuilder.show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu_usuario, View view, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_usuario, menu_usuario);
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();
        final String nombre = (listaNombres.get((int) menuInfo.id));

        switch (menuItem.getItemId()) {
            case R.id.borrar_usuario:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("¿Está seguro?")
                        .setMessage("Se va a eliminar a " + nombre)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                listaNombres.remove(nombre);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "Eliminado " + nombre, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            default:
                return super.onContextItemSelected(menuItem);
        }
    }
}