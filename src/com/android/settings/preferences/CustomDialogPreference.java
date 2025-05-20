package com.android.settings.preferences;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.DialogPreference;
import androidx.fragment.app.DialogFragment;

public abstract class CustomDialogPreference<T extends AlertDialog> extends DialogPreference {
    private DialogInterface.OnClickListener mPositiveButtonListener;
    private DialogInterface.OnClickListener mNegativeButtonListener;
    private DialogInterface.OnDismissListener mDismissListener;
    private T mDialog;

    public CustomDialogPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomDialogPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDialogPreference(Context context) {
        super(context);
    }

    public void setOnPositiveButtonClickListener(DialogInterface.OnClickListener listener) {
        mPositiveButtonListener = listener;
    }

    public void setOnNegativeButtonClickListener(DialogInterface.OnClickListener listener) {
        mNegativeButtonListener = listener;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        mDismissListener = listener;
    }

    protected DialogInterface.OnClickListener getPositiveButtonListener() {
        return mPositiveButtonListener;
    }

    protected DialogInterface.OnClickListener getNegativeButtonListener() {
        return mNegativeButtonListener;
    }

    protected DialogInterface.OnDismissListener getDismissListener() {
        return mDismissListener;
    }

    public T getDialog() {
        return mDialog;
    }

    protected void setDialog(T dialog) {
        mDialog = dialog;
    }

    protected void onPrepareDialogBuilder(AlertDialog.Builder builder,
            DialogInterface.OnClickListener listener) {
        builder.setPositiveButton(null, null);
        builder.setNegativeButton(null, null);
    }

    protected void onDialogClosed(boolean positiveResult) {
        // Override in subclasses
    }

    protected void onBindDialogView(View view) {
        // Override in subclasses
    }

    public abstract DialogFragment getDialogFragment();
}
