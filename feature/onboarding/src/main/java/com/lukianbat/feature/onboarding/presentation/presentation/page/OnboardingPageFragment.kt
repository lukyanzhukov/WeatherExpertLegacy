package com.lukianbat.feature.onboarding.presentation.presentation.page

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.lukianbat.coreui.utils.argument
import com.lukianbat.coreui.utils.viewBinding
import com.lukianbat.feature.onboarding.R
import com.lukianbat.feature.onboarding.databinding.FragmentOnboardingPageBinding

class OnboardingPageFragment : Fragment(R.layout.fragment_onboarding_page) {

    private val onboardingPage by argument { getSerializable(ARG_ONBOARDING_PAGE) as OnboardingPage }
    private lateinit var onComplete: () -> Unit

    private val binding by viewBinding(FragmentOnboardingPageBinding::bind)

    private val onboardingPageTitle get() = binding.onboardingPageTitle
    private val onboardingPageDescription get() = binding.onboardingPageDescription
    private val onboardingPageBannerView get() = binding.onboardingPageBannerView
    private val onboardingCompleteButton get() = binding.onboardingCompleteButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPage(onboardingPage)
    }

    private fun initPage(page: OnboardingPage) {
        onboardingPageTitle.text = getString(page.titleRes)
        onboardingPageDescription.text = getString(page.descriptionRes)
        onboardingPageBannerView.setImageResource(page.backgroundRes)
        onboardingCompleteButton.isVisible = page.isLastPage
        onboardingCompleteButton.setOnClickListener { onComplete() }
    }

    companion object {
        private const val ARG_ONBOARDING_PAGE = "ARG_ONBOARDING_PAGE"

        fun newInstance(pageName: OnboardingPage, onComplete: () -> Unit): OnboardingPageFragment {
            return OnboardingPageFragment().apply {
                this.onComplete = onComplete
                arguments = bundleOf(ARG_ONBOARDING_PAGE to pageName)
            }
        }
    }
}
