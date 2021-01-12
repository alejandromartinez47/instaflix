package co.instaflix.config.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.instaflix.config.core.viewmodel.InstaflixViewModelFactory
import co.instaflix.config.core.viewmodel.ViewModelKey
import co.instaflix.home.viewmodel.ContentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    abstract fun binViewModelFactory(factory: InstaflixViewModelFactory):
            ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ContentViewModel::class)
    abstract fun bindViewModelContentViewModel(contentViewModel: ContentViewModel):
            ViewModel

}