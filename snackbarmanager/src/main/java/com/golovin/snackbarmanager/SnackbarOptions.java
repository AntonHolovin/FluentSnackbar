package com.golovin.snackbarmanager;

import android.content.res.ColorStateList;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackbarOptions {
    private int mMaxLines;

    private int mTextColor;

    private int mBackgroundColor;

    private boolean mIsImportant;

    private int mDuration;

    private int mActionTextId;
    private View.OnClickListener mActionListener;
    private int mActionTextColor;
    private ColorStateList mActionColors;

    public SnackbarOptions() {
        mMaxLines = 1;
        mTextColor = android.R.color.white;
        mBackgroundColor = R.color.default_background;
        mIsImportant = false;
        mDuration = Snackbar.LENGTH_LONG;
    }

    /**
     * Sets duration of snackbar. See {@link android.support.design.widget.Snackbar#setDuration(int)}. Default value is
     * {@link Snackbar#LENGTH_LONG}.
     *
     * @return same instance of {@link SnackbarOptions}
     */
    public SnackbarOptions setDuration(@Snackbar.Duration int duration) {
        mDuration = duration;
        return this;
    }

    /**
     * Sets max amount lines in snackbar's TextView. See {@link android.widget.TextView#setMaxLines(int)}. Default value
     * is 1.
     *
     * @return same instance of {@link SnackbarOptions}
     */
    public SnackbarOptions setMaxLines(int maxLines) {
        mMaxLines = maxLines;
        return this;
    }

    /**
     * Sets color of the text. Default color is white.
     *
     * @return same instance of {@link SnackbarOptions}
     */
    public SnackbarOptions setTextColor(@ColorRes int textColor) {
        mTextColor = textColor;
        return this;
    }

    /**
     * Sets bacground color of snackbar. Default color is #323232.
     *
     * @return same instance of {@link SnackbarOptions}
     */
    public SnackbarOptions setBackgroundColor(@ColorRes int backgroundColor) {
        mBackgroundColor = backgroundColor;
        return this;
    }

    /**
     * Sets is snackbar important. Important snackbars cannot be dismissed by another snackbars and will be shown
     * throughout their duration. Default value is false.
     *
     * @return same instance of {@link SnackbarOptions}
     */
    public SnackbarOptions setIsImportant(boolean isImportant) {
        mIsImportant = isImportant;
        return this;
    }

    /**
     * See {@link Snackbar#setAction(int, View.OnClickListener)}.
     *
     * @return same instance of {@link SnackbarOptions}
     */
    public SnackbarOptions setAction(@StringRes int actionText, View.OnClickListener listener) {
        mActionTextId = actionText;
        mActionListener = listener;
        return this;
    }

    /**
     * See {@link Snackbar#setAction(int, View.OnClickListener)} and {@link Snackbar#setActionTextColor(int)}.
     *
     * @return same instance of {@link SnackbarOptions}
     */
    public SnackbarOptions setAction(@StringRes int actionText, @ColorRes int textColor, View.OnClickListener listener) {
        mActionTextId = actionText;
        mActionListener = listener;
        mActionTextColor = textColor;
        return this;
    }

    /**
     * See {@link Snackbar#setAction(int, View.OnClickListener)} and {@link Snackbar#setActionTextColor(ColorStateList)}.
     *
     * @return same instance of {@link SnackbarOptions}
     */
    public SnackbarOptions setAction(@StringRes int actionText, ColorStateList textColor, View.OnClickListener listener) {
        mActionTextId = actionText;
        mActionListener = listener;
        mActionColors = textColor;
        return this;
    }

    public boolean hasAction() {
        return mActionListener != null;
    }

    public boolean hasActionTextColor() {
        return mActionTextColor != 0;
    }

    public boolean hasActionColors() {
        return mActionColors != null;
    }

    public ColorStateList getActionColors() {
        return mActionColors;
    }

    @ColorRes
    public int getActionTextColor() {
        return mActionTextColor;
    }

    @Snackbar.Duration
    public int getDuration() {
        return mDuration;
    }

    public int getMaxLines() {
        return mMaxLines;
    }

    @ColorRes
    public int getTextColor() {
        return mTextColor;
    }

    @ColorRes
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public boolean isImportant() {
        return mIsImportant;
    }

    @StringRes
    public int getActionTextId() {
        return mActionTextId;
    }

    public View.OnClickListener getActionListener() {
        return mActionListener;
    }
}
