package com.achulkov.diablocuberessurected.ui.fragments.cube

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.achulkov.diablocuberessurected.R
import com.achulkov.diablocuberessurected.data.models.DCubeMappedInput
import com.achulkov.diablocuberessurected.databinding.FragmentCubeBinding
import com.achulkov.diablocuberessurected.ui.MainViewModel
import com.achulkov.diablocuberessurected.ui.fragments.cube.adapters.ItemListAdapter
import com.achulkov.diablocuberessurected.util.ImageLoader
import com.achulkov.diablocuberessurected.util.TextViewGradientSetter
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CubeFragment : Fragment(), ItemListAdapter.AdapterItemClickListener {

    private lateinit var binding : FragmentCubeBinding

    @Inject
    lateinit var gradientSetter : TextViewGradientSetter
    @Inject
    lateinit var imageLoader : ImageLoader
    @Inject
    lateinit var storage: FirebaseStorage
    @Inject
    lateinit var adapter : ItemListAdapter

    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cube, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCubeBinding.bind(view)

        gradientSetter.setTextViewGradient(binding.appNameTitle)
        gradientSetter.setTextViewGradient(binding.appNameSubtitle)


        binding.selectRecipeButton.setOnClickListener {
            requireActivity().findNavController(R.id.main_host).navigate(R.id.recipesListFragment)
        }

        val layoutManager =
            LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        binding.recyclerInputsList.layoutManager = layoutManager
        binding.recyclerInputsList.adapter = adapter
        adapter.setListener(this)
        binding.recyclerInputsList.setHasFixedSize(true)


        viewModel.selectedRecipe.observe(viewLifecycleOwner ,{ recipe ->
            adapter.submitList(recipe.inputs)
            binding.itemResultImage.setOnClickListener {
                viewModel.selectedItem.value = recipe.output
                requireActivity().findNavController(R.id.main_host).navigate(R.id.singleItemFragment)
            }
            if(recipe.output.image.isNotEmpty())
                imageLoader
                    .load(storage.getReferenceFromUrl(recipe.output.image))
                    .centerCrop()
                    .into(binding.itemResultImage)
            else binding.itemResultImage.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.convert_icon, null))
        })


    }

    override fun onAdapterItemClick(item: DCubeMappedInput) {
        viewModel.selectedItem.value = item.item
        requireActivity().findNavController(R.id.main_host).navigate(R.id.singleItemFragment)
    }


}