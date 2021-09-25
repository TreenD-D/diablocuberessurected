package com.achulkov.diablocuberessurected.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.achulkov.diablocuberessurected.R
import sdk.chat.ui.activities.ProfileActivity

class DCubeChatProfileActivity : ProfileActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_dcube_chat_profile
    }

}