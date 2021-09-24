package com.achulkov.diablocuberessurected

import android.os.StrictMode
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDexApplication
import com.achulkov.diablocuberessurected.BuildConfig.GMAPS_STATIC_API_KEY
import com.achulkov.diablocuberessurected.ui.DCMainActivity
import com.achulkov.diablocuberessurected.ui.chat.DCChatActivity
import com.achulkov.diablocuberessurected.ui.chat.DCubeChatInterfaceAdapter
import com.achulkov.diablocuberessurected.ui.chat.DCubePrivateThreadsFragment
import com.achulkov.diablocuberessurected.ui.chat.DCubePublicThreadsFragment
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp
import sdk.chat.core.session.ChatSDK
import sdk.chat.firebase.adapter.module.FirebaseModule
import sdk.chat.firebase.push.FirebasePushModule
import sdk.chat.firebase.ui.FirebaseUIModule
import sdk.chat.ui.ChatSDKUI
import sdk.chat.ui.module.UIModule
import timber.log.Timber

@HiltAndroidApp
class DiabloCubeApp : MultiDexApplication() {
    override fun onCreate() {
        Firebase.database.setPersistenceEnabled(true)
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)




        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults()
            Timber.plant(Timber.DebugTree())
        }

        try {
            ChatSDK.builder()
                .setGoogleMaps(GMAPS_STATIC_API_KEY)
                .setReplyFromNotificationEnabled(true)
                .setAnonymousLoginEnabled(true)
                .setInboundPushHandlingEnabled(true)
                .setReuseDeleted1to1Threads(true)
                .setPushNotificationColor(R.color.grey)
                .setPushNotificationImageDefaultResourceId(R.mipmap.ic_launcher)
                .setPublicChatRoomLifetimeMinutes(0)
                .setRolesEnabled(true)
                .build()
            // Add the Firebase network adapter module
            .addModule(
                FirebaseModule.builder()
                    .setFirebaseRootPath("pre_1")
                    .build()
            )
            // Add the UI module
            .addModule(
                UIModule.builder()
                .setPublicRoomCreationEnabled(true)
                .setPublicRoomsEnabled(true)
                .setDefaultProfilePlaceholder(R.drawable.default_avatar)
                .setAllowBackPressFromMainActivity(true)
                .build()
            )
            // Add modules to handle file uploads, push notifications
//            .addModule(FirebaseUploadModule.shared())
            .addModule(FirebasePushModule.shared())
            // Enable Firebase UI with phone and email auth
            .addModule(
                FirebaseUIModule.builder()
                .setProviders(EmailAuthProvider.PROVIDER_ID, PhoneAuthProvider.PROVIDER_ID)
                .build()
            )
            // Activate
            .build()
                .activate(this)



            ChatSDK.shared().setInterfaceAdapter(DCubeChatInterfaceAdapter(this))
        } catch (e :Exception) {
            e.printStackTrace()
        }

        ChatSDK.config().logoDrawableResourceID = R.mipmap.ic_launcher
        ChatSDK.config().threadDestructionEnabled = false
        UIModule.config().setTheme(R.style.CustomChatSDKTheme)

        ChatSDK.ui().mainActivity = (DCMainActivity::class.java)
        ChatSDK.ui().chatActivity = (DCChatActivity::class.java)
        ChatSDKUI.setChatActivity(DCChatActivity::class.java)
        ChatSDKUI.setPublicThreadsFragment(DCubePublicThreadsFragment())
        ChatSDK.ui().setPublicThreadsFragment(DCubePublicThreadsFragment())
        ChatSDKUI.setPrivateThreadsFragment(DCubePrivateThreadsFragment())
        ChatSDK.ui().setPrivateThreadsFragment(DCubePrivateThreadsFragment())
    }
}