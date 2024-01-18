package com.example.customui

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieDrawable
import com.example.customui.databinding.ActivityMainBinding
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import kotlin.math.log


class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    private var icons = arrayOf(R.raw.angry,R.raw.smug,R.raw.smile)

    private var backgroundColors = arrayOf(Color.parseColor("#FF0000"),Color.parseColor("#6A0DAD"),Color.parseColor("#87CEEB"))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            emoji(50)
            seekBar.setProgress(50f)

            seekBar.onSeekChangeListener = object : OnSeekChangeListener {
                override fun onSeeking(seekParams: SeekParams) {}
                override fun onStartTrackingTouch(seekBar: IndicatorSeekBar) {}
                override fun onStopTrackingTouch(seekBar: IndicatorSeekBar) {
                    emoji(seekBar.progress)
                }
            }

        }
    }

    private fun emoji(progress: Int) {
       when(progress){
           0-> setLottie(0)
           50-> setLottie(1)
           100-> setLottie(2)
       }
    }

    private fun setLottie(position:Int) {
        binding.apply {
            animateBackgroundColor(position)
            lottie.setAnimation(icons[position])
            lottie.repeatCount = LottieDrawable.INFINITE
            lottie.playAnimation()

        }
    }
    private fun animateBackgroundColor(position: Int) {

        Log.d("animateBackgroundColor", "animateBackgroundColor: ${if(position-1 < 0) 0 else position -1}")

        val animator = ObjectAnimator.ofObject(
            binding.root,
            "backgroundColor",
            ArgbEvaluator(),
            (binding.root.background as? android.graphics.drawable.ColorDrawable)?.color ?: 0,
            backgroundColors[position]
        )

        animator.duration = 1000
        animator.start()
    }

}