package be.supinfo.supermarketapp.util

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class MyLifeCycleObserver(val tag: String) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateCallBack() {
        Log.i(tag, "onCreate()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStartCallBack() {
        Log.i(tag, "onStart()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResumeCallBack() {
        Log.i(tag, "onResume()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPauseCallBack() {
        Log.i(tag, "onPause()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStopCallBack() {
        Log.i(tag, "onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyCallBack() {
        Log.i(tag, "onDestroy()")
    }

}