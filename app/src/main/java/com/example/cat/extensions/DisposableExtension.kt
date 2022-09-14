package com.example.cat.extensions

import io.reactivex.rxjava3.disposables.Disposable

fun Disposable.disposeIfNotAlready() {
    if(!isDisposed) {
        dispose()
    }
}