package com.achulkov.diablocuberessurected.ui.chat.tabcontainers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.achulkov.diablocuberessurected.R

/**
 * Wrapping class required for chats cause they behave like dialogs
 */
class PrivateRoomsContainerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_private_rooms_container, container, false)
    }


}