package com.example.akn.gestionecoleexcellence.Application;

import android.app.Application;
import com.example.akn.gestionecoleexcellence.Models.Migration;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ClassRoomListApplication  extends Application {

    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("school.realm")
                .schemaVersion(1)
                .migration(new Migration())
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
