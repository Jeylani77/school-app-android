package com.example.akn.gestionecoleexcellence.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import com.example.akn.gestionecoleexcellence.Adapter.ClassRoomAdapter;
import com.example.akn.gestionecoleexcellence.Models.ClassRoom;
import com.example.akn.gestionecoleexcellence.R;
import io.realm.Realm;
import io.realm.RealmResults;

import java.util.UUID;

public class ClassRoomActivity extends AppCompatActivity {

    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText taskEditText = new EditText(ClassRoomActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(ClassRoomActivity.this);
                builder.setTitle("Ajouter Classe");
                builder.setView(taskEditText);
                builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                ClassRoom classRoom = realm.createObject(ClassRoom.class, UUID.randomUUID().toString());
                                classRoom.setNom(String.valueOf(taskEditText.getText()));
                                classRoom.setTimestamp(System.currentTimeMillis());
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

        RealmResults<ClassRoom> classrooms = realm.where(ClassRoom.class).findAll();
        final ClassRoomAdapter adapter = new ClassRoomAdapter(this, classrooms);

        ListView listView = (ListView) findViewById(R.id.classroom_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ClassRoom classRoom = (ClassRoom) parent.getAdapter().getItem(position);
                final EditText taskEditText = new EditText(ClassRoomActivity.this);
                taskEditText.setText(classRoom.getNom());
                AlertDialog dialog = new AlertDialog.Builder(ClassRoomActivity.this)
                        .setTitle("Modifier Classe")
                        .setView(taskEditText)
                        .setPositiveButton("Sauvegarder", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // TODO: 5/4/17 Save Edited Task
                                changeClassroomName(classRoom.getId(), String.valueOf(taskEditText.getText()));
                            }
                        })
                        .setNegativeButton("Supprimer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // TODO: 5/4/17 Delete Task
                                deleteClassroom(classRoom.getId());
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


    private void changeClassroomName(final String classroomId, final String name) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ClassRoom classRoom = realm.where(ClassRoom.class).equalTo("id", classroomId).findFirst();
                classRoom.setNom(name);
            }
        });
    }

    private void deleteClassroom(final String classroomId) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(ClassRoom.class).equalTo("id", classroomId)
                        .findFirst()
                        .deleteFromRealm();
            }
        });
    }

    private void deleteAll() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(ClassRoom.class)
                        .findAll()
                        .deleteAllFromRealm();
                realm.where(ClassRoom.class)
                        .findAll()
                        .deleteAllFromRealm();
            }
        });
    }
}
