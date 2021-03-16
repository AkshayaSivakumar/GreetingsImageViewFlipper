package com.example.android.greeting.utils

import android.view.animation.*

class AnimationUtils() {
    private val fadeInDuration = 800L;
    private val fadeOutDuration = 800L;
    private val imageDisplayDuration = 3000L;

    private var flipIntervalTime: Long

    init {
        flipIntervalTime = fadeInDuration + fadeOutDuration + imageDisplayDuration
    }

    fun initAnimation(): AnimationSet {

        val fadeInAnimation = AlphaAnimation(0F, 1F)
        fadeInAnimation.interpolator = DecelerateInterpolator() // add this
        fadeInAnimation.duration = fadeInDuration

        val fadeOutAnimation = AlphaAnimation(1F, 0F)
        fadeOutAnimation.interpolator = AccelerateInterpolator()
        fadeOutAnimation.startOffset = fadeInDuration + imageDisplayDuration
        fadeOutAnimation.duration = fadeOutDuration

        val animation = AnimationSet(false)
        animation.addAnimation(fadeInAnimation)
        animation.addAnimation(fadeOutAnimation)
        animation.repeatCount = 1

        return animation
    }

    fun getFlipInterval(): Int = flipIntervalTime.toInt()
}