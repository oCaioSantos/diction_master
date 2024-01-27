package com.example.dictionmaster.ui.components

import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.widget.addTextChangedListener
import com.example.dictionmaster.R
import com.example.dictionmaster.databinding.BoldableEditTextBinding

class BoldableEditText(
    context: Context,
    attrs: AttributeSet
) : RelativeLayout(
    context,
    attrs
) {

    private val binding by lazy {
        BoldableEditTextBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.BoldableEditText).apply {
            recycle()
        }
    }

    fun setBold(isBold: Boolean) {
        binding.etTypeWord.typeface = if (isBold) {
            Typeface.DEFAULT_BOLD
        } else {
            Typeface.DEFAULT
        }
    }

    fun addTextChangedListener(listener: (Editable?) -> Unit) {
        binding.etTypeWord.addTextChangedListener { editable ->
            listener(editable)
        }
    }

    fun getText(): String = binding.etTypeWord.text.toString()

}