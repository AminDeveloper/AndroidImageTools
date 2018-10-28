package com.paraxco.imagetools.Dialog

import android.app.Dialog
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.content.Context

class LifecycleDialg : Dialog, LifecycleOwner {
    constructor(context: Context) : super(context)
    constructor(context: Context, fadeInAnimateDialog: Int) : super(context, fadeInAnimateDialog)

    private var mLifecycleRegistry: LifecycleRegistry

    init {
        mLifecycleRegistry = LifecycleRegistry(this)
//        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }


    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun onStop() {
        super.onStop()
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    override fun dismiss() {
        super.dismiss()
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }

}
