package com.paraxco.imagetools.Dialog

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.TextView
import com.paraxco.imagetools.R
import com.paraxco.imagetools.Utils.ImageUtils

/**
 * use view by id bluredView for blured background
 * and btnCancel for dismiss
 */
open class BluredBackgroundDialog(val layoutRes: Int) {
    protected var dialog: LifecycleDialg? = null

    fun showDialog(activity: Activity) {

        dialog = LifecycleDialg(activity, R.style.FadeInAnimateDialog)
        dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.attributes.windowAnimations = R.style.FadeInDialogAnimation
        dialog!!.setContentView(layoutRes)
        dialog!!.setCancelable(true)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val wlp = dialog!!.window!!.attributes
        dialog!!.window!!.attributes = wlp

        initDialog()
        //blure
        ImageUtils.blurView(activity, dialog!!.findViewById(R.id.bluredView))
        dialog!!.show()
    }

    protected open fun initDialog() {

        dialog?.findViewById<View>(R.id.rlDialogBack)?.setOnClickListener {
            dialog?.dismiss()
        }

        dialog?.findViewById<View>(R.id.rlDialog)?.setOnClickListener {
            //to prevent rlDialogBack on click to be performed on rlDialog or background activity
        }

        dialog?.findViewById<View>(R.id.btnCancel)?.setOnClickListener {
            dialog?.dismiss()
        }

    }
}
