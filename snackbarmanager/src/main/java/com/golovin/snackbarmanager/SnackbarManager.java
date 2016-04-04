package com.golovin.snackbarmanager;

import android.os.Looper;
import android.os.Message;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SnackbarManager {
    private final AppCompatActivity mActivity;

    private final SnackbarHandler mSnackbarHandler;

    public SnackbarManager(AppCompatActivity activity) {
        verifyMainThread();

        mActivity = activity;

        mSnackbarHandler = new SnackbarHandler(this);
    }

    private void verifyMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException(
                    "Expected to be called on the main thread but was " + Thread.currentThread().getName());
        }
    }

    /**
     * Shows snackbar with green #4CAF50 background color.
     */
    public void showSuccess(@StringRes int textRes) {
        showSuccess(textRes, false);
    }

    /**
     * Shows snackbar with default #323232 backgrund color.
     */
    public void showNeutral(@StringRes int textRes) {
        showNeutral(textRes, false);
    }

    /**
     * Shows snackbar with yellow #FFEB3B background color.
     */
    public void showWarning(@StringRes int textRes) {
        showWarning(textRes, false);
    }

    /**
     * Shows snackbar with red #F44336 background color.
     */
    public void showError(@StringRes int textRes) {
        showError(textRes, false);
    }

    /**
     * Shows snackbar with green #4CAF50 background color.
     *
     * @param textRes     text that will be shown
     * @param isImportant important snackbars cannot be dismissed by another snackbars and will be shown
     *                    throughout their duration
     */
    public void showSuccess(@StringRes int textRes, boolean isImportant) {
        showSuccess(mActivity.getString(textRes), isImportant);
    }

    /**
     * Shows snackbar with default #323232 backgrund color.
     *
     * @param textRes     text that will be shown
     * @param isImportant important snackbars cannot be dismissed by another snackbars and will be shown
     *                    throughout their duration
     */
    public void showNeutral(@StringRes int textRes, boolean isImportant) {
        showNeutral(mActivity.getString(textRes), isImportant);
    }

    /**
     * Shows snackbar with yellow #FFEB3B backgrund color.
     *
     * @param textRes     text that will be shown
     * @param isImportant important snackbars cannot be dismissed by another snackbars and will be shown
     *                    throughout their duration
     */
    public void showWarning(@StringRes int textRes, boolean isImportant) {
        showWarning(mActivity.getString(textRes), isImportant);
    }

    /**
     * Shows snackbar with red #F44336 backgrund color.
     *
     * @param textRes     text that will be shown
     * @param isImportant important snackbars cannot be dismissed by another snackbars and will be shown
     *                    throughout their duration
     */
    public void showError(@StringRes int textRes, boolean isImportant) {
        showError(mActivity.getString(textRes), isImportant);
    }

    public void showSuccess(String text) {
        showSuccess(text, false);
    }

    public void showNeutral(String text) {
        showNeutral(text, false);
    }

    public void showWarning(String text) {
        showWarning(text, false);
    }

    public void showError(String text) {
        showError(text, false);
    }

    public void showSuccess(String text, boolean isImportant) {
        SnackbarOptions options = new SnackbarOptions()
                .setBackgroundColor(R.color.green_500)
                .setIsImportant(isImportant);
        show(text, options);
    }

    public void showNeutral(String text, boolean isImportant) {
        SnackbarOptions options = new SnackbarOptions()
                .setIsImportant(isImportant);

        show(text, options);
    }

    public void showWarning(String text, boolean isImportant) {
        SnackbarOptions options = new SnackbarOptions()
                .setBackgroundColor(R.color.yellow_500)
                .setIsImportant(isImportant);

        show(text, options);
    }

    public void showError(String text, boolean isImportant) {
        SnackbarOptions options = new SnackbarOptions()
                .setBackgroundColor(R.color.red_500)
                .setIsImportant(isImportant);

        show(text, options);
    }

    /**
     * Shows snackbar with specified {@link SnackbarOptions}.
     */
    public void show(@StringRes int textRes, SnackbarOptions options) {
        show(mActivity.getString(textRes), options);
    }

    /**
     * Shows snackbar with specified {@link SnackbarOptions}.
     */
    public void show(String text, SnackbarOptions options) {
        putToMessageQueue(text, options);
    }

    private void putToMessageQueue(String text, SnackbarOptions options) {
        SnackbarMessage snackbarMessage = new SnackbarMessage(text, options);
        Message message = mSnackbarHandler.obtainMessage(SnackbarHandler.MESSAGE_NEW, snackbarMessage);

        mSnackbarHandler.sendMessage(message);
    }

    void showSnackbar(String text, SnackbarOptions options) {
        //noinspection ConstantConditions,ResourceType
        Snackbar snackbar = Snackbar.make(mActivity.findViewById(android.R.id.content), text, options.getDuration());

        View view = snackbar.getView();
        view.setBackgroundColor(ContextCompat.getColor(mActivity, options.getBackgroundColor()));

        TextView textView = (TextView) view.findViewById(R.id.snackbar_text);
        textView.setMaxLines(options.getMaxLines());
        textView.setTextColor(ContextCompat.getColor(mActivity, options.getTextColor()));

        if (options.hasAction()) {
            snackbar.setAction(options.getActionTextId(), options.getActionListener());

            if (options.hasActionTextColor()) {
                snackbar.setActionTextColor(ContextCompat.getColor(mActivity, options.getActionTextColor()));
            } else if (options.hasActionColors()) {
                snackbar.setActionTextColor(options.getActionColors());
            }
        }

        if (options.isImportant()) {
            snackbar.setCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    Message message = mSnackbarHandler.obtainMessage(SnackbarHandler.MESSAGE_DISMISSED);
                    mSnackbarHandler.sendMessage(message);
                }
            });
        }

        snackbar.show();
    }
}
