package info.tommarsh.data.di

import android.content.Context
import android.content.SharedPreferences
import dagger.BindsInstance
import dagger.Component
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.domain.source.CategoryRepository
import info.tommarsh.domain.source.VideoRepository

@Component(modules = [NetworkModule::class, LocalModule::class, RepositoryModule::class])
interface DataComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): DataComponent
    }

    fun articleRepository(): ArticleRepository

    fun categoriesRepository(): CategoryRepository

    fun videoRepository(): VideoRepository

    fun sharedPreferences(): SharedPreferences
}