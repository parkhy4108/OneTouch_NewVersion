package com.dev_musashi.onetouch.di

import android.app.Application
import com.dev_musashi.onetouch.data.gallery.Gallery
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GalleryModule {

    @Provides
    @Singleton
    fun provideGallery(app: Application): Gallery {
        return Gallery(app)
    }


}