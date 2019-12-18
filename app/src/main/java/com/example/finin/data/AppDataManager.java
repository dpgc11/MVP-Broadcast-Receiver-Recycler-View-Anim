package com.example.finin.data;


import android.content.Context;

import com.example.finin.data.prefs.PreferencesHelper;
import com.example.finin.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Yogesh
 */

@Singleton
public class AppDataManager implements DataManager {

    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          PreferencesHelper preferencesHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public String getCurrentUserProfileId() {
        return mPreferencesHelper.getCurrentUserProfileId();
    }

    @Override
    public void setCurrentUserProfileId(String profileId) {
        mPreferencesHelper.setCurrentUserProfileId(profileId);
    }

    @Override
    public void clearCurrentUserData() {
        mPreferencesHelper.clearCurrentUserData();
    }
}


