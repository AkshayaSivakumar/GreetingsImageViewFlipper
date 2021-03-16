package com.example.android.greeting

import android.content.Context

class MainActivityPresenter(private val context: Context) :
    MainActivityContract.Presenter {

    private lateinit var view: MainActivityContract.View

    override fun setView(view: MainActivityContract.View) {
        this.view = view
    }

    override fun initiateDataCreation() {
        val model = MainActivityModel(context)
        model.createImageArray()
        view.setData(model.getData())
    }


    override fun pauseButtonClicked() {
        view.pauseAnimation()
        view.pauseAudio()
        view.enableDisablePausePlayButton(pauseFlag = false, playFlag = true)
    }

    override fun playButtonClicked() {
        view.resumeAnimation()
        view.resumeAudio()
        view.enableDisablePausePlayButton(pauseFlag = true, playFlag = false)
    }
}