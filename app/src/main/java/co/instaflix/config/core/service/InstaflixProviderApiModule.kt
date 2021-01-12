package co.instaflix.config.core.service

import co.instaflix.home.api.ContentDataSource
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module
class InstaflixProviderApiModule {
    @Provides @Reusable fun providerContentApi(retrofit: Retrofit): ContentDataSource =
            retrofit.create(ContentDataSource::class.java)

}

