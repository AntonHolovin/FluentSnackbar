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
                showNext();
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

            showNext();
        } else {
            mQueue.add(newMessage);
        }
    }

    private void showNext() {
        removeLowPriorityMessages();

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

    private void removeLowPriorityMessages() {
        while (hasItemsToRemove()) {
            mQueue.poll();
        }
    }

    private boolean hasItemsToRemove() {
        if (mQueue.isEmpty()) {
            return false;
        }

        boolean hasImportant = mQueue.peek().isImportant();
        boolean hasSingleNonImportant = mQueue.size() == 1 && !mQueue.peek().isImportant();

        return !(hasImportant || hasSingleNonImportant);
    }
}