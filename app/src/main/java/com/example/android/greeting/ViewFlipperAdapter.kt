package com.example.android.greeting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.android.greeting.data.DataModel

class ViewFlipperAdapter(
    context: Context,
    private var displayImagesArray: ArrayList<DataModel>
) :
    BaseAdapter() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    private var position: Int = 0

    private class ViewHolder(rowItem: View?) {
        var displayImage: ImageView = rowItem?.findViewById(R.id.display_image) as ImageView
        var captionText: TextView = rowItem?.findViewById(R.id.caption_text) as TextView

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val viewHolder: ViewHolder

        if (null == convertView) {
            view = this.layoutInflater.inflate(R.layout.flip_view_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val dataModel: DataModel = getItem(position)

        setPosition(position)

        viewHolder.displayImage.setImageResource(dataModel.imageId)
        viewHolder.captionText.text = dataModel.caption

        return view
    }

    override fun getItem(position: Int): DataModel {
        return displayImagesArray[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return displayImagesArray.size
    }

    fun getPosition(): Int {
        return position
    }

    private fun setPosition(position: Int) {
        this.position = position
    }
}