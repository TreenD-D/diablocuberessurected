package com.achulkov.diablocuberessurected.ui.fragments.runewords

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.achulkov.diablocuberessurected.R
import com.achulkov.diablocuberessurected.databinding.FragmentRuneWordsBinding
import com.achulkov.diablocuberessurected.ui.MainViewModel
import com.achulkov.diablocuberessurected.util.ImageLoader
import com.achulkov.diablocuberessurected.util.TextViewGradientSetter
import com.google.firebase.storage.FirebaseStorage
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
    @Inject
    lateinit var storage: FirebaseStorage


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
        gradientSetter.setTextViewGradient(binding.runewordName)

        binding.selectRunewordButton.setOnClickListener { requireActivity().findNavController(R.id.main_host).navigate(R.id.runeWordsListFragment) }

        binding.statsTitle.visibility = View.GONE
        binding.usedWithTitle.visibility = View.GONE


        viewModel.selectedRuneword.observe(viewLifecycleOwner, {
            binding.runewordName.text = it.name
            gradientSetter.setTextViewGradient(binding.runewordName)
            binding.usedWith.text = it.inputBaseItem
            binding.subtitle.text = it.levelRequirement
            gradientSetter.setTextViewGradient(binding.subtitle)

            binding.statsTitle.visibility = View.VISIBLE
            binding.usedWithTitle.visibility = View.VISIBLE

            var stats = ""
            for(stat in it.stats) stats = stats + stat +"\n"
            binding.statsList.text = stats

            for((i, rune) in it.inputs.withIndex()){
                var imageView: ImageView
                var runeTitle: TextView
                var runeContainer: LinearLayout
                when(i){
                    0 -> {
                        runeContainer= binding.rune1
                        imageView = binding.rune1Img
                        runeTitle = binding.runeTitle
                    }
                    1 -> {
                        runeContainer = binding.rune2
                        binding.arrow12.visibility = View.VISIBLE
                        imageView = binding.rune2Img
                        runeTitle = binding.rune2Title
                    }
                    2 -> {
                        runeContainer = binding.rune3
                        binding.arrow23.visibility = View.VISIBLE
                        imageView = binding.rune3Img
                        runeTitle = binding.rune3Title
                    }
                    3 -> {
                        runeContainer = binding.rune4
                        binding.arrow34.visibility = View.VISIBLE
                        imageView = binding.rune4Img
                        runeTitle = binding.rune4Title
                    }
                    4 -> {
                        runeContainer = binding.rune5
                        binding.arrow45.visibility = View.VISIBLE
                        imageView = binding.rune5Img
                        runeTitle = binding.rune5Title
                    }
                    else -> {
                        runeContainer = binding.rune6
                        binding.arrow56.visibility = View.VISIBLE
                        imageView = binding.rune6Img
                        runeTitle = binding.rune6Title
                    }
                }
                runeContainer.visibility = View.VISIBLE
                if(viewModel.itemsList.value?.contains(rune) == true) {
                    runeContainer.setOnClickListener {
                        viewModel.selectedItem.postValue(rune)
                        requireActivity().findNavController(R.id.main_host).navigate(R.id.singleItemFragment)
                    }
                }
                runeTitle.visibility = View.VISIBLE
                runeTitle.text = rune.itemname
                imageLoader
                    .load(storage.getReferenceFromUrl(rune.image))
                    .centerInside()
                    .into(imageView)
            }

        })

    }

}