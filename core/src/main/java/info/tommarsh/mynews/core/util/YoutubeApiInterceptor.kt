package info.tommarsh.mynews.core.util

import info.tommarsh.mynews.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response


internal class YoutubeApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
            .newBuilder()
            .addQueryParameter("playlistId", BuildConfig.YOUTUBE_PLAYLIST)
            .addQueryParameter("part", "snippet,contentDetails")
            .addQueryParameter("maxResults", "20")
            .addQueryParameter("key", BuildConfig.YOUTUBE_API_KEY)
            .build()

        val request = chain.request().newBuilder().url(url).build()

        return chain.proceed(request)
    }
}