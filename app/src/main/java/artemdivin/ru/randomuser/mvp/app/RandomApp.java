package artemdivin.ru.randomuser.mvp.app;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by admin on 13.12.2017.
 */

public class RandomApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //init realm
        Realm.init(this);

    RealmConfiguration config = new RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build();
        Realm.setDefaultConfiguration(config);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
            .build());

        RealmInspectorModulesProvider.builder(this)
                .withFolder(getCacheDir())
            .withMetaTables()
                .withDescendingOrder()
                .withLimit(1000)
                .build();
}
}
