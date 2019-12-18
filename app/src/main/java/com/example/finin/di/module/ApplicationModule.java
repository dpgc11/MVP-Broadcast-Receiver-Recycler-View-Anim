
package com.example.finin.di.module;

import android.app.Application;
import android.content.Context;

import com.example.finin.data.AppDataManager;
import com.example.finin.data.DataManager;
import com.example.finin.data.prefs.AppPreferencesHelper;
import com.example.finin.data.prefs.PreferencesHelper;
import com.example.finin.di.ApplicationContext;
import com.example.finin.di.PreferenceInfo;
import com.example.finin.utils.AppConstants;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.example.finin.rest.ApiClient.getOkHttpClient;

/**
 * Created by Yogesh on 27/01/17.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }


    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    Picasso providePicasso() {

        return new Picasso.Builder(provideContext())
                .downloader(new OkHttp3Downloader(getOkHttpClient()))
                .build();
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }
}
