package com.achulkov.diablocuberessurected.ui.onboarding.page_fragments

import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.achulkov.diablocuberessurected.R
import com.achulkov.diablocuberessurected.databinding.FragmentPageBinding
import com.achulkov.diablocuberessurected.util.TextViewGradientSetter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PageFragment : Fragment() {

    private lateinit var binding: FragmentPageBinding
    @Inject
    lateinit var gradientSetter : TextViewGradientSetter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPageBinding.bind(view)

        when(arguments?.getInt("index")){
            0 -> {
                binding.mainPageImage.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.onborading_image_1, null))
                binding.topLayout.setBackgroundResource(R.drawable.onboarding_background_1)
                binding.title.text = resources.getString(R.string.welcome_page_1_title)
                binding.subtitle.text = resources.getString(R.string.welcome_page_1_subtitle)
            }
            1 -> {
                binding.mainPageImage.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.onborading_image_2, null))
                binding.topLayout.setBackgroundResource(R.drawable.onboarding_background_2)
                binding.title.text = resources.getString(R.string.welcome_page_2_title)
                binding.subtitle.text = resources.getString(R.string.welcome_page_2_subtitle)
            }
            2 -> {
                binding.mainPageImage.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.onborading_image_3, null))
                binding.topLayout.setBackgroundResource(R.drawable.onboarding_background_3)
                binding.title.text = resources.getString(R.string.welcome_page_3_title)
                binding.subtitle.text = resources.getString(R.string.welcome_page_3_subtitle)
            }
        }



        gradientSetter.setTextViewGradient(binding.title)

    }

}