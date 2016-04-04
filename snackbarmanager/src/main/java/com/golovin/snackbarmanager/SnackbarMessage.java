package com.golovin.snackbarmanager;

class SnackbarMessage {
    private final String mText;
    private final SnackbarOptions mOptions;

    SnackbarMessage(String text, SnackbarOptions options) {
        mText = text;
        mOptions = options;
    }

    public boolean isImportant() {
        return mOptions.isImportant();
    }

    public SnackbarOptions getOptions() {
        return mOptions;
    }

    public String getText() {
        return mText;
    }
}
