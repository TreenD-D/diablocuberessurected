package com.achulkov.diablocuberessurected.ui.fragments.settings

import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import com.achulkov.diablocuberessurected.R
import com.achulkov.diablocuberessurected.databinding.FragmentSettingsBinding
import com.achulkov.diablocuberessurected.util.TextViewGradientSetter
import com.google.android.gms.ads.AdRequest
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import sdk.chat.core.session.ChatSDK
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var binding : FragmentSettingsBinding
    @Inject
    lateinit var gradientSetter : TextViewGradientSetter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)


        gradientSetter.setTextViewGradient(binding.appNameTitle)
        gradientSetter.setTextViewGradient(binding.appNameSubtitle)
        gradientSetter.setTextViewGradient(binding.settingsTitle)


        binding.profileButton.setOnClickListener{
            if(!ChatSDK.auth().isAuthenticated){
                Snackbar.make(binding.profileButton, resources.getString(R.string.need_to_login), Snackbar.LENGTH_LONG)
                    .setAction(R.string.dismiss_snack) {
                        // Responds to click on the action
                    }
                    .setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
                    .show()
                requireActivity().findNavController(R.id.main_host).navigate(R.id.community_page)
            }
            else ChatSDK.ui().startEditProfileActivity(this.context, ChatSDK.currentUserID())
        }

        binding.aboutButton.setOnClickListener {
            requireActivity().findNavController(R.id.main_host).navigate(R.id.aboutAppFragment)
        }


        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)


    }



}