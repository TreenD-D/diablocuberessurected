package com.achulkov.diablocuberessurected.data


import com.achulkov.diablocuberessurected.BuildConfig
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun DataRepo(impl : DCubeDataRepo) : com.achulkov.diablocuberessurected.data.DataRepo

    companion object {

        @Provides
        @Singleton
        fun moshi(): Moshi {
            val moshi: Moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()


            return moshi.newBuilder()
                .build()
        }

        @Provides
        @Singleton
        fun retrofit(httpClient: OkHttpClient, moshi: Moshi): Retrofit {
            val builder: Retrofit.Builder = Retrofit.Builder()
            builder.baseUrl(BuildConfig.API_ENDPOINT_METADIABLO)
            builder.client(httpClient.newBuilder()
                .addInterceptor { chain ->
                    val request: Request = chain.request()
                    return@addInterceptor chain.proceed(
                        request.newBuilder()
                            .addHeader("Authorization", BuildConfig.METADIABLO_API_KEY)
                            .build()
                    )
                }
                .build())
            builder.addConverterFactory(MoshiConverterFactory.create(moshi))
            builder.addCallAdapterFactory(RxJava3CallAdapterFactory.create())

            return builder.build()
        }

        @Provides
        fun metaDiabloApi(retrofit: Retrofit): MetaDiabloApi {
            return retrofit.create(MetaDiabloApi::class.java)
        }

        @Provides
        @Singleton
        fun getFirebaseStorage() : FirebaseStorage {
            return Firebase.storage("gs://diablocuberessurectedimages")
        }

        @Provides
        @Singleton
        fun getFirebaseDatabase() : FirebaseDatabase {
            return Firebase.database
        }


    }

}