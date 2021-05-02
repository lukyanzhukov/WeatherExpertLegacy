package com.lukianbat.feature.onboarding.presentation.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.viewpager2.widget.ViewPager2
import com.lukianbat.feature.onboarding.R
import com.lukianbat.feature.onboarding.presentation.di.OnboardingComponentController
import com.lukianbat.feature.onboarding.presentation.presentation.page.OnboardingPage
import kotlinx.android.synthetic.main.fragment_onboarding.*
import javax.inject.Inject

class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by navGraphViewModels<OnboardingViewModel>(R.id.navigation_global) { viewModelFactory }

    private val navController by lazy { findNavController() }

    private var adapter: OnboardingAdapter? = null

    private val viewPagerChangeCallBack: ViewPager2.OnPageChangeCallback =
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                onboardingPageIndicatorView.selection = position
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as OnboardingComponentController)
            .provideOnboardingComponent()
            .inject(this)
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