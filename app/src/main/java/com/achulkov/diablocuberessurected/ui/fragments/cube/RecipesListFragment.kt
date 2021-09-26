package com.achulkov.diablocuberessurected.ui.fragments.cube

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.achulkov.diablocuberessurected.R
import com.achulkov.diablocuberessurected.data.models.DCubeMappedRecipe
import com.achulkov.diablocuberessurected.databinding.FragmentRecipesListBinding
import com.achulkov.diablocuberessurected.ui.MainViewModel
import com.achulkov.diablocuberessurected.ui.fragments.cube.adapters.RecipeListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipesListFragment : Fragment(), RecipeListAdapter.AdapterItemClickListener {

    @Inject
    lateinit var adapter: RecipeListAdapter

    private lateinit var binding: FragmentRecipesListBinding
    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecipesListBinding.bind(view)

        val layoutManager =
            LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        binding.recyclerRecipesList.layoutManager = layoutManager
        binding.recyclerRecipesList.adapter = adapter
        adapter.setListener(this)
        binding.recyclerRecipesList.setHasFixedSize(true)

        viewModel.recipesList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
            binding.recipesCounterText.text = String.format(resources.getString(R.string.search_results_counter), it.size)
        })





    }

    override fun onAdapterItemClick(recipe: DCubeMappedRecipe) {
        viewModel.selectedRecipe.value = recipe
        requireActivity().findNavController(R.id.main_host).navigateUp()
    }


}