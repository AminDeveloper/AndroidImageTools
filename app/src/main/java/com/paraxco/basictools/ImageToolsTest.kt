package com.paraxco.basictools

import android.graphics.Bitmap
import android.os.Bundle
import com.paraxco.commontools.Activities.BaseActivity
import com.paraxco.imagetools.ImagePickerHelpers.ImagePickerHelper
import kotlinx.android.synthetic.main.image_tools_test.*
import java.io.File

/**
 *
 */
class ImageToolsTest : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_tools_test)
        button.setOnClickListener { showNotification() }
    }


    private fun showNotification() {
        ImagePickerHelper(this).getImage({ file: File, bitmap: Bitmap ->
            imageView.setImageBitmap(bitmap)
        })
    }


}