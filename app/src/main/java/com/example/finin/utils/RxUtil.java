package com.example.finin.utils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxUtil {

    public static void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public static void clear(CompositeDisposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.clear();
        }
    }

}
