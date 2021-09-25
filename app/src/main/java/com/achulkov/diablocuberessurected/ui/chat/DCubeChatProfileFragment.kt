package com.achulkov.diablocuberessurected.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.achulkov.diablocuberessurected.R
import sdk.chat.ui.fragments.ProfileFragment


class DCubeChatProfileFragment : ProfileFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun getLayout(): Int {
        return R.layout.fragment_d_cube_chat_profile
    }


}