package com.achulkov.diablocuberessurected

import android.app.Application
import android.content.Context
import android.net.TrafficStats
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun context(app: Application): Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    fun cacheDir(app: Application): File {
        return app.cacheDir
    }

    @Provides
    @Singleton
    fun ioExecutor(): ExecutorService {
        val poolSize = Runtime.getRuntime().availableProcessors() * 2 + 1
        val threadIds = AtomicInteger()
        return Executors.newFixedThreadPool(poolSize) { r: Runnable? ->
            val thread = Thread(r)
            val threadId = threadIds.incrementAndGet()
            TrafficStats.setThreadStatsTag(threadId)
            thread.name = "io-$threadId"
            thread.priority = Thread.MIN_PRIORITY
            thread
        }
    }

    @Provides
    @Singleton
    fun httpClient(executor: ExecutorService?, cacheDir : File): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.dispatcher(Dispatcher(executor!!))
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }
        builder.cache(
            Cache(
                File(cacheDir, "http_cache"),
                200L * 1024L * 1024L // 200 MiB
            )
        )
        builder.connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
        return builder.build()
    }


}