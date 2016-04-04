package com.golovin.snackbarmanager;

import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Queue;

class SnackbarHandler extends Handler {
    static final int MESSAGE_DISMISSED = 0;
    static final int MESSAGE_NEW = 1;

    private Queue<SnackbarMessage> mQueue = new LinkedList<>();

    private WeakReference<SnackbarManager> mSnackbarManager;

    SnackbarHandler(SnackbarManager manager) {
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
        SnackbarMessage shownMessage = mQueue.peek();
        SnackbarMessage newMessage = (SnackbarMessage) msg.obj;

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

    private void show(SnackbarMessage message) {
        SnackbarManager manager = mSnackbarManager.get();
        if (manager != null) {
            manager.showSnackbar(message.getText(), message.getOptions());
        }
    }

    private boolean hasLastAndNotImportant() {
        return !mQueue.peek().isImportant() && mQueue.size() == 1;
    }
}
