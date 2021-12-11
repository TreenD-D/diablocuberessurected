package com.achulkov.diablocuberessurected.ui.fragments.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import com.achulkov.diablocuberessurected.R
import com.achulkov.diablocuberessurected.databinding.FragmentAboutAppBinding
import com.achulkov.diablocuberessurected.ui.MainViewModel
import com.achulkov.diablocuberessurected.util.TextViewGradientSetter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AboutAppFragment : Fragment() {

    @Inject
    lateinit var gradientSetter : TextViewGradientSetter

    private lateinit var binding: FragmentAboutAppBinding

    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_app, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAboutAppBinding.bind(view)


        gradientSetter.setTextViewGradient(binding.appNameTitle)
        gradientSetter.setTextViewGradient(binding.appNameSubtitle)

        viewModel.aboutAppText.observe(viewLifecycleOwner , {
            binding.appInfoText.text = HtmlCompat.fromHtml(it, 0)
        })

    }


}