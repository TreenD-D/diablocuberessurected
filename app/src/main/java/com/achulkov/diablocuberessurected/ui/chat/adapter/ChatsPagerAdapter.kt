package com.achulkov.diablocuberessurected.ui.chat.adapter


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.achulkov.diablocuberessurected.ui.chat.tabcontainers.PrivateRoomsContainerFragment
import com.achulkov.diablocuberessurected.ui.chat.tabcontainers.PublicRoomsContainerFragment

class ChatsPagerAdapter (
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun createFragment(position: Int): Fragment {


        return when (position) {
            0 -> {
                PublicRoomsContainerFragment()

            }
            1 -> {
                PrivateRoomsContainerFragment()
            }

            else -> {
                PublicRoomsContainerFragment()
//                DCubePublicThreadsFragment.newInstance()
            }
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}