package com.achulkov.diablocuberessurected.ui.chat

import android.os.Bundle
import com.achulkov.diablocuberessurected.ui.fragments.SettingsFragment
import sdk.chat.ui.fragments.PublicThreadsFragment


class DCubePublicThreadsFragment : PublicThreadsFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.reloadData()

    }

    companion object {
        fun newInstance() =
            DCubePublicThreadsFragment()
    }
}