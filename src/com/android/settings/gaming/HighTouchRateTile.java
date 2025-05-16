/*
 * Copyright (C) 2025 Yet Another AOSP Extended Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.settings.gaming;

import android.content.Context;
import android.content.Intent;
import android.os.SystemProperties;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import com.android.settings.R;

public class HighTouchRateTile extends TileService {

    private static final String GAMING_MODE_KEY = "gaming_mode_high_touch_rate";
    private static final String DISPLAY_KEY = Settings.Secure.HIGH_TOUCH_RATE_ENABLED;
    private static final String PROP_NAME = "debug.high_touch_rate";

    @Override
    public void onStartListening() {
        super.onStartListening();
        updateState();
    }

    @Override
    public void onClick() {
        super.onClick();
        boolean enabled = !isEnabled();
        // Update gaming mode setting
        Settings.System.putInt(getContentResolver(), GAMING_MODE_KEY, enabled ? 1 : 0);
        // Update display setting
        Settings.Secure.putInt(getContentResolver(), DISPLAY_KEY, enabled ? 1 : 0);
        // Update system property
        SystemProperties.set(PROP_NAME, enabled ? "1" : "0");
        updateState();
    }

    private void updateState() {
        boolean enabled = isEnabled();
        getQsTile().setState(enabled ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        getQsTile().setLabel(getString(R.string.touch_rate_title));
        getQsTile().updateTile();
    }

    private boolean isEnabled() {
        // Check both settings - if either is enabled, consider the feature enabled
        boolean gamingEnabled = Settings.System.getInt(getContentResolver(), GAMING_MODE_KEY, 0) == 1;
        boolean displayEnabled = Settings.Secure.getInt(getContentResolver(), DISPLAY_KEY, 0) == 1;
        return gamingEnabled || displayEnabled;
    }
}
