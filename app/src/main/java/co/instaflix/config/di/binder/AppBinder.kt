package co.instaflix.config.di.binder

import co.instaflix.home.view.fragments.DetailContentMovie
import co.instaflix.home.view.fragments.DetailContentSeries
import co.instaflix.home.view.fragments.MoviesFragment
import co.instaflix.home.view.fragments.SeriesFragment
import co.instaflix.navigation.view.NavigationActivity
import co.instaflix.splash.view.SplashActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class AppBinder {
    @ContributesAndroidInjector
    abstract fun splashActivity(): SplashActivity

    @ContributesAndroidInjector
    internal abstract fun navigationActivity(): NavigationActivity

    @ContributesAndroidInjector
    internal abstract fun seriesFragment(): SeriesFragment

    @ContributesAndroidInjector
    internal abstract fun moviesFragment(): MoviesFragment

    @ContributesAndroidInjector
    internal abstract fun detailContentSeries(): DetailContentSeries

    @ContributesAndroidInjector
    internal abstract fun detailContentMovie(): DetailContentMovie



}