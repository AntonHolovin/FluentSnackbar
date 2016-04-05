package com.golovin.fluentstackbar;

import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Queue;

class SnackbarHandler extends Handler {
    static final int MESSAGE_DISMISSED = 0;
    static final int MESSAGE_NEW = 1;

    private Queue<FluentSnackbar.Builder> mQueue = new LinkedList<>();

    private WeakReference<FluentSnackbar> mSnackbarManager;

    SnackbarHandler(FluentSnackbar manager) {
        mSnackbarManager = new WeakReference<>(manager);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MESSAGE_DISMISSED:
                mQueue.poll();
                iterateAndShow();
                break;

            case MESSAGE_NEW:
                onNewMessage(msg);
                break;
        }
    }

    private void onNewMessage(Message msg) {
        FluentSnackbar.Builder shownMessage = mQueue.peek();
        FluentSnackbar.Builder newMessage = (FluentSnackbar.Builder) msg.obj;

        if (shownMessage == null || !shownMessage.isImportant()) {
            mQueue.poll();
            mQueue.add(newMessage);

            iterateAndShow();
        } else {
            mQueue.add(newMessage);
        }
    }

    private void iterateAndShow() {
        while (!mQueue.isEmpty() && !(hasLastAndNotImportant() || mQueue.peek().isImportant())) {
            mQueue.poll();
        }

        if (!mQueue.isEmpty()) {
            show(mQueue.peek());
        }
    }

    private void show(FluentSnackbar.Builder message) {
        FluentSnackbar manager = mSnackbarManager.get();
        if (manager != null) {
            manager.showSnackbar(message);
        }
    }

    private boolean hasLastAndNotImportant() {
        return !mQueue.peek().isImportant() && mQueue.size() == 1;
    }
}
