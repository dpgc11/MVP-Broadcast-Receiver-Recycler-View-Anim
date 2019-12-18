package com.example.finin;

import android.app.Application;
import android.content.Context;

import com.example.finin.di.component.ApplicationComponent;
import com.example.finin.di.component.DaggerApplicationComponent;
import com.example.finin.di.module.ApplicationModule;
import com.example.finin.receivers.ConnectivityReceiver;


public class fininApp extends Application {

    private ApplicationComponent mApplicationComponent;

    public static fininApp get(Context context) {
        return (fininApp) context.getApplicationContext();
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    public ConnectivityReceiver.ConnectivityReceiverListener getConnectivityListener() {
       return ConnectivityReceiver.connectivityReceiverListener;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
