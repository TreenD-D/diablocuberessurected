package com.achulkov.diablocuberessurected.ui.chat

import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.achulkov.diablocuberessurected.R
import com.achulkov.diablocuberessurected.databinding.FragmentDcChatBinding
import com.achulkov.diablocuberessurected.ui.chat.adapter.ChatsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import sdk.chat.core.session.ChatSDK


class DCChatFragment : Fragment() {

    private lateinit var binding : FragmentDcChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dc_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //log user in
        if(!ChatSDK.auth().isAuthenticated){
            ChatSDK.ui().startSplashScreenActivity(context)
        }

        binding = FragmentDcChatBinding.bind(view)

        val tabLayout = binding.tabs
        val viewPager = binding.viewpager

        viewPager.adapter =
            ChatsPagerAdapter(childFragmentManager, lifecycle)



        TabLayoutMediator(tabLayout, viewPager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.setText(R.string.chat_rooms_title)
                }
                1 -> {
                    tab.setText(R.string.conversations_title)
                }

            }
        }.attach()

    }


}