package com.example.dictionmaster.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.dictionmaster.R
import com.example.dictionmaster.databinding.SearchFragmentBinding

class SearchFragment : Fragment() {

    private val binding by lazy {
        SearchFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureListeners()
    }

    private fun configureListeners() {
        binding.etSearch.addTextChangedListener { editable ->
            editable?.let {
                if (it.length > 1) return@let
                binding.buttonSearch.isVisible = editable.isNotEmpty()
                binding.etSearch.setBold(editable.isNotEmpty())
            }
        }
        binding.buttonSearch.setOnClickListener {
            navigateToDefinitionScreen(binding.etSearch.getText())
        }
    }

    private fun navigateToDefinitionScreen(word: String) {
        val action = SearchFragmentDirections
            .fromSearchToDefinition(word)
        requireActivity()
            .findNavController(R.id.fragmentContainerView)
            .navigate(action)
    }
}
