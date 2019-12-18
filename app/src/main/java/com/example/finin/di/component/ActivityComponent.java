package com.example.finin.di.component;

import com.example.finin.di.PerActivity;
import com.example.finin.di.module.ActivityModule;
import com.example.finin.ui.main.MainActivity;

import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);


}
