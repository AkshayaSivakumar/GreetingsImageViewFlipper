package com.example.android.greeting

import com.example.android.greeting.data.DataModel

interface MainActivityContract {

    interface View {
        fun setData(data: ArrayList<DataModel>)
        fun pauseAnimation()
        fun pauseAudio()
        fun resumeAnimation()
        fun resumeAudio()
        fun enableDisablePausePlayButton(pauseFlag: Boolean, playFlag: Boolean)
    }

    interface Presenter {
        fun initiateDataCreation()
        fun setView(view: View)
        fun pauseButtonClicked()
        fun playButtonClicked()
    }

    interface Model {
        fun createImageArray()
        fun getData(): ArrayList<DataModel>
    }
}