package com.example.android.greeting.data

import android.content.Context
import com.example.android.greeting.R

object DataProvider {
    private var modelList = ArrayList<DataModel>()

    fun createAndReturnDataModel(context: Context): ArrayList<DataModel> {

        val images = context.resources.obtainTypedArray(R.array.images_array)
        val captions = context.resources.getStringArray(R.array.captions_array)

        for (index in captions.indices) {
            modelList.add(
                DataModel(
                    imageId = images.getResourceId(index, -1),
                    caption = captions[index]
                )
            )
        }

        images.recycle()

        return modelList
    }
}