package com.android.settings.preferences;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;

import androidx.preference.DropDownPreference;

public class SystemSettingDropDownPreference extends DropDownPreference {

    public SystemSettingDropDownPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SystemSettingDropDownPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SystemSettingDropDownPreference(Context context) {
        super(context);
    }

    @Override
    protected boolean persistString(String value) {
        if (shouldPersist()) {
            if (value == getPersistedString(null)) {
                // It's already there, so the same as persisting
                return true;
            }
            Settings.System.putString(getContext().getContentResolver(), getKey(), value);
            return true;
        }
        return false;
    }

    @Override
    protected String getPersistedString(String defaultReturnValue) {
        if (!shouldPersist()) {
            return defaultReturnValue;
        }
        return Settings.System.getString(getContext().getContentResolver(), getKey());
    }
}
