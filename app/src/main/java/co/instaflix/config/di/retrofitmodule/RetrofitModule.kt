package co.instaflix.config.di.retrofitmodule

import co.instaflix.BuildConfig
import co.instaflix.config.core.api.RetrofitInterceptor
import co.instaflix.config.core.apicalladapter.CoroutineCallAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RetrofitModule {
    @Provides @Singleton fun gson(): Gson = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()

    @Provides @Singleton fun retrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client())
            .build()

    private fun client(): OkHttpClient {
        val okHttpClient = OkHttpClient
                .Builder()
                .connectionSpecs(arrayListOf(ConnectionSpec.MODERN_TLS))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(RetrofitInterceptor())

        if(BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addInterceptor(httpLoggingInterceptor)
        }

        return okHttpClient.build()
    }
}