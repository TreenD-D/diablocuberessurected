package com.achulkov.diablocuberessurected

import android.os.StrictMode
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDexApplication
import com.achulkov.diablocuberessurected.BuildConfig.GMAPS_STATIC_API_KEY
import com.achulkov.diablocuberessurected.ui.fragments.chat.DCubeChatInterfaceAdapter
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.HiltAndroidApp
import sdk.chat.core.session.ChatSDK
import sdk.chat.firebase.adapter.module.FirebaseModule
import sdk.chat.firebase.push.FirebasePushModule
import sdk.chat.firebase.ui.FirebaseUIModule
import sdk.chat.ui.module.UIModule
import timber.log.Timber
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class DiabloCubeApp : MultiDexApplication() {
    override fun onCreate() {
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
//                .setPublicChatRoomLifetimeMinutes(TimeUnit.DAYS.toMinutes(30))
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

            ChatSDK.config().logoDrawableResourceID = R.mipmap.ic_launcher

            ChatSDK.shared().setInterfaceAdapter(DCubeChatInterfaceAdapter(this))
        } catch (e :Exception) {
            e.printStackTrace();
        }
    }
}