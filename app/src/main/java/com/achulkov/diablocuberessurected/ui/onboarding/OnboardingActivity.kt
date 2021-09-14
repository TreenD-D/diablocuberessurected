package com.achulkov.diablocuberessurected.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.achulkov.diablocuberessurected.databinding.ActivityOnboardingBinding
import com.achulkov.diablocuberessurected.ui.DCMainActivity
import com.achulkov.diablocuberessurected.ui.onboarding.page_fragments.PageFragment

private const val NUM_PAGES = 3

class OnboardingActivity : AppCompatActivity(){
    companion object{
        val KEY_SHOW_WELCOME = "show_welcome"
    }

    private lateinit var binding: ActivityOnboardingBinding

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private lateinit var viewPager: ViewPager2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.pager

        viewPager.setPageTransformer(CubeInRotationTransformation())

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter

        binding.wormDotsIndicator.setViewPager2(viewPager)

        binding.welcomeProceedButton.setOnClickListener {
            PreferenceManager.getDefaultSharedPreferences(baseContext).edit()
                .putBoolean(KEY_SHOW_WELCOME, false)
                .apply()
            startActivity(Intent(this, DCMainActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> { val fragment = PageFragment()
                    fragment.arguments = bundleOf("index" to 0)
                    fragment
                }
                1 -> { val fragment = PageFragment()
                    fragment.arguments = bundleOf("index" to 1)
                    fragment
                }
                2 -> { val fragment = PageFragment()
                    fragment.arguments = bundleOf("index" to 2)
                    fragment
                }
                else -> PageFragment()
            }
        }
    }


}