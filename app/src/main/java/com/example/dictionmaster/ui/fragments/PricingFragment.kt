package com.example.dictionmaster.ui.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.dictionmaster.R
import com.example.dictionmaster.databinding.PricingFragmentBinding

class PricingFragment : Fragment() {

    private val binding by lazy {
        PricingFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        setupSubtitleStyle()
        setupSubtitlePricingStyle()
        binding.buttonSubscribe.setOnClickListener {
            navigateToSearchScreen()
        }
    }

    private fun navigateToSearchScreen() {
        val action = PricingFragmentDirections
            .fromPricingToSearch()
        requireActivity()
            .findNavController(R.id.fragmentContainerView)
            .navigate(action)
    }

    private fun setupSubtitleStyle() {
        val spannableString = SpannableStringBuilder(getString(R.string.subscribe_now_label))
        val lightBlue = ContextCompat.getColor(requireContext(), R.color.light_blue)
        val unlimitedSpan = ForegroundColorSpan(lightBlue)
        val allFeaturesSpan = ForegroundColorSpan(lightBlue)
        spannableString.setSpan(
            unlimitedSpan,
            21, 30,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            allFeaturesSpan,
            58, spannableString.length - 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.subtitle.text = spannableString
    }

    private fun setupSubtitlePricingStyle() {
        val spannableString = SpannableStringBuilder(getString(R.string.tryal_label))
        val trySevenDaysSpan = StyleSpan(Typeface.BOLD)
        val priceSpan = StyleSpan(Typeface.BOLD)
        spannableString.setSpan(
            trySevenDaysSpan,
            0, 15,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            priceSpan,
            27, 33,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.subtitlePricing.text = spannableString
    }

}