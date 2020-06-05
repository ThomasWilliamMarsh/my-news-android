package info.tommarsh.mynews.onboarding.di

import dagger.Component
import info.tommarsh.mynews.core.di.CoreComponent
import info.tommarsh.mynews.core.di.FeatureScope
import info.tommarsh.mynews.onboarding.di.modules.OnBoardingModule
import info.tommarsh.mynews.onboarding.ui.OnBoardingActivity

@FeatureScope
@Component(modules = [OnBoardingModule::class])
internal interface OnBoardingComponent {

    @Component.Factory
    interface Factory {
        fun create(): OnBoardingComponent
    }

    fun inject(searchActivity: OnBoardingActivity)
}