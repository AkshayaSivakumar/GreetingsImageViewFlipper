package com.example.android.greeting

import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import android.widget.AdapterViewFlipper
import androidx.appcompat.app.AppCompatActivity
import com.example.android.greeting.data.DataModel
import com.example.android.greeting.utils.AnimationUtils

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private lateinit var presenter: MainActivityPresenter

    private lateinit var adapterViewFlipper: AdapterViewFlipper

    private lateinit var animation: Animation
    private var mediaPlayer: MediaPlayer? = null

    private lateinit var animationUtils: AnimationUtils

    private var currentIndex: Int = 0
    private var displayImageArray = ArrayList<DataModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainActivityPresenter(this)
        presenter.setView(this)
        presenter.initiateDataCreation()

        setupViewFlipper()
    }

    override fun setData(data: ArrayList<DataModel>) {
        displayImageArray = data
    }

    private fun setupViewFlipper() {
        adapterViewFlipper = findViewById<AdapterViewFlipper>(R.id.view_flipper)
        val adapter = ViewFlipperAdapter(this, displayImageArray)
        adapterViewFlipper.adapter = adapter

        setupAnimation()

        setupMediaPlayerAndBGM()
    }

    private fun setupAnimation() {
        animationUtils = AnimationUtils()
        adapterViewFlipper.flipInterval = animationUtils.getFlipInterval()
        animateViewFlipper(0, false)
        adapterViewFlipper.startFlipping()
    }

    private fun setupMediaPlayerAndBGM() {
        mediaPlayer = MediaPlayer.create(this, R.raw.greetings_bgm)
        mediaPlayer?.start()
        mediaPlayer?.isLooping = true
    }

    private fun animateViewFlipper(index: Int, forever: Boolean) {
        animation = animationUtils.initAnimation()
        adapterViewFlipper.animation = animation

        setAnimationListener(index, forever)
    }

    private fun setAnimationListener(index: Int, forever: Boolean) {
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                if (displayImageArray.size - 1 > index) {
                    currentIndex = index + 1
                    animateViewFlipper(currentIndex, forever)
                } else {
                    if (forever) {
                        currentIndex = 0
                        animateViewFlipper(0, forever)
                    }
                }
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
    }

    private lateinit var pauseIcon: MenuItem
    private lateinit var playIcon: MenuItem

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_editor, menu)

        pauseIcon = menu.findItem(R.id.action_pause)
        playIcon = menu.findItem(R.id.action_play)
        playIcon.isEnabled = false

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_pause -> {
                presenter.pauseButtonClicked()
                true
            }
            R.id.action_play -> {
                presenter.playButtonClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun enableDisablePausePlayButton(pauseFlag: Boolean, playFlag: Boolean) {
        pauseIcon.isEnabled = pauseFlag
        playIcon.isEnabled = playFlag
    }

    override fun pauseAnimation() {
        if (adapterViewFlipper.isFlipping) {
            adapterViewFlipper.stopFlipping()
            adapterViewFlipper.clearAnimation()
            animation.cancel()
            animation.setAnimationListener(null)
        }
    }

    override fun pauseAudio() {
        if (null != mediaPlayer && mediaPlayer?.isPlaying!!) {
            mediaPlayer?.pause()
        }
    }

    override fun resumeAnimation() {
        if (!adapterViewFlipper.isFlipping) {
            adapterViewFlipper.startFlipping()
            adapterViewFlipper.animation = animation
            animation.start()
            setAnimationListener(currentIndex + 1, false)
        }
    }

    override fun resumeAudio() {
        if (null != mediaPlayer && !mediaPlayer?.isPlaying!!) {
            mediaPlayer?.start()
        }
    }

    override fun onResume() {
        super.onResume()
        resumeAnimation()
        if (null == mediaPlayer)
            setupMediaPlayerAndBGM()
        resumeAudio()
    }

    override fun onPause() {
        super.onPause()
        pauseAnimation()
        pauseAudio()
    }

    override fun onStop() {
        super.onStop()
        pauseAnimation()
        releaseMediaPlayer()
    }

    private fun releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer?.release();
            mediaPlayer = null;
//            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }
}