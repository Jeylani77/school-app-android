package com.example.akn.gestionecoleexcellence.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.example.akn.gestionecoleexcellence.Activity.EleveActivity;
import com.example.akn.gestionecoleexcellence.Models.Eleve;
import com.example.akn.gestionecoleexcellence.R;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class EleveAdapter extends RealmBaseAdapter<Eleve> implements ListAdapter {

    private EleveActivity activity;

    public EleveAdapter(EleveActivity activity, OrderedRealmCollection<Eleve> data) {
        super(data);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final EleveAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.eleve_row, parent, false);
            viewHolder = new EleveAdapter.ViewHolder();
            viewHolder.eleveNom = (TextView) convertView.findViewById(R.id.eleve_item_name);
            viewHolder.classeNom = (TextView) convertView.findViewById(R.id.classe_item_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (EleveAdapter.ViewHolder) convertView.getTag();
        }

        if (adapterData != null) {
            Eleve eleve = adapterData.get(position);
            viewHolder.eleveNom.setText(eleve.getNomComplet());
            viewHolder.classeNom.setText(eleve.getClasse());
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView eleveNom, classeNom;

    }



}
