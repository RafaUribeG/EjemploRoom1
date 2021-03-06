package com.example.ejemploroom1

import android.app.Activity
import android.app.AlertDialog

class Dialogo(val mActivity: Activity) {
    private lateinit var isDialog: AlertDialog
    fun mostrarCargando(){
        //set view
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialogo_loading, null)

        //set dialog
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
    }
    fun ocultarCargando(){isDialog.dismiss()}
}