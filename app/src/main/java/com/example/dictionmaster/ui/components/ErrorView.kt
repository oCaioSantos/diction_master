package com.example.dictionmaster.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.annotation.StringRes
import com.example.dictionmaster.R
import com.example.dictionmaster.databinding.ErrorViewBinding

class ErrorView(
    context: Context,
    attrs: AttributeSet
) : RelativeLayout(context, attrs) {

    private val binding by lazy {
        ErrorViewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.ErrorView)
            .apply {
                setText(
                    R.string.something_went_wrong_label
                )
                recycle()
            }
    }

    fun setText(text: String? = null) {
        binding.tvWordError.text =
            text ?: context.getString(R.string.something_went_wrong_label)
    }

    fun setText(@StringRes text: Int? = null) {
        binding.tvWordError.text =
            context.getString(text ?: R.string.something_went_wrong_label)
    }

    fun setButtonText(text: String? = null) {
        binding.buttonReload.text =
            text ?: context.getString(R.string.reload_label)
    }

    fun setButtonText(@StringRes text: Int? = null) {
        binding.buttonReload.text =
            context.getString(text ?: R.string.reload_label)
    }

    fun setButtonClickListener(listener: () -> Unit) {
        binding.buttonReload.setOnClickListener {
            listener()
        }
    }

}