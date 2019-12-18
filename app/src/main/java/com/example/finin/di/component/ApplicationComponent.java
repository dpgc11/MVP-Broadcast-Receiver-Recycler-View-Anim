package com.example.finin.di.component;

import android.app.Application;
import android.content.Context;

import com.example.finin.data.DataManager;
import com.example.finin.di.ApplicationContext;
import com.example.finin.di.module.ApplicationModule;
import com.example.finin.fininApp;
import com.example.finin.ui.adapter.UserDataAdapter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Yogesh on 27/01/17.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(fininApp app);

    void inject(UserDataAdapter adapter);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}
