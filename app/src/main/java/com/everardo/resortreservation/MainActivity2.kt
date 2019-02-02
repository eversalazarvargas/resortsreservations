package com.everardo.resortreservation

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


/**
 * @author everardo.salazar on 1/30/19
 */
class MainActivity2 : FragmentActivity() {

    private lateinit var resortViewModel: ResortViewModel
    private lateinit var tvData: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvData = findViewById(R.id.tvData)

        // Create your sync account
        AccountGeneral.createSyncAccount(this)

        val textEdit = findViewById<EditText>(R.id.txDataToSave)
//        textEdit.addTextChangedListener(object: TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {
//
//            }
//
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                resortViewModel.saveText(p0.toString())
//            }
//
//        })

        val button = findViewById<Button>(R.id.btnSave)
        button.setOnClickListener {
            resortViewModel.saveText(textEdit.text.toString())
        }

        val factory = ResortViewModelFactory(application)

        resortViewModel = ViewModelProviders.of(this, factory).get(ResortViewModel::class.java)

        observeLiveData()
    }

    private fun observeLiveData() {
        resortViewModel.data.observe(this, Observer<String> {
            tvData.text = it
        })
    }
}