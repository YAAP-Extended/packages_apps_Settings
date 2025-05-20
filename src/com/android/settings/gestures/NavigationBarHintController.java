package com.android.settings.gestures;

import android.content.Context;
import android.provider.Settings;
import android.view.WindowManager;
import android.view.Window;
import android.app.Activity;
import android.view.View;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;

public class NavigationBarHintController extends TogglePreferenceController {

    public NavigationBarHintController(Context context, String key) {
        super(context, key);
    }

    @Override
    public boolean isChecked() {
        return Settings.Secure.getInt(mContext.getContentResolver(),
                Settings.Secure.NAVIGATION_BAR_HINT, 1) == 1;
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        boolean result = Settings.Secure.putInt(mContext.getContentResolver(),
                Settings.Secure.NAVIGATION_BAR_HINT, isChecked ? 1 : 0);
        
        // Обновляем видимость навбара
        if (result && mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            Window window = activity.getWindow();
            if (window != null) {
                int visibility = window.getDecorView().getSystemUiVisibility();
                if (isChecked) {
                    visibility |= View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN;
                } else {
                    visibility &= ~(View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
                }
                window.getDecorView().setSystemUiVisibility(visibility);
            }
        }
        
        return result;
    }

    @Override
    public int getAvailabilityStatus() {
        return SystemNavigationPreferenceController.isGestureNavigationEnabled(mContext) 
            ? AVAILABLE : UNSUPPORTED_ON_DEVICE;
    }

    @Override
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_system;
    }
}
