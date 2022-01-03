package com.achulkov.diablocuberessurected.ui.chat

import com.achulkov.diablocuberessurected.R
import sdk.chat.ui.activities.ChatActivity
import sdk.chat.ui.activities.ChatActivityWrapper

class DCChatActivity : ChatActivity(){
    override fun onBackPressed() {
        finish()
    }

    override fun getLayout(): Int {
        return R.layout.dc_custom_activity_chat
    }

    override fun hideTextInput() {
        //empty
    }

}