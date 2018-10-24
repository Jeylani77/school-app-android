package com.example.akn.gestionecoleexcellence.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.example.akn.gestionecoleexcellence.Activity.ClassRoomActivity;
import com.example.akn.gestionecoleexcellence.Models.ClassRoom;

import com.example.akn.gestionecoleexcellence.R;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;


public class ClassRoomAdapter extends RealmBaseAdapter<ClassRoom> implements ListAdapter {

    private ClassRoomActivity activity;

    private static class ViewHolder {
        TextView classRoomNom;

    }

    public ClassRoomAdapter(ClassRoomActivity activity, OrderedRealmCollection<ClassRoom> data) {
        super(data);
        this.activity = activity;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.class_room_list_row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.classRoomNom = (TextView) convertView.findViewById(R.id.classroom_item_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (adapterData != null) {
            ClassRoom classRoom = adapterData.get(position);
            viewHolder.classRoomNom.setText(classRoom.getNom());
             }

        return convertView;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           /*
            int position = (Integer) view.getTag();
            if (adapterData != null) {
                ClassRoom task = adapterData.get(position);
                activity.changeTaskDone(task.getId());
            } */
        }
    };

}
