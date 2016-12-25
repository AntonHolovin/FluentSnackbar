package com.golovin.fluentstackbar.helpers;

import android.os.Looper;

public final class ThreadHelper {
    private ThreadHelper() {
        //no instance
    }

    public static void verifyMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException(
                    "Expected to be called on the main thread but was " + Thread.currentThread().getName());
        }
    }
}
