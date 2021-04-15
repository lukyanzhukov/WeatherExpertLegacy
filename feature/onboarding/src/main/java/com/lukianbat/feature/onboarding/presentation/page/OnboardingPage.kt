package com.lukianbat.feature.onboarding.presentation.page

import com.lukianbat.feature.onboarding.R

enum class OnboardingPage constructor(
    val titleRes: Int,
    val descriptionRes: Int,
    val backgroundRes: Int,
    val isLastPage: Boolean = false
) {

    FIRST_ONBOARDING_PAGE(
        R.string.onboarding_first_page_title,
        R.string.onboarding_first_page_description,
        R.drawable.onboarding_banner
    ),
    SECOND_ONBOARDING_PAGE(
        R.string.onboarding_second_page_title,
        R.string.onboarding_second_page_description,
        R.drawable.onboarding_banner
    ),
    THIRD_ONBOARDING_PAGE(
        R.string.onboarding_third_page_title,
        R.string.onboarding_third_page_description,
        R.drawable.onboarding_banner,
        true
    )
}
