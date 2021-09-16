package com.achulkov.diablocuberessurected.util

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilModule {

    @Binds
    abstract fun imageLoader(impl: GlideImageLoader) : ImageLoader

}