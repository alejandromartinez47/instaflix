package co.instaflix.config.core.service

import co.instaflix.home.repository.ContentRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class InstaflixProviderRepository {
    @Provides @Reusable fun providercontentRepositoryRepository(dataSource: ContentRepository.ContentNetwork):
            ContentRepository = dataSource



}