package com.achulkov.diablocuberessurected.ui.chat

import com.achulkov.diablocuberessurected.R
import sdk.chat.ui.activities.ChatActivity

class DCChatActivity : ChatActivity(){
    override fun onBackPressed() {
        finish()
    }

    override fun getLayout(): Int {
        return R.layout.dc_custom_activity_chat
    }

    override fun updateOptionsButton() {
        //empty to avoid attachment button appearing
    }
}