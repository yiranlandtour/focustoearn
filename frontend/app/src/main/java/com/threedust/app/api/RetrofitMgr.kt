package com.threedust.app.api

import com.threedust.app.MyApp
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author zzh
 * desc:
 */

object RetrofitMgr {

    const val BASE_URL = "http://app.xuyaomaoxian.com:8083/focuson/"
    const val TOKEN = "focuson_token"

    val api: ApiService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        getRetrofit().create(ApiService::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val cacheFile = File(MyApp.getContext().cacheDir, "cache")
        val cache = Cache(cacheFile, 50 * 1024 * 1024)

        return OkHttpClient.Builder()
            .addInterceptor(addQueryParameterInterceptor())
            .addInterceptor(addHeaderInterceptor()) // t
            .addInterceptor(httpLoggingInterceptor) //
            .cache(cache)
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .build()
    }

    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originRequest = chain.request()
            val modifiedUrl = originRequest.url.newBuilder()
                .build()
            val request = originRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }

    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originRequest = chain.request()
            val requestBuilder = originRequest.newBuilder()
                .header("token", TOKEN)
                .method(originRequest.method, originRequest.body)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }
}