package com.paraxco.imagetools.Dialog

import android.app.Dialog
import android.view.View
import android.widget.TextView
import com.paraxco.imagetools.R

open class YesNoDialogBase(layoutRes: Int) : BluredBackgroundDialog(layoutRes) {
    var yesAction: (() -> Unit)? = null


    override fun initDialog() {
        super.initDialog()

        if (getDialogNote() > 0)
            dialog?.findViewById<TextView>(R.id.txtNote)?.setText(getDialogNote())

        if (getDialogTitle() > 0)
            dialog?.findViewById<TextView>(R.id.txtTitle)?.setText(getDialogTitle())

        dialog?.findViewById<View>(R.id.btnYes)?.setOnClickListener {
            doYesAction()
            yesAction?.invoke()
//            dialog?.dismiss()
        }
    }

    open fun getDialogNote() = 0
    open fun doYesAction() {}
    open fun getDialogTitle() = 0
}
