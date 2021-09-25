package com.achulkov.diablocuberessurected.ui.chat

import android.os.Bundle
import sdk.chat.ui.fragments.PrivateThreadsFragment

class DCubePrivateThreadsFragment : PrivateThreadsFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.reloadData()

    }

    companion object {
        fun newInstance() =
            DCubePrivateThreadsFragment()
    }
}