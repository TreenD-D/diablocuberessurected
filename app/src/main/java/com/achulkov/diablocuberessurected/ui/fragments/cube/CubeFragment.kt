package com.achulkov.diablocuberessurected.ui.fragments.cube

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.achulkov.diablocuberessurected.R
import com.achulkov.diablocuberessurected.databinding.FragmentCubeBinding
import com.achulkov.diablocuberessurected.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CubeFragment : Fragment() {

    private lateinit var binding : FragmentCubeBinding

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


        //temp
        requireActivity().findNavController(R.id.main_host).navigate(R.id.recipesListFragment)




    }


}