package com.paraxco.basictools

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.paraxco.basictools.ImageTools.Dialog.MyCustomDialog
import kotlinx.android.synthetic.main.main_activity.*


/**
 * Created by Amin on 18/11/2017.
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        SmartLogger.initLogger(applicationContext)
        setContentView(R.layout.main_activity)

        showImageToolsTest.setOnClickListener({
            startImageToolsTest()
        })
        DialogTest.setOnClickListener {
            var myCustomDialog = MyCustomDialog()
            myCustomDialog.showDialog(this)
        }


    }


    private fun startImageToolsTest() {
        val myIntent = Intent(this, ImageToolsTest::class.java)
//        myIntent.putExtra("key", value) //Optional parameters
        this.startActivity(myIntent)
    }

}