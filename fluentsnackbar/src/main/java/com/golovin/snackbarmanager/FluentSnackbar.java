package com.golovin.snackbarmanager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class FluentSnackbar {
    private final AppCompatActivity mActivity;

    private final SnackbarHandler mSnackbarHandler;

    public FluentSnackbar(AppCompatActivity activity) {
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

    private void putToMessageQueue(Builder builder) {
        Message message = mSnackbarHandler.obtainMessage(SnackbarHandler.MESSAGE_NEW, builder);

        mSnackbarHandler.sendMessage(message);
    }

    void showSnackbar(Builder builder) {
        //noinspection ConstantConditions,ResourceType
        Snackbar snackbar = Snackbar.make(mActivity.findViewById(android.R.id.content), builder.getText(),
                builder.getDuration());

        View view = snackbar.getView();
        view.setBackgroundColor(builder.getBackgroundColor());

        TextView textView = (TextView) view.findViewById(R.id.snackbar_text);
        textView.setMaxLines(builder.getMaxLines());
        textView.setTextColor(builder.getTextColor());

        if (builder.hasAction()) {
            snackbar.setAction(builder.getActionText(), builder.getActionListener());

            if (builder.hasActionTextColor()) {
                snackbar.setActionTextColor(ContextCompat.getColor(mActivity, builder.getActionTextColor()));
            } else if (builder.hasActionTextColors()) {
                snackbar.setActionTextColor(builder.getActionColors());
            }
        }

        if (builder.isImportant()) {
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

    public Builder create(@StringRes int text) {
        return create(mActivity.getString(text));
    }

    public Builder create(String text) {
        return new Builder(text);
    }

    public class Builder {
        private CharSequence mText;

        private int mMaxLines;

        @ColorInt
        private int mTextColor;

        @ColorInt
        private int mBackgroundColor;

        private boolean mIsImportant;

        @Snackbar.Duration
        private int mDuration;

        private CharSequence mActionText;

        private View.OnClickListener mActionListener;

        @ColorInt
        private int mActionTextColor;
        private ColorStateList mActionColors;
        private boolean mHasActionTextColor;

        private Builder(CharSequence text) {
            mText = text;
            mMaxLines = 1;
            mTextColor = Color.WHITE;
            mBackgroundColor = ContextCompat.getColor(mActivity, R.color.default_background);
            mIsImportant = false;
            mDuration = Snackbar.LENGTH_LONG;
            mActionText = mActivity.getString(R.string.default_action);
        }

        public Builder maxLines(int maxLines) {
            mMaxLines = maxLines;
            return this;
        }

        public Builder textColorRes(@ColorRes int color) {
            mTextColor = ContextCompat.getColor(mActivity, color);
            return this;
        }

        public Builder textColor(@ColorInt int color) {
            mTextColor = color;
            return this;
        }

        public Builder successBackgroundColor() {
            mBackgroundColor = ContextCompat.getColor(mActivity, R.color.green_500);
            return this;
        }

        public Builder errorBackgroundColor() {
            mBackgroundColor = ContextCompat.getColor(mActivity, R.color.red_500);
            return this;
        }

        public Builder warningBackgroundColor() {
            mBackgroundColor = ContextCompat.getColor(mActivity, R.color.yellow_500);
            return this;
        }

        public Builder neutralBackgroundColor() {
            mBackgroundColor = ContextCompat.getColor(mActivity, R.color.default_background);
            return this;
        }

        public Builder backgroundColorRes(@ColorRes int color) {
            mBackgroundColor = ContextCompat.getColor(mActivity, color);
            return this;
        }

        public Builder backgroundColor(@ColorInt int color) {
            mBackgroundColor = color;
            return this;
        }

        public Builder important() {
            return important(true);
        }

        public Builder important(boolean isImportant) {
            mIsImportant = isImportant;
            return this;
        }

        public Builder duration(@Snackbar.Duration int duration) {
            mDuration = duration;
            return this;
        }

        public Builder action(View.OnClickListener listener) {
            mActionListener = listener;
            return this;
        }

        public Builder actionTextRes(@StringRes int text) {
            mActionText = mActivity.getString(text);
            return this;
        }

        public Builder actionText(String text) {
            mActionText = text;
            return this;
        }

        public Builder actionTextColorRes(@ColorRes int color) {
            return actionTextColor(ContextCompat.getColor(mActivity, color));
        }

        public Builder actionTextColor(@ColorInt int color) {
            mActionTextColor = color;
            mHasActionTextColor = true;
            return this;
        }

        public Builder actionTextColors(ColorStateList actionColors) {
            mActionColors = actionColors;
            return this;
        }

        public void show() {
            putToMessageQueue(this);
        }

        CharSequence getText() {
            return mText;
        }

        int getMaxLines() {
            return mMaxLines;
        }

        @Snackbar.Duration
        int getDuration() {
            return mDuration;
        }

        @ColorInt
        int getBackgroundColor() {
            return mBackgroundColor;
        }

        @ColorInt
        int getTextColor() {
            return mTextColor;
        }

        @ColorInt
        int getActionTextColor() {
            return mActionTextColor;
        }

        CharSequence getActionText() {
            return mActionText;
        }

        View.OnClickListener getActionListener() {
            return mActionListener;
        }

        ColorStateList getActionColors() {
            return mActionColors;
        }

        boolean hasAction() {
            return mActionListener != null;
        }

        boolean isImportant() {
            return mIsImportant;
        }

        boolean hasActionTextColor() {
            return mHasActionTextColor;
        }

        boolean hasActionTextColors() {
            return mActionColors != null;
        }
    }
}
