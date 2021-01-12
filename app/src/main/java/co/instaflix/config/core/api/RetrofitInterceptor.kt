package co.instaflix.config.core.api

import android.content.Context
import android.net.Uri
import co.instaflix.BuildConfig
import co.instaflix.InstaflixApplication
import co.instaflix.config.core.api.annotation.TagServiceApis
import co.instaflix.config.core.api.exception.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import co.instaflix.R

class RetrofitInterceptor: Interceptor {

    @Inject lateinit var context: Context

    init {
        InstaflixApplication.daggerAppComponent.inject(this)
    }

    companion object {
        private const val API_KEY = "api_key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = chain.request().url()

        val newRequest = request.newBuilder()
            .addQueryParameter(API_KEY, BuildConfig.API_KEY)


        originalRequest.tag(Invocation::class.java)?.let { invocation ->
                invocation.method().getAnnotation(TagServiceApis::class.java)?.let { tag ->
                    val host = getHostFromBaseUrl(tag.value)
                    host?.let {
                        newRequest.host(it)
                        chain.withConnectTimeout(3, TimeUnit.MINUTES)
                        chain.withReadTimeout(3, TimeUnit.MINUTES)
                        chain.withWriteTimeout(3, TimeUnit.MINUTES)
                    }
                }
            }


        return kotlin.runCatching {
            chain.proceed(originalRequest
                    .newBuilder()
                    .url(
                            newRequest.build()
                    )
                    .build()
            )
        }.onFailure {
            throw NoConnectivityException(context.getString(R.string.error_conectivity_message))
        }.getOrThrow()
    }

    private fun getHostFromBaseUrl(type: String): String? {
        val uri = Uri.parse(BuildConfig.BASE_URL)
        return uri.host
    }
}