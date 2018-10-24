package com.example.akn.gestionecoleexcellence.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.akn.gestionecoleexcellence.R;

public class AccueilActivity extends AppCompatActivity {

    private Button btnGestionClasse,btnGestionEleve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        btnGestionClasse = (Button)findViewById(R.id.btnGestionClasse);
        btnGestionEleve = (Button)findViewById(R.id.btnGestionEleve);

        btnGestionClasse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccueilActivity.this,ClassRoomActivity.class));
            }
        });

        btnGestionEleve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccueilActivity.this,EleveActivity.class));

            }
        });

    }
}
