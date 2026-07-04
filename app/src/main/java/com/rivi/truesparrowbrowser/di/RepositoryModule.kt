package com.rivi.truesparrowbrowser.di

import com.rivi.truesparrowbrowser.data.repository.BrowserTabRepositoryImpl
import com.rivi.truesparrowbrowser.domain.repository.BrowserTabRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBrowserTabRepository(impl: BrowserTabRepositoryImpl): BrowserTabRepository
}