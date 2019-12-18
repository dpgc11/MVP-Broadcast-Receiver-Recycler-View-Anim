package com.example.finin.di.module;

import android.app.Activity;
import android.content.Context;

import com.example.finin.di.ActivityContext;
import com.example.finin.di.PerActivity;
import com.example.finin.ui.main.MainMvpPresenter;
import com.example.finin.ui.main.MainMvpView;
import com.example.finin.ui.main.MainPresenter;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

import static com.example.finin.rest.ApiClient.getOkHttpClient;

/**
 * Created by yogesh
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    Picasso providePicasso() {

        return new Picasso.Builder(provideContext())
                .downloader(new OkHttp3Downloader(getOkHttpClient()))
                .build();
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }


    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView>
                                                                              presenter) {
        return presenter;
    }



}
