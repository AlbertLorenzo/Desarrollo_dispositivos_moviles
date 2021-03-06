package es.umh.dadm.appcontext;

import android.app.Application;
import android.content.Context;
import es.umh.dadm.category.CategoryWrapper;

public class App extends Application {
    private static Application app;

    public static Application getApplication() {
        return app;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
