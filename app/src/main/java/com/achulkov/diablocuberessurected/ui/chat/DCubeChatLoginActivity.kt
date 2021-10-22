package com.achulkov.diablocuberessurected.ui.chat

import com.achulkov.diablocuberessurected.R
import sdk.chat.core.session.ChatSDK
import sdk.chat.ui.activities.LoginActivity

class DCubeChatLoginActivity : LoginActivity() {
    override fun onBackPressed() {
        ChatSDK.ui().startMainActivity(this)
    }

    override fun getLayout(): Int {
        return R.layout.activity_dcube_chat_login
    }

}