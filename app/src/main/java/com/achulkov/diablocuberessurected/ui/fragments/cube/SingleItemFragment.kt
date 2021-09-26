package com.achulkov.diablocuberessurected.ui.fragments.cube

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.achulkov.diablocuberessurected.R
import com.achulkov.diablocuberessurected.databinding.FragmentSingleItemBinding
import com.achulkov.diablocuberessurected.ui.MainViewModel
import com.achulkov.diablocuberessurected.util.ImageLoader
import com.achulkov.diablocuberessurected.util.TextViewGradientSetter
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SingleItemFragment : Fragment() {


    private val  viewModel : MainViewModel by activityViewModels()
    @Inject
    lateinit var gradientSetter: TextViewGradientSetter
    @Inject
    lateinit var imageLoader: ImageLoader
    @Inject
    lateinit var storage: FirebaseStorage

    private lateinit var binding: FragmentSingleItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSingleItemBinding.bind(view)

        gradientSetter.setTextViewGradient(binding.itemInfoTitle)
        gradientSetter.setTextViewGradient(binding.statsTitle)
        gradientSetter.setTextViewGradient(binding.recipesTitle)


        viewModel.selectedItem.observe(viewLifecycleOwner, {
            binding.itemName.text = it.itemname
            binding.itemDesc.text = it.itemdesc
            if(it.image.isNotEmpty())
                imageLoader
                    .load(storage.getReferenceFromUrl(it.image))
                    .centerCrop()
                    .into(binding.itemImage)
            var stats = ""
            for(stat in it.stats) stats = stats + stat +"\n"
            binding.stats.text = stats

            var inputOf =""
            for(input in it.inputOf) inputOf = inputOf + input +"\n"
            binding.usedIn.text = inputOf

        })

    }


}