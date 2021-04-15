package com.lukianbat.feature.onboarding.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lukianbat.feature.onboarding.presentation.page.OnboardingPage
import com.lukianbat.feature.onboarding.presentation.page.OnboardingPageFragment

class OnboardingAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val onComplete: () -> Unit
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = OnboardingPage.values().size

    override fun createFragment(position: Int): Fragment {
        val page = OnboardingPage.values()[position]
        return OnboardingPageFragment.newInstance(page, onComplete)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.scrollToPosition(0)
    }
}
