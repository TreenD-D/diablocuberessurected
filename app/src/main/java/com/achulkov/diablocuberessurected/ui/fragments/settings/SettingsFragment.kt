package com.achulkov.diablocuberessurected.ui.fragments.settings

import android.annotation.SuppressLint
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.achulkov.diablocuberessurected.R
import com.achulkov.diablocuberessurected.databinding.FragmentSettingsBinding
import com.achulkov.diablocuberessurected.ui.MainViewModel
import com.achulkov.diablocuberessurected.util.TextViewGradientSetter
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import sdk.chat.core.session.ChatSDK
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var binding : FragmentSettingsBinding
    @Inject
    lateinit var gradientSetter : TextViewGradientSetter

    private var mRewardedAd: RewardedAd? = null
    private val TAG = "SettingsRew"

    private val viewModel : MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)


        val adRequestRew = AdRequest.Builder().build()

        RewardedAd.load(context,"ca-app-pub-3489954973980283/8188470535", adRequestRew, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Timber.d("%s%s", TAG, adError.message)
                mRewardedAd = null
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                Timber.d("%sAd was loaded.", TAG)
                mRewardedAd = rewardedAd
            }
        })

        mRewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Timber.d("%sAd was shown.", TAG)
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                // Called when ad fails to show.
                Timber.d("%sAd failed to show.", TAG)
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Timber.d("%sAd was dismissed.", TAG)
                mRewardedAd = null
            }
        }


        gradientSetter.setTextViewGradient(binding.appNameTitle)
        gradientSetter.setTextViewGradient(binding.appNameSubtitle)
        gradientSetter.setTextViewGradient(binding.settingsTitle)


        binding.profileButton.setOnClickListener{
            viewModel.userActionsCounter.value = viewModel.userActionsCounter.value?.plus(1)
            if(!ChatSDK.auth().isAuthenticated){
                Snackbar.make(binding.profileButton, resources.getString(R.string.need_to_login), Snackbar.LENGTH_LONG)
                    .setAction(R.string.dismiss_snack) {
                        // Responds to click on the action
                    }
                    .setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
                    .show()
                requireActivity().findNavController(R.id.main_host).navigate(R.id.community_page)
            }
            else ChatSDK.ui().startEditProfileActivity(this.context, ChatSDK.currentUserID())
        }

        binding.aboutButton.setOnClickListener {
            viewModel.userActionsCounter.value = viewModel.userActionsCounter.value?.plus(1)
            requireActivity().findNavController(R.id.main_host).navigate(R.id.aboutAppFragment)
        }

        binding.supportButton.setOnClickListener {
            viewModel.userActionsCounter.value =  viewModel.userActionsCounter.value?.plus(1)
            if (mRewardedAd != null) {
                mRewardedAd?.show(requireActivity(), OnUserEarnedRewardListener {
                    fun onUserEarnedReward(rewardItem: RewardItem) {
                        Timber.d("%sUser earned the reward.", TAG)
                    }
                })
            } else {
                Timber.d("%sThe rewarded ad wasn't ready yet.", TAG)
            }
        }


        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)


    }



}