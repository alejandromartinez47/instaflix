package co.instaflix.config.di.component

import android.app.Application
import android.content.Context
import co.instaflix.InstaflixApplication
import co.instaflix.config.core.api.RetrofitInterceptor
import co.instaflix.config.core.service.InstaflixProviderApiModule
import co.instaflix.config.core.service.InstaflixProviderRepository
import co.instaflix.config.di.binder.AppBinder
import co.instaflix.config.di.retrofitmodule.RetrofitModule
import co.instaflix.config.di.viewmodel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            RetrofitModule::class,
            AndroidSupportInjectionModule::class,
            ViewModelModule::class,
            InstaflixProviderApiModule::class,
            InstaflixProviderRepository::class,
            AppBinder::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        @BindsInstance fun context(context: Context): Builder
        fun build(): AppComponent
    }

    fun inject(instaflixApplication: InstaflixApplication)
    fun inject(retrofitInterceptor: RetrofitInterceptor)
}