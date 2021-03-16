package com.example.android.greeting

import android.content.Context
import com.example.android.greeting.data.DataModel
import com.example.android.greeting.data.DataProvider

class MainActivityModel(private val context: Context) :
    MainActivityContract.Model {

    private lateinit var data: ArrayList<DataModel>

    override fun createImageArray() {
        data = DataProvider.createAndReturnDataModel(context)
    }

    override fun getData(): ArrayList<DataModel> {
        return data
    }
}