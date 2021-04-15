package com.lukianbat.feature.onboarding.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.lukianbat.feature.onboarding.R
import com.lukianbat.feature.onboarding.presentation.page.OnboardingPage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_onboarding.*

@AndroidEntryPoint
class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {

    private val viewModel by viewModels<OnboardingViewModel>()
    private val navController by lazy { findNavController() }

    private var adapter: OnboardingAdapter? = null

    private val viewPagerChangeCallBack: ViewPager2.OnPageChangeCallback =
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                onboardingPageIndicatorView.selection = position
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.completeAction().observeData(viewLifecycleOwner, { onOnboardingComplete() })

        onboardingCloseButton.setOnClickListener { viewModel.complete() }
        initViewPager()
    }

    override fun onStop() {
        onboardingViewPager.unregisterOnPageChangeCallback(viewPagerChangeCallBack)
        super.onStop()
    }

    private fun initViewPager() {
        adapter = OnboardingAdapter(childFragmentManager, lifecycle, viewModel::complete)
        onboardingViewPager.adapter = adapter
        onboardingViewPager.registerOnPageChangeCallback(viewPagerChangeCallBack)
        onboardingPageIndicatorView.count = OnboardingPage.values().size
    }

    private fun onOnboardingComplete() {
        navController.navigate(R.id.onboardingPassedAction)
    }
}