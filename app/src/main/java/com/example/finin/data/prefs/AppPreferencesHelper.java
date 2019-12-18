package com.example.finin.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.finin.di.ApplicationContext;
import com.example.finin.di.PreferenceInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by yogesh
 */

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    public static final String PREF_KEY_CURRENT_USER_PROFILE_ID = "PREF_KEY_CURRENT_USER_PROFILE_ID";
    public final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getCurrentUserProfileId() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_PROFILE_ID, "");
    }

    @Override
    public void setCurrentUserProfileId(String profileId) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PROFILE_ID, profileId).apply();
    }


    @Override
    public void clearCurrentUserData() {
        mPrefs.edit().remove(PREF_KEY_CURRENT_USER_PROFILE_ID).apply();


    }


}
