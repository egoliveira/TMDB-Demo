package br.com.gielamo.tmdb.data.mechanism.interceptor

import br.com.gielamo.tmdb.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TMDBApiInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        builder.addHeader(
            AUTHORIZATION_HEADER, String.format(
                AUTHORIZATION_HEADER_BEARER_VALUE_TEMPLATE,
                BuildConfig.TMDB_BEARER_TOKEN
            )
        )

        return chain.proceed(builder.build())
    }

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"

        private const val AUTHORIZATION_HEADER_BEARER_VALUE_TEMPLATE = "Bearer %1\$s"
    }
}