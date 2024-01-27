package com.example.dictionmaster.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import com.example.dictionmaster.R
import com.example.dictionmaster.databinding.PhoneticButtonBinding

class PhoneticButton(
    context: Context,
    attrs: AttributeSet,
) : RelativeLayout(
    context,
    attrs
) {

    private val binding by lazy {
        PhoneticButtonBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.PhoneticButton).apply {
            setLoading(
                getBoolean(R.styleable.PhoneticButton_loading, false)
            )
            recycle()
        }
    }

    fun setLoading(loading: Boolean) {
        binding.progressBar.isVisible = loading
        binding.buttonPlay.isVisible = !loading
    }

    fun setNoAudio() {
        binding.buttonPlay.setImageResource(R.drawable.audio_speaker_off)
    }

}