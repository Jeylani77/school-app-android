package com.example.akn.gestionecoleexcellence.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.example.akn.gestionecoleexcellence.Adapter.ClassRoomAdapter;
import com.example.akn.gestionecoleexcellence.Adapter.EleveAdapter;
import com.example.akn.gestionecoleexcellence.Models.ClassRoom;
import com.example.akn.gestionecoleexcellence.Models.Eleve;
import com.example.akn.gestionecoleexcellence.R;
import io.realm.Realm;
import io.realm.RealmResults;

import java.util.UUID;

public class EleveActivity extends AppCompatActivity {
    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleve);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout layout = new LinearLayout(EleveActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

// Add a TextView here for the "Title" label, as noted in the comments
                final EditText nomEditText = new EditText(EleveActivity.this);
                nomEditText.setHint("Nom Complet ");
                layout.addView(nomEditText); // Notice this is an add method

// Add another TextView here for the "Description" label
                final EditText classeEditText = new EditText(EleveActivity.this);
                classeEditText.setHint("Classe");
                layout.addView(classeEditText); // Another add method


                AlertDialog.Builder builder = new AlertDialog.Builder(EleveActivity.this);
                builder.setTitle("Inscrire Eleve");
                builder.setView(layout);
                builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Eleve eleve = realm.createObject(Eleve.class, UUID.randomUUID().toString());
                                eleve.setNomComplet(String.valueOf(nomEditText.getText()));
                                eleve.setClasse(String.valueOf(classeEditText.getText()));
                                eleve.setTimestamp(System.currentTimeMillis());
                            }
                        });
                    }
                });
                builder.setNegativeButton("Annuler", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        realm.init(this);

        realm = Realm.getDefaultInstance();

        RealmResults<Eleve> eleves = realm.where(Eleve.class).findAll();
        final EleveAdapter adapter = new EleveAdapter(this, eleves);

        ListView listView = (ListView) findViewById(R.id.eleve_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout layout = new LinearLayout(EleveActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

// Add a TextView here for the "Title" label, as noted in the comments
                final EditText nomEditText = new EditText(EleveActivity.this);
                nomEditText.setHint("Nom Complet ");
                layout.addView(nomEditText); // Notice this is an add method

// Add another TextView here for the "Description" label
                final EditText classeEditText = new EditText(EleveActivity.this);
                classeEditText.setHint("Classe");
                layout.addView(classeEditText); // Another add method


                final Eleve eleve = (Eleve) parent.getAdapter().getItem(position);
                nomEditText.setText(eleve.getNomComplet());
                classeEditText.setText(eleve.getClasse());

                AlertDialog dialog = new AlertDialog.Builder(EleveActivity.this)
                        .setTitle("Modifier Eleve")
                        .setView(layout)
                        .setPositiveButton("Sauvegarder", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // TODO: 5/4/17 Save Edited Task
                                changeEleve(eleve.getId(), String.valueOf(nomEditText.getText()), String.valueOf(classeEditText.getText()));
                            }
                        })
                        .setNegativeButton("Supprimer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // TODO: 5/4/17 Delete Task
                                deleteEleve(eleve.getId());
                            }
                        })
                        .create();
                dialog.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_classroom_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_delete) {
            deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }


    private void changeEleve(final String eleveId, final String name, final String classeName) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Eleve eleve = realm.where(Eleve.class).equalTo("id", eleveId).findFirst();
                eleve.setNomComplet(name);
                eleve.setClasse(classeName);
            }
        });
    }

    private void deleteEleve(final String eleveId) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Eleve.class).equalTo("id", eleveId)
                        .findFirst()
                        .deleteFromRealm();
            }
        });
    }

    private void deleteAll() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Eleve.class)
                        .findAll()
                        .deleteAllFromRealm();
                realm.where(Eleve.class)
                        .findAll()
                        .deleteAllFromRealm();
            }
        });
    }

}
