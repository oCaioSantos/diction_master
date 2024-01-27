package com.example.dictionmaster.ui.fragments

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dictionmaster.R
import com.example.dictionmaster.databinding.DefinitionFragmentBinding
import com.example.dictionmaster.domain.models.FreeDictionaryResponse
import com.example.dictionmaster.domain.models.getDefinitionMockList
import com.example.dictionmaster.domain.models.getPhoneticAudio
import com.example.dictionmaster.domain.models.getPhoneticText
import com.example.dictionmaster.domain.models.wordToCapitalized
import com.example.dictionmaster.ui.adapters.DefinitionAdapter
import com.example.dictionmaster.ui.viewholders.DefinitionViewHolder
import com.example.dictionmaster.ui.viewmodels.DefinitionViewModel
import org.koin.android.ext.android.inject

class DefinitionFragment : Fragment() {

    private val viewModel by inject<DefinitionViewModel>()

    private val binding by lazy {
        DefinitionFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners(navArgs<DefinitionFragmentArgs>().value.word)
    }

    private fun initListeners(word: String) {
        viewModel.doSearchByWord(word)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DefinitionViewHolder.Loading -> {
                    handleLoadingUI()
                }

                is DefinitionViewHolder.Success -> {
                    handleSuccessUI(state.data)
                }

                is DefinitionViewHolder.Error -> {
                    handleErrorUI()
                }

                is DefinitionViewHolder.NoDefinition -> {
                    handleNoDefinitionUI(word)

                }

                is DefinitionViewHolder.WordLimitExceeded -> {
                    navigateToPricingScreen()
                }
            }
        }
        binding.layoutErrorContainer.setButtonText(R.string.retry_label)
        binding.layoutErrorContainer.setButtonClickListener {
            navigateToSearchScreen()
        }
    }

    private fun navigateToPricingScreen() {
        val action = DefinitionFragmentDirections.fromDefinitionToPricing()
        requireActivity()
            .findNavController(R.id.fragmentContainerView)
            .navigate(action)
    }

    private val mediaPlayer by lazy {
        MediaPlayer()
    }

    private fun handleErrorUI(
        message: String = getString(R.string.something_went_wrong_label),
        buttonText: String? = null
    ) {
        binding.progressBar.isVisible = false
        binding.layoutContainer.isVisible = false
        binding.layoutErrorContainer.isVisible = true
        binding.layoutNewSearchContainer.isVisible = false
        binding.layoutErrorContainer.setText(message)
        buttonText?.let {
            binding.layoutErrorContainer.setButtonText(it)
        }
    }

    private fun handleNoDefinitionUI(word: String) {
        handleErrorUI(
            getString(R.string.no_definition_error, word),
            getString(R.string.new_search_label)
        )
    }

    private fun handleSuccessUI(data: FreeDictionaryResponse) {
        binding.progressBar.isVisible = false
        binding.layoutContainer.isVisible = true
        binding.layoutErrorContainer.isVisible = false
        binding.layoutNewSearchContainer.isVisible = true
        binding.tvWord.text = data.wordToCapitalized()
        binding.tvPhonetic.text =
            data.getPhoneticText() ?: getString(R.string.no_phonetic_available_label)
        binding.thatsItText.text = getString(R.string.thats_it_label, data.word)
        data.getPhoneticAudio()?.let { phoneticAudio ->
            binding.buttonPhonetic.setOnClickListener {
                prepareAudioListener(
                    audio = phoneticAudio
                )
            }
        } ?: run {
            binding.buttonPhonetic.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_audio_available_label),
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.buttonPhonetic.setNoAudio()
        }
        binding.buttonNewSearch.setOnClickListener {
            navigateToSearchScreen()
        }
        binding.rvDefinitions.adapter = DefinitionAdapter().apply {
            setData(data.getDefinitionMockList())
        }
    }

    private var audioAlreadyPlayed = false
    private fun prepareAudioListener(audio: String) {
        if (!audioAlreadyPlayed) {
            binding.buttonPhonetic.setLoading(true)
            mediaPlayer.setDataSource(audio)
            mediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()

            )
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                audioAlreadyPlayed = true
                binding.buttonPhonetic.setLoading(false)
                mediaPlayer.start()
            }
        } else {
            mediaPlayer.seekTo(0)
            mediaPlayer.start()
        }
    }

    private fun handleLoadingUI() {
        binding.progressBar.isVisible = true
        binding.layoutContainer.isVisible = false
        binding.layoutErrorContainer.isVisible = false
        binding.layoutNewSearchContainer.isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
    }

    private fun navigateToSearchScreen() {
        val action = DefinitionFragmentDirections.fromDefinitionToSearch()
        requireActivity()
            .findNavController(R.id.fragmentContainerView)
            .navigate(action)
    }

}