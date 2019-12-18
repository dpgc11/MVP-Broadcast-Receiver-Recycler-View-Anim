package com.example.finin.data.prefs;

/**
 * Created by yogesh
 */

public interface PreferencesHelper {

    String getCurrentUserProfileId();

    void setCurrentUserProfileId(String profileId);

    void clearCurrentUserData();
}
