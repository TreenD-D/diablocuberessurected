package com.achulkov.diablocuberessurected.ui.fragments.runewords

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.achulkov.diablocuberessurected.R
import com.achulkov.diablocuberessurected.databinding.FragmentRuneWordsBinding
import com.achulkov.diablocuberessurected.ui.MainViewModel
import com.achulkov.diablocuberessurected.util.ImageLoader
import com.achulkov.diablocuberessurected.util.TextViewGradientSetter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RuneWordsFragment : Fragment() {

    private lateinit var binding: FragmentRuneWordsBinding

    private val viewModel : MainViewModel by activityViewModels()

    @Inject
    lateinit var gradientSetter : TextViewGradientSetter
    @Inject
    lateinit var imageLoader : ImageLoader


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rune_words, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRuneWordsBinding.bind(view)

        gradientSetter.setTextViewGradient(binding.appNameTitle)
        gradientSetter.setTextViewGradient(binding.appNameSubtitle)


        binding.selectRunewordButton.setOnClickListener { requireActivity().findNavController(R.id.main_host).navigate(R.id.runeWordsListFragment) }


        viewModel.selectedRuneword.observe(viewLifecycleOwner, {
            binding.runewordName.text = it.name
            gradientSetter.setTextViewGradient(binding.runewordName)
        })

    }

}