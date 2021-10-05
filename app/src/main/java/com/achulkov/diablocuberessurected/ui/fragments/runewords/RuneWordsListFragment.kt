package com.achulkov.diablocuberessurected.ui.fragments.runewords

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
import com.achulkov.diablocuberessurected.data.models.DCubeMappedRuneword
import com.achulkov.diablocuberessurected.databinding.FragmentRuneWordsListBinding
import com.achulkov.diablocuberessurected.ui.MainViewModel
import com.achulkov.diablocuberessurected.ui.fragments.runewords.adapters.RuneWordListAdapter
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class RuneWordsListFragment : Fragment(), RuneWordListAdapter.AdapterItemClickListener {

    private lateinit var binding: FragmentRuneWordsListBinding

    private val viewModel : MainViewModel by activityViewModels()

    @Inject
    lateinit var adapter: RuneWordListAdapter

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rune_words_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRuneWordsListBinding.bind(view)

        val layoutManager =
            LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        binding.recyclerRecipesList.layoutManager = layoutManager
        binding.recyclerRecipesList.adapter = adapter
        adapter.setListener(this)
        binding.recyclerRecipesList.setHasFixedSize(true)

        viewModel.filteredRuneWords.observe(viewLifecycleOwner, {
            adapter.submitList(it)
            binding.recipesCounterText.text = String.format(resources.getString(R.string.search_runewords_results_counter), it.size)
        })

        binding.recipesSearchEdittext.textChanges()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                viewModel.filterRunewords(it.toString())
            }
            .addTo(disposable)

    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }

    override fun onAdapterItemClick(runeword: DCubeMappedRuneword) {
        viewModel.selectedRuneword.value = runeword
        requireActivity().findNavController(R.id.main_host).navigateUp()
    }


}