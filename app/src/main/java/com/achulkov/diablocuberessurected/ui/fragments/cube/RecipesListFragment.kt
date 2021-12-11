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
import com.achulkov.diablocuberessurected.util.TextViewGradientSetter
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class RecipesListFragment : Fragment(), RecipeListAdapter.AdapterItemClickListener {

    @Inject
    lateinit var adapter: RecipeListAdapter


    private val disposable : CompositeDisposable = CompositeDisposable()
    private lateinit var binding: FragmentRecipesListBinding
    private val viewModel : MainViewModel by activityViewModels()

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

        viewModel.filteredRecipesList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
            binding.recipesCounterText.text = String.format(resources.getString(R.string.search_results_counter), it?.size)
        })

        binding.recipesSearchEdittext.textChanges()
//            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                viewModel.filterRecipes(it.toString())
            }
            .addTo(disposable)





    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }

    override fun onAdapterItemClick(recipe: DCubeMappedRecipe) {
        viewModel.selectedRecipe.value = recipe
        viewModel.userActionsCounter.value = viewModel.userActionsCounter.value?.plus(1)
        requireActivity().findNavController(R.id.main_host).navigateUp()
    }


}